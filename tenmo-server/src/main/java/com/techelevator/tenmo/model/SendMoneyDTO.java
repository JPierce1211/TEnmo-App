package com.techelevator.tenmo.model;

public class SendMoneyDTO
{
        private int senderId;
        private int receiverId;
        private double sendingAmount;
        private int transferType;



    public SendMoneyDTO(int senderId, int receiverId, double sendingAmount, int transferType) {

            this.sendingAmount = sendingAmount;
            this.senderId = senderId;
            this.receiverId = receiverId;
            this.transferType = transferType;
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

        public double getSendingAmount() {
            return sendingAmount;
        }

        public void setSendingAmount(double sendingAmount) {
            this.sendingAmount = sendingAmount;
        }
        public int getTransferType() {
            return transferType;
        }

        public void setTransferType(int transferType) {
            this.transferType = transferType;
        }
}


