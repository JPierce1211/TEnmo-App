package com.techelevator.tenmo.dao;

import java.time.LocalDateTime;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcTransactionDao {
    private double availableSenderBalance;
    private double availableReceiverBalance;
    private double requestedAmount;
    private double sendingAmount;
    private JdbcTemplate jdbcTemplate;
    private Transfer transfer;
    public String rtrn = null;
    public String sendMoney(Transfer transfer, double receiverBalance, double senderBalance, double sendAmount)
    {
        try
        {
            if (senderBalance < sendAmount) {
                String sqlFailedTrans = "INSERT INTO transfer (from_id, to_id, amt, dot, transfer_type, transfer_status) VALUES (?,?,?,?, 1, 2)";
                jdbcTemplate.update(sqlFailedTrans, transfer.getFromId(), transfer.getToId(), sendingAmount, LocalDateTime.now());
                rtrn = "There are not enough funds to cover this transfer.";
            }
            if (senderBalance >= sendAmount) {
                double remainingFromBal = senderBalance - sendAmount;
                double remainingToBal = receiverBalance + sendAmount;
                String sqlFromCMD = "UPDATE account SET balance = ? WHERE user_id = ?";
                String sqlToCMD = "UPDATE account SET balance = ? WHERE user_id = ?";
                String sqlSentTrans = "INSERT INTO transfer (from_id, to_id, amt, dot, transfer_type, transfer_status) VALUES (?,?,?,?, 1, 1)";
                jdbcTemplate.update(sqlFromCMD, remainingFromBal, transfer.getFromId());
                jdbcTemplate.update(sqlToCMD, remainingToBal, transfer.getToId());
                jdbcTemplate.update(sqlSentTrans, transfer.getFromId(), transfer.getToId(), transfer.getAmt(), LocalDateTime.now());
                rtrn =  "Funds have been successfully transferred";
            }
        }
        catch (CannotGetJdbcConnectionException e) {
            System.out.println("Cannot Get a connection to the data: " + e);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Data is messed up (DIV): " + e);
        }
        return rtrn;
    }
/*
    public String requestMoney()
    {

    }
*/

}
