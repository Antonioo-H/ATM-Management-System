package com.model;

import java.util.Date;

public class CheckBalance extends Operation implements I2 {

    public CheckBalance() {}

    public void checkBalance(Account account, String bankCodeATM) {

        if(!account.getIban().getBankCode().equals(bankCodeATM)) {
            this.fees = AdditionalFees.CHECK_BALANCE.getFee();
            account.setBalance(account.getBalance() - this.fees);
        }
        this.operationDate = new Date();
        this.successfulOperation = true;
        System.out.println("Current balance of your account: " + account.getBalance());
        this.operationDetails = "Current balance of your account: " + account.getBalance();
    }

    @Override
    public double displayOperation(Account account, String bankCodeATM) {

        this.operationDate = new Date();
        this.checkBalance(account,bankCodeATM);

        return 0;
    }

    @Override
    public String toString() {
        return "CheckBalance{" +
                "operationDate=" + operationDate +
                ", operationDetails='" + operationDetails + '\'' +
                ", successfulOperation=" + successfulOperation +
                ", fees=" + fees +
                '}';
    }
}
