package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.SendMoneyDTO;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
public class TransferController
{
    private JdbcTemplate jdbcTemplate;
    private JdbcTransferDao transferDao;
    private JdbcAccount accountDao;
    private JdbcUserDao userDao;




    public TransferController(JdbcTransferDao transferDao, JdbcAccount accountDao, JdbcUserDao userDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @GetMapping("/transfers")
    public List<Transfer> listTransfers() {
        return transferDao.findAll();
    }

    @GetMapping("/transfers/{id}")
    public Transfer getTransferById(@PathVariable int id) {
        Transfer transfer = transferDao.findByTransferId(id);
        if (transfer == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found.");
        } else {
            return transfer;
        }
    }

    @PostMapping("/transfers")
    public Transfer transferMoney(@RequestBody SendMoneyDTO sendMoneyDto)
    {
        Transfer transfer = new Transfer();
        int senderId = sendMoneyDto.getSenderId();
        int receiverId = sendMoneyDto.getReceiverId();
        double availableSenderBalance;
        double availableReceiverBalance;
        double sendingAmt = sendMoneyDto.getSendingAmount();
        availableSenderBalance = accountDao.getBalance(userDao.findUsernameById(senderId));
        availableReceiverBalance = accountDao.getBalance(userDao.findUsernameById(receiverId));
        int transType = sendMoneyDto.getTransferType();

        if (transType == 1) {
            if (availableSenderBalance >= sendingAmt) {
                transfer.setFromId(senderId);
                transfer.setToId(receiverId);
                transfer.setAmt(sendingAmt);
                transfer.setTransferType(transType);
                transfer.setTransferStatus(1);
            }
            if (availableSenderBalance < sendingAmt) {
                throw new DaoException("Not enough funds to cover this requested transfer!");
            }
        }
        if (transType == 2)
        {
            if (availableReceiverBalance >= sendingAmt) {
                transfer.setFromId(senderId);
                transfer.setToId(receiverId);
                transfer.setAmt(sendingAmt);
                transfer.setTransferType(transType);
                transfer.setTransferStatus(2);
            }
            if (availableReceiverBalance < sendingAmt) {
                throw new DaoException("Not enough funds to cover this requested transfer!");
            }
        }
        if (transType > 2 || transType < 1)
        {
            throw new DaoException("Wrong input for transfer type. Use (1) to send funds, use (2) to request funds. All other entries are invalid");
        }
                 return transferDao.createTransfer(transfer);





    }
}
