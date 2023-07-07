package com.techelevator.tenmo.model;

public class SendMoneyDTO
{
        private int senderId;
        private int receiverId;
        private double sendingAmount;

        public SendMoneyDTO(int senderId, int receiverId, double sendingAmount) {

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

        public double getSendingAmount() {
            return sendingAmount;
        }

        public void setSendingAmount(double sendingAmount) {
            this.sendingAmount = sendingAmount;
        }
}


