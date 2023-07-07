package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferDao {
    List<Transfer> findAll();
    Transfer findByTransferId(int transferId);
    boolean createTransfer(int senderId, int receiverId, double availableSenderBalance,
                           double availableReceiverBalance, double sendingAmt);


}
