package com.model;

import java.util.Date;
import java.util.Scanner;

public class Withdrawl extends Operation implements I1 {

    public Withdrawl(){}

    public void withdrawal(Account account, String bankCodeATM, double amount) {

        if(amount > account.getBalance()) {
            this.operationDetails = "Insufficient funds on your account!";
            System.out.println("Insufficient funds on your account!");
        }
        else {
            account.setBalance(account.getBalance() - amount);
            if(!account.getIban().getBankCode().equals(bankCodeATM)) {
                this.fees = AdditionalFees.WITHDRWAL.getFee();
                account.setBalance(account.getBalance() - AdditionalFees.WITHDRWAL.getFee());
            }
            this.successfulOperation = true;
            System.out.println(amount + " was withdrawn from your account!");
            this.operationDetails = amount+ " was withdrawn from your account!";
        }
    }

    @Override
    public double displayOperation(Account account, String bankCodeATM, double amountOfCashDisponible) {

        this.operationDate = new Date();
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------------------------------------------------------");
        System.out.println("0. BACK");
        System.out.print("Add the amount of money that you want to withdraw: ");
        double amount = scanner.nextDouble();
        while(amount < 0) {
            System.out.print("Error occurred! Add again the amount of money that you want to withdraw: ");
            amount = scanner.nextDouble();
        }
        if(amount != 0) {
            if(amount <= amountOfCashDisponible) {
                this.withdrawal(account,bankCodeATM,amount);
                if(this.successfulOperation) {
                    return -amount;
                }
            }
            else {
                this.operationDetails = "Insufficient funds on ATM!";
                System.out.println("Insufficient funds on ATM!");
            }
        }

        return 0;
    }

    @Override
    public String toString() {
        return "Withdrawl{" +
                "operationDate=" + operationDate +
                ", operationDetails='" + operationDetails + '\'' +
                ", successfulOperation=" + successfulOperation +
                ", fees=" + fees +
                '}';
    }
}