package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccount;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Transaction;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.techelevator.tenmo.model.SendMoneyDTO;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
public class TransactionController
{
    private JdbcTemplate jdbcTemplate;
    private JdbcTransferDao transferDao;
    private JdbcAccount accountDao;
    private JdbcUserDao userDao;
    private int senderId;
    private int receiverId;
    private double requestedAmt;
    private double sendingAmt;
    private double availableSenderBalance;
    private double availableReceiverBalance;
    private Transaction transaction;

    public TransactionController(int senderId, int receiverId, double requestedAmt, double sendingAmt, double availableSenderBalance, double availableReceiverBalance, JdbcTransferDao transferDao, JdbcAccount accountDao, JdbcUserDao userDao)
    {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.requestedAmt = requestedAmt;
        this.sendingAmt = sendingAmt;
        this.availableSenderBalance = availableSenderBalance;
        this.availableReceiverBalance = availableReceiverBalance;
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }



    @GetMapping("/transfer")
    public String sendMoney(@RequestBody SendMoneyDTO sendMoneyDto) {
        senderId = transaction.getSenderId();
        receiverId = transaction.getReceiverId();
        sendingAmt = transaction.getSendingAmount();
        availableSenderBalance = accountDao.getBalance(userDao.findUsernameById(senderId));
        availableReceiverBalance = accountDao.getBalance(userDao.findUsernameById(receiverId));
        String rtrn = null;
        try {

            if (availableSenderBalance < sendingAmt) {
                String sqlFailedTrans = "INSERT INTO transfer (from_id, to_id, amt, dot, transfer_type, transfer_status) VALUES (?,?,?,?, 1, 2)";
                jdbcTemplate.update(sqlFailedTrans, senderId, receiverId, sendingAmt, LocalDateTime.now());
                rtrn = "There are not enough funds to cover this transfer.";
            }
            if (availableSenderBalance >= sendingAmt) {
                double remainingFromBal = availableSenderBalance - sendingAmt;
                double remainingToBal = availableReceiverBalance + sendingAmt;
                String sqlFromCMD = "UPDATE account SET balance = ? WHERE user_id = ?";
                String sqlToCMD = "UPDATE account SET balance = ? WHERE user_id = ?";
                String sqlSentTrans = "INSERT INTO transfer (from_id, to_id, amt, dot, transfer_type, transfer_status) VALUES (?,?,?,?, 1, 1)";
                jdbcTemplate.update(sqlFromCMD, remainingFromBal, senderId);
                jdbcTemplate.update(sqlToCMD, remainingToBal, receiverId);
                jdbcTemplate.update(sqlSentTrans, senderId, receiverId, sendingAmt, LocalDateTime.now());
                rtrn = "Funds have been successfully transferred";
            }
        } catch (CannotGetJdbcConnectionException e) {
            System.out.println("Cannot Get a connection to the data: " + e);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Data is messed up (DIV): " + e);
        }
        return rtrn;
    }
}
