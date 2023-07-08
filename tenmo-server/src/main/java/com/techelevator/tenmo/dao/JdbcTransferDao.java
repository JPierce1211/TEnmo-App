package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{
    //private Account account = new Account();
    private JdbcTemplate jdbcTemplate;
    private JdbcAccount accountDao;
    private JdbcUserDao userDao;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate, JdbcAccount accountDao, JdbcUserDao userDao){
        this.jdbcTemplate = jdbcTemplate;
        this.accountDao = accountDao;
        this.userDao = userDao;
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
        Transfer transfer = new Transfer();
        String sqlCMD = "SELECT * FROM transfer WHERE transfer_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlCMD, transferId);
        if (rowSet.next()){
            transfer = mapRowToTransfer(rowSet);
            return transfer;
        }
        throw new UsernameNotFoundException("Account " + transferId + " was not found.");
    }

    @Override
    public Transfer createTransfer(Transfer transfer)
    {
        int senderId = transfer.getFromId();
        int receiverId = transfer.getToId();
        double sendingAmt = transfer.getAmt();
        double availableSenderBalance = accountDao.getBalance(userDao.findUsernameById(senderId));
        double availableReceiverBalance = accountDao.getBalance(userDao.findUsernameById(receiverId));
        int newTransferId = 0;
        double remainingFromBal;
        double remainingToBal;
        String sqlFromCMD = "UPDATE account SET balance = ? WHERE user_id = ?";
        String sqlToCMD = "UPDATE account SET balance = ? WHERE user_id = ?";

        if (transfer.getTransferType() == 1)
        {
            String sqlSentTrans = "INSERT INTO transfer (from_id, to_id, amt, dot, transfer_type, transfer_status) VALUES (?,?,?,?, 1, 1) RETURNING transfer_id";
            remainingFromBal = availableSenderBalance - sendingAmt;
            remainingToBal = availableReceiverBalance + sendingAmt;
            try
            {
                jdbcTemplate.update(sqlFromCMD, remainingFromBal, senderId);
                jdbcTemplate.update(sqlToCMD, remainingToBal, receiverId);
                newTransferId = jdbcTemplate.queryForObject(sqlSentTrans, int.class, senderId, receiverId, sendingAmt, LocalDateTime.now());

            } catch (ResponseStatusException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Accounts Not Found");
            }
        }
        if (transfer.getTransferType() == 2)
        {
            String sqlReceiveTrans = "INSERT INTO transfer (from_id, to_id, amt, dot, transfer_type, transfer_status) VALUES (?,?,?,?, 2, 1) RETURNING transfer_id";
            remainingFromBal = availableSenderBalance + sendingAmt;
            remainingToBal = availableReceiverBalance - sendingAmt;
            try
            {
                jdbcTemplate.update(sqlFromCMD, remainingFromBal, senderId);
                jdbcTemplate.update(sqlToCMD, remainingToBal, receiverId);
                newTransferId = jdbcTemplate.queryForObject(sqlReceiveTrans, int.class, senderId, receiverId, sendingAmt, LocalDateTime.now());

            } catch (ResponseStatusException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Accounts Not Found");
            }
        }

    return findByTransferId(newTransferId);

    }

    private Transfer mapRowToTransfer(SqlRowSet rs){
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setFromId(rs.getInt("from_id"));
        transfer.setToId(rs.getInt("to_id"));
        transfer.setAmt(rs.getInt("amt"));
        transfer.setTransferStatus(rs.getInt("transfer_status"));
        transfer.setTransferType(rs.getInt("transfer_type"));
        return transfer;
    }
}
