package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> findAll(){
        List<Transfer> transfer = new ArrayList<>();
        String sqlCMD  = "SELECT * FROM transfer;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCMD);
        while (results.next()){
            Transfer transfers = mapRowToTransfer(results);
            transfer.add(transfers);
        }
        return transfer;
    }
    @Override
    public Transfer findByTransferId(int transferId){
        String sqlCMD = "SELECT * FROM transfer WHERE transfer_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlCMD, transferId);
        if (rowSet.next()){
            return mapRowToTransfer(rowSet);
        }
        throw new UsernameNotFoundException("Account " + transferId + " was not found.");
    }
    @Override
    public void createTransfer(int senderId, int receiverId, double availableSenderBalance,
                                  double availableReceiverBalance, double sendingAmt)
    {
        double remainingFromBal = availableSenderBalance - sendingAmt;
        double remainingToBal = availableReceiverBalance + sendingAmt;
        String sqlFromCMD = "UPDATE account SET balance = ? WHERE user_id = ?";
        String sqlToCMD = "UPDATE account SET balance = ? WHERE user_id = ?";
        String sqlSentTrans = "INSERT INTO transfer (from_id, to_id, amt, dot, transfer_type, transfer_status) VALUES (?,?,?,?, 1, 1)";
        jdbcTemplate.update(sqlFromCMD, remainingFromBal, senderId);
        jdbcTemplate.update(sqlToCMD, remainingToBal, receiverId);
        jdbcTemplate.update(sqlSentTrans, senderId, receiverId, sendingAmt, LocalDateTime.now());
        //rtrn = "Funds have been successfully transferred";


    }

    private Transfer mapRowToTransfer(SqlRowSet rs){
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setFromId(rs.getInt("fromID"));
        transfer.setToId(rs.getInt("toId"));
        transfer.setAmt(rs.getInt("amt"));
        transfer.setTransferStatus(rs.getInt("transfer_status"));
        transfer.setTransferType(rs.getInt("transfer_type"));
        return transfer;
    }
}
