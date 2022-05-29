package com.model;

import org.iban4j.Iban;

import java.util.Date;
import java.util.Scanner;

public class Transfer extends Operation implements I2 {

    public Transfer(){}

    public void transfer(Account account, String bankCodeATM, Iban iban, double amount){

        if(amount > account.getBalance()) {
            System.out.println("Insufficient funds on your account!");
            this.operationDetails = "Insufficient funds on your account!";
        }
        else {
            Account toAccount = AssociatedBanks.findBank(BankCode.getEnumByString(iban.getBankCode())).findAccount(iban);
            if(toAccount != null) {
                toAccount.setBalance(toAccount.getBalance() + amount);
                account.setBalance(account.getBalance() - amount);
                if(!account.getIban().getBankCode().equals(bankCodeATM)) {
                    this.fees = AdditionalFees.TRANSFER.getFee();
                    account.setBalance(account.getBalance() - AdditionalFees.TRANSFER.getFee());
                }
                this.successfulOperation = true;
                System.out.println(amount + " was transfered from your account to the specified account!");
                this.operationDetails = amount + " was transfered from your account to the account specified!";
            }
            else {
                System.out.println("The iban introduced doesn't exist!");
                this.operationDetails = "The iban introduced doesn't exist!";
            }
        }
    }

    @Override
    public double displayOperation(Account account, String bankCodeATM) {

        this.operationDate = new Date();
        Scanner scanner = new Scanner(System.in);
        System.out.println("--------------------------------------------------------");
        System.out.println("0. BACK");
        System.out.println("Add the amount of money that you want to transfer:");
        double amount = scanner.nextDouble();
        while(amount < 0) {
            System.out.print("Error occurred! Add again the amount of money that you want to transfer: ");
            amount = scanner.nextDouble();
        }
        scanner.nextLine();
        if(amount != 0) {
            Iban iban = null;
            int ok = 0;
            while(ok == 0) {
                ok = 1;
                try {
                    System.out.println("Add iban:");
                    iban = Iban.valueOf(scanner.nextLine());
                }
                catch (Exception e) {
                    System.out.println("The iban you introduced is wrong! Try again!");
                    ok = 0;
                }
            }
            this.transfer(account,bankCodeATM,iban,amount);
        }

        return 0;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "operationDate=" + operationDate +
                ", operationDetails='" + operationDetails + '\'' +
                ", successfulOperation=" + successfulOperation +
                ", fees=" + fees +
                '}';
    }
}
