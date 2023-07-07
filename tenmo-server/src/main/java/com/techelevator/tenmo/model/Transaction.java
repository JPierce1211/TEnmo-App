package com.techelevator.tenmo.model;



public class Transaction {
    private int senderId;
    private int receiverId;
    private double availableSenderBalance;
    private double availableReceiverBalance;
    private double requestedAmount;
    private double sendingAmount;

    public Transaction(double availableSenderBalance, double availableReceiverBalance, double requestedAmount, double sendingAmount, int senderId, int receiverId) {
        this.availableSenderBalance = availableSenderBalance;
        this.availableReceiverBalance = availableReceiverBalance;
        this.requestedAmount = requestedAmount;
        this.sendingAmount = sendingAmount;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public double getAvailableSenderBalance() {
        return availableSenderBalance;
    }

    public void setAvailableSenderBalance(double availableSenderBalance) {
        this.availableSenderBalance = availableSenderBalance;
    }

    public double getAvailableReceiverBalance() {
        return availableReceiverBalance;
    }

    public void setAvailableReceiverBalance(double availableReceiverBalance) {
        this.availableReceiverBalance = availableReceiverBalance;
    }

    public double getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public double getSendingAmount() {
        return sendingAmount;
    }

    public void setSendingAmount(double sendingAmount) {
        this.sendingAmount = sendingAmount;
    }
}
