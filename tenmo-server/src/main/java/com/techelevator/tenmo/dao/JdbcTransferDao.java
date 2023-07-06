package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDao implements TransferDao{
    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> findAll(){
        List<Transfer> transfer = new ArrayList<>();
        String sqlCMD  = "SELECT * FROM transfer_records;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCMD);
        while (results.next()){
            Transfer transfers = mapRowToTransfer(results);
            transfer.add(transfers);
        }
        return transfer;
    }
    @Override
    public Transfer findByTransferId(int transferId){
        String sqlCMD = "SELECT * FROM transfer_records WHERE transfer_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlCMD, transferId);
        if (rowSet.next()){
            return mapRowToTransfer(rowSet);
        }
        throw new UsernameNotFoundException("Account " + transferId + " was not found.");
    }
    @Override
    public boolean createTransfer(int fromId, int toId){
        

    }

    private Transfer mapRowToTransfer(SqlRowSet rs){
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getInt("transfer_id"));
        transfer.setFromId(rs.getInt("fromID"));
        transfer.setToId(rs.getInt("toId"));
        transfer.setAmt(rs.getInt("amt"));
        transfer.setTransferStatus(rs.getInt("transfer_status"));
        transfer.setTransferType(rs.getInt("transfer_type"));
    }
}
