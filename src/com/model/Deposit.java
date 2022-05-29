package com.model;

import java.util.Date;
import java.util.Scanner;

public class Deposit extends Operation implements I2 {

    public Deposit() {}

    public void deposit(Account account, String bankCodeATM, double amount) {

        account.setBalance(account.getBalance() + amount);
        if(!account.getIban().getBankCode().equals(bankCodeATM)) {
            this.fees = AdditionalFees.DEPOSIT.getFee();
            account.setBalance(account.getBalance() - AdditionalFees.DEPOSIT.getFee());
        }
        this.successfulOperation = true;
        System.out.println(amount + " was added to your account!");
        this.operationDetails = amount + " was added to your account!";
    }

    @Override
    public double displayOperation(Account account, String bankCodeATM) {

        this.operationDate = new Date();
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------------------------------------------------------");
        System.out.println("0. BACK");
        System.out.print("Add the amount of money that you want to deposit: ");
        double amount = scanner.nextDouble();
        while(amount < 0) {
            System.out.print("Error occurred! Add again the amount of money that you want to deposit: ");
            amount = scanner.nextDouble();
        }
        if(amount != 0) {
            this.deposit(account,bankCodeATM,amount);
            if(this.successfulOperation) {
                return amount;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Deposit{" +
                "operationDate=" + operationDate +
                ", operationDetails='" + operationDetails + '\'' +
                ", successfulOperation=" + successfulOperation +
                ", fees=" + fees +
                '}';
    }
}
