package com.model;

import java.util.ArrayList;

public class AssociatedBanks {

    private static ArrayList<Bank> databaseAboutBanks = new ArrayList<>();

    public static void addBank(Bank bank) throws CloneNotSupportedException {
        Bank newBank = (Bank) bank.clone();
        AssociatedBanks.databaseAboutBanks.add(newBank);
    }

    static Bank findBank(String bankName) {
        for(Bank it : databaseAboutBanks) {
            if(it.getBankName().equals(bankName))
                return it;
        }
        return null;
    }

    public static ArrayList<Bank> getDatabaseAboutBanks() {
        return databaseAboutBanks;
    }
}
