package com.model;

import lombok.*;
import org.iban4j.Iban;

import java.util.ArrayList;

@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Bank implements Cloneable {

    private String bankName;
    private String bankTelephone;
    private String email;
    private String website;
    private ArrayList<BankBranch> bankBranches;

    public Bank(String bankName, String bankTelephone, String email, String website, ArrayList<BankBranch> bankBranches) throws CloneNotSupportedException {
        this.bankName = bankName.toUpperCase();
        this.bankTelephone = bankTelephone;
        this.email = email;
        this.website = website;

        this.bankBranches = new ArrayList<>();
        for (BankBranch bankBranch : bankBranches) {
            this.bankBranches.add((BankBranch) bankBranch.clone());
        }
    }

    public Account findAccount(Card card) {
        Account tempAccount;
        for(BankBranch branch : bankBranches) {
            tempAccount = branch.findAccount(card);
            if(tempAccount != null) {
                return branch.findAccount(card);
            }
        }
        return null;
    }

    public Account findAccount(Iban iban) {
        Account tempAccount;
        for(BankBranch branch : bankBranches) {
            tempAccount = branch.findAccount(iban);
            if(tempAccount != null) {
                return branch.findAccount(iban);
            }
        }
        return null;
    }

    public Card findCardByID(String ID) {

        if(ID.length() == 16) {
            Card tempCard;
            for(BankBranch branch : bankBranches) {
                tempCard = branch.findCardByID(ID);
                if(tempCard != null) {
                    return branch.findCardByID(ID);
                }
            }
        }
        return null;
    }

    public Card findCardByCNP(String CNP) {

        if(CNP.length() == 13) {
            Card tempCard;
            for(BankBranch branch : bankBranches) {
                tempCard = branch.findCard(CNP).get(0);
                if(tempCard != null) {
                    return branch.findCard(CNP).get(0);
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "\n\tbankName='" + bankName + '\'' +
                "\n\ttelephone='" + bankTelephone + '\'' +
                "\n\temail='" + email + '\'' +
                "\n\twebsite='" + website + '\'' +
                "\n\tbankBranches=" + bankBranches +
                "\n}";
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Bank bankClone = (Bank) super.clone();
        bankClone.setBankBranches((ArrayList<BankBranch>) bankClone.getBankBranches().clone());
        return bankClone;
    }
}