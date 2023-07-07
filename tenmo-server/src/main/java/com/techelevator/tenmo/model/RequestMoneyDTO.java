package com.techelevator.tenmo.model;

public class RequestMoneyDTO
{
    private int senderId;
    private int receiverId;

    private double requestedAmount;


    public RequestMoneyDTO(double availableSenderBalance, double availableReceiverBalance, double requestedAmount, double sendingAmount, int senderId, int receiverId) {
        this.requestedAmount = requestedAmount;
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
    public double getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(double requestedAmount) {
        this.requestedAmount = requestedAmount;
    }
}
