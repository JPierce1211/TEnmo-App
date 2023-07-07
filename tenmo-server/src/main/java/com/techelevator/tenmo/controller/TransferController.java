package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.*;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

//@PreAuthorize("isAuthenticated()")
@RestController
public class TransferController {
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

}
