package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
public class TransactionController {
    private TransferDao transferDao;
    private AccountDao accountDao;
    private UserDao userDao;

    public TransactionController(TransferDao transferDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }
    @GetMapping("/user/{id}")
    public Account getAccountById(@PathVariable int userId){
        Account account = accountDao.findByUserId(userId);
        if (account == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
        } else {
            return account;
        }
    }
    @GetMapping("/user")
    public List<User> listUsers(){
        return userDao.findAll();
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
