package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    List<Transfer> findAll();
    Transfer findByTransferId(int transferId);
    boolean create(int fromId, int toId);


}
