package com;

import lombok.*;
import org.iban4j.Iban;

import java.util.ArrayList;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ATM implements Actions {

    private Address location;
    private double amountOfCashDisponible;
    private String bankCode;
    private ArrayList<Bank> banks;


    private Bank findBank(String bankName) {
        for(Bank it : banks) {
            if(it.getBankName().equals(bankName))
                return it;
        }
        return null;
    }

    @Override
    public Operation withdrawal(Card card, double amount) throws CloneNotSupportedException {
        Operation operation = new Operation();

        if(amount > amountOfCashDisponible) {
            System.out.println("Insufficient funds!");
            return null;
        }

        Bank associatedBank = findBank(card.getBankName().toUpperCase());
        if(associatedBank != null) {
            Account associatedAccount = associatedBank.findAccount(card);
            if(associatedAccount != null) {
                associatedAccount.setBalance(associatedAccount.getBalance() - amount);
                amountOfCashDisponible -= amount;
                int ok = 0;
                if(!BankCode.valueOf(card.getBankName().toUpperCase()).getBankCode().equals(this.bankCode)) {
                    operation.setFees(AdditionalFees.WITHDRWAL.getFee());
                    associatedAccount.setBalance(associatedAccount.getBalance() - AdditionalFees.WITHDRWAL.getFee());
                    ok = 1;
                }
                operation.setOperationDate(new Date());
                operation.setSuccessfulOperation(true);
                if(ok == 0) {
                    operation.setOperationDetails(amount + " was withdrawn from your account!");
                }
                else {
                    operation.setOperationDetails((amount + AdditionalFees.WITHDRWAL.getFee()) + " was withdrawn from your account!");
                }
            }
        }
        return (Operation) operation.clone();
    }

    @Override
    public Operation deposit(Card card, double amount) throws CloneNotSupportedException {
        Operation operation = new Operation();

        Bank associatedBank = findBank(card.getBankName().toUpperCase());
        if(associatedBank != null) {
            Account associatedAccount = associatedBank.findAccount(card);
            if(associatedAccount != null) {
                associatedAccount.setBalance(associatedAccount.getBalance() + amount);
                amountOfCashDisponible += amount;
                int ok = 0;
                if(!BankCode.valueOf(card.getBankName().toUpperCase()).getBankCode().equals(this.bankCode)) {
                    operation.setFees(AdditionalFees.DEPOSIT.getFee());
                    associatedAccount.setBalance(associatedAccount.getBalance() - AdditionalFees.DEPOSIT.getFee());
                    ok = 1;
                }
                operation.setOperationDate(new Date());
                operation.setSuccessfulOperation(true);
                if(ok == 0) {
                    operation.setOperationDetails(amount + " was added to your account!");
                }
                else {
                    operation.setOperationDetails((amount-AdditionalFees.DEPOSIT.getFee()) + " was added to your account!");
                }

            }
        }
        return (Operation) operation.clone();
    }

    @Override
    public Operation checkBalance(Card card) throws CloneNotSupportedException {
        Operation operation = new Operation();

        Bank associatedBank = findBank(card.getBankName().toUpperCase());
        if(associatedBank != null) {
            Account associatedAccount = associatedBank.findAccount(card);
            if(associatedAccount != null) {
                int ok = 0;
                if(!BankCode.valueOf(card.getBankName().toUpperCase()).getBankCode().equals(this.bankCode)) {
                    operation.setFees(AdditionalFees.CHECK_BALANCE.getFee());
                    associatedAccount.setBalance(associatedAccount.getBalance() - AdditionalFees.CHECK_BALANCE.getFee());
                    ok = 1;
                }
//                System.out.println("Current balance of your account: " + associatedAccount.getBalance());
                operation.setOperationDate(new Date());
                operation.setSuccessfulOperation(true);
                if(ok == 0) {
                    operation.setOperationDetails("Current balance of your account: " + associatedAccount.getBalance());
                }
                else {
                    operation.setOperationDetails("Current balance of your account: " + (associatedAccount.getBalance()-AdditionalFees.CHECK_BALANCE.getFee()));
                }
            }
        }
        return (Operation) operation.clone();
    }

    @Override
    public Operation transfer(Card card, double amount, Iban toIban) throws CloneNotSupportedException {
        Operation operation = new Operation();

        Bank associatedBank1 = findBank(card.getBankName().toUpperCase());
        Bank associatedBank2 = findBank(BankCode.getEnumByString(toIban.getBankCode().toUpperCase()));
        if(associatedBank1 != null && associatedBank2 != null) {
            Account associatedAccount = associatedBank1.findAccount(card);
            Account toAccount = associatedBank2.findAccount(toIban);
            if (associatedAccount != null && toAccount != null) {

                associatedAccount.setBalance(associatedAccount.getBalance() - amount);
                amountOfCashDisponible += amount;
                toAccount.setBalance(toAccount.getBalance() + amount);
                int ok = 0;
                if(!BankCode.valueOf(card.getBankName().toUpperCase()).getBankCode().equals(this.bankCode)) {
                    operation.setFees(AdditionalFees.TRANSFER.getFee());
                    associatedAccount.setBalance(associatedAccount.getBalance() - AdditionalFees.TRANSFER.getFee());
                    ok = 1;
                }
                operation.setOperationDate(new Date());
                operation.setSuccessfulOperation(true);
                if(ok == 0) {
                    operation.setOperationDetails(amount + " was transfered from your account to the account mentioned!");
                }
                else {
                    operation.setOperationDetails(amount + " was transfered from your account to the account mentioned! Additional fees: " + AdditionalFees.TRANSFER.getFee());
                }
            }
            else if(associatedAccount != null) {
                operation.setOperationDetails("The iban introduced doesn't exist! Try again!");
            } else {
                operation.setOperationDetails("Your account can't be accessed!");
            }
        }
        return (Operation) operation.clone();
    }
}
