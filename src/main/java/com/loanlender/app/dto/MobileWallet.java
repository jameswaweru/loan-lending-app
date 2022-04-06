package com.loanlender.app.dto;

public class MobileWallet {

    private double amount;

    public MobileWallet(double amount) {
        this.amount = amount;
    }

    public MobileWallet() {
    }

    public String checkStatus(){
        if(this.amount == 5000){
            return "failed";
        }

        return "Success";
    }
}
