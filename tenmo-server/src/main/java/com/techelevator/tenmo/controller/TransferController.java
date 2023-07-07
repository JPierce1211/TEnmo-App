package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.SendMoneyDTO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

//@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {
    private JdbcTemplate jdbcTemplate;
    private JdbcTransferDao transferDao;
    private JdbcAccount accountDao;
    private JdbcUserDao userDao;


    public TransferController(JdbcTransferDao transferDao, JdbcAccount accountDao, JdbcUserDao userDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.userDao =  userDao;
    }

    @GetMapping("/transfer")
    public List<Transfer> listTransfers(){
        return transferDao.findAll();
    }
    @GetMapping("/transfer/{id}")
    public Transfer getTransferById(@PathVariable int transferId){
        Transfer transfer = transferDao.findByTransferId(transferId);
        if (transfer == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found.");
        } else {
            return transfer;
        }
    }
    @PostMapping("/transfers")
    public Transfer sendMoney(@RequestBody SendMoneyDTO sendMoneyDto) { //Can do both sending and receiving in this annotation
        //change return type to Transfer due to entry into transfer table
        double availableSenderBalance;
        double availableReceiverBalance;
        int senderId = sendMoneyDto.getSenderId();
        int receiverId = sendMoneyDto.getReceiverId();
        double sendingAmt = sendMoneyDto.getSendingAmount();
        availableSenderBalance = accountDao.getBalance(userDao.findUsernameById(senderId)); //If getBalance works keep these
        availableReceiverBalance = accountDao.getBalance(userDao.findUsernameById(receiverId)); //If getBalance works keep these
        String rtrn = null;
        //if (availableSenderBalance < sendingAmt){
        //
        //try { //line 53 down goes into transferDao

            if (availableSenderBalance < sendingAmt) {
//                String sqlFailedTrans = "INSERT INTO transfer (from_id, to_id, amt, dot, transfer_type, transfer_status) VALUES (?,?,?,?, 1, 2)";
//                jdbcTemplate.update(sqlFailedTrans, senderId, receiverId, sendingAmt, LocalDateTime.now());
                rtrn = "There are not enough funds to cover this transfer."; //create exception for insufficient funds and put throw new (created Exception) look at Dao day 2
            }
//            if (availableSenderBalance >= sendingAmt) { //Line 63 down needs to go into JdbcTransfer Create() //leave if condition in controller
//                double remainingFromBal = availableSenderBalance - sendingAmt;
//                double remainingToBal = availableReceiverBalance + sendingAmt;
//                String sqlFromCMD = "UPDATE account SET balance = ? WHERE user_id = ?";
//                String sqlToCMD = "UPDATE account SET balance = ? WHERE user_id = ?";
//                String sqlSentTrans = "INSERT INTO transfer (from_id, to_id, amt, dot, transfer_type, transfer_status) VALUES (?,?,?,?, 1, 1)";
//                jdbcTemplate.update(sqlFromCMD, remainingFromBal, senderId);
//                jdbcTemplate.update(sqlToCMD, remainingToBal, receiverId);
//                jdbcTemplate.update(sqlSentTrans, senderId, receiverId, sendingAmt, LocalDateTime.now());
//                rtrn = "Funds have been successfully transferred";
//            }
//        } catch (CannotGetJdbcConnectionException e) {
//            System.out.println("Cannot Get a connection to the data: " + e);
//        } catch (DataIntegrityViolationException e) {
//            System.out.println("Data is messed up (DIV): " + e);
//        }
       return null;
    }

}
