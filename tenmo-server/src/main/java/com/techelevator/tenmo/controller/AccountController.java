package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccount;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class AccountController
{

    private JdbcTransferDao transferDao;
    private JdbcAccount accountDao;
    private JdbcUserDao userDao;

    public AccountController(JdbcTransferDao transferDao, JdbcAccount accountDao, JdbcUserDao userDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @GetMapping("/account")
    public List<Account> listAllAccounts()
    {
        return accountDao.findAll();
    }

    @GetMapping("/account/{id}")
    public Account getAccountById(@PathVariable int id)
    {
        Account account = accountDao.findByUserId(id);
        if (account == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
        } else {
            return account;
        }
    }

}
