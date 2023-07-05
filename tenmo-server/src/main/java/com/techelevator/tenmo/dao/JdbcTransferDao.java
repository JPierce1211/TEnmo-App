package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

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
