package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.JdbcAccount;
import com.techelevator.tenmo.dao.JdbcTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class UserController
{
    private JdbcTransferDao transferDao;
    private JdbcAccount accountDao;
    private JdbcUserDao userDao;


    public UserController(JdbcTransferDao transferDao, JdbcAccount accountDao, JdbcUserDao userDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.userDao =  userDao;
    }

    //@RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
    @GetMapping("/user/{id}")
    public Account getUserById(@PathVariable int id){
        Account account = accountDao.findByUserId(id);
        if (account == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found.");
        } else {
            return account;
        }
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    //@GetMapping("/user")
    public List<User> listUsers(){
        return userDao.findAll();
    }
}
