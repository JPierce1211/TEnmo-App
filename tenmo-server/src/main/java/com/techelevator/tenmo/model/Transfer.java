package com.techelevator.tenmo.model;

public class Transfer {
    private int transferId;
    private int fromId;
    private int toId;
    private int amt;
    private int transferType;
    private int transferStatus;
    public Transfer()
    {}
    public Transfer(int transferId, int fromId, int toId, int amt,
                    int transferType, int transferStatus){
        this.transferId = transferId;
        this.fromId = fromId;
        this.toId = toId;
        this.amt = amt;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    public int getTransferType() {
        return transferType;
    }

    public void setTransferType(int transferType) {
        this.transferType = transferType;
    }

    public int getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(int transferStatus) {
        this.transferStatus = transferStatus;
    }
}
