package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccount implements AccountDao
{
    private JdbcTemplate jdbcTemplate;

    public JdbcAccount(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Account> findAll()
    {
        List<Account> accounts = new ArrayList<>();
        String sqlCMD = "SELECT * FROM account;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sqlCMD);
        while(results.next())
        {
            Account account = mapRowToAccount(results);
            accounts.add(account);
        }
        return accounts;
    }

    @Override
    public Account findByUserId(int userId)
    {
        String sqlCMD = "SELECT * FROM account WHERE user_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlCMD, userId);
        if (rowSet.next()){
            return mapRowToAccount(rowSet);
        }
        throw new UsernameNotFoundException("Account " + userId + " was not found.");
    }

    @Override
    public double getBalance(int userId){
        String sqlCMD = "SELECT balance FROM account WHERE user_id = ? RETURNING balance;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sqlCMD, userId);
        double currentBal = 0;
        try {
           currentBal = jdbcTemplate.queryForObject(sqlCMD, double.class, userId);

        }catch (DataAccessException e){

        } return currentBal;
    }

    @Override
    public boolean create(int userId)
    {
 return true;
    }

    private Account mapRowToAccount(SqlRowSet rs)
    {
        Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(rs.getDouble("balance"));
        return account;
    }
}
