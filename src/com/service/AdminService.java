package com.service;

import com.model.ATM;
import com.model.Address;
import com.model.BankBranch;
import com.repository.ATMRepository;
import com.repository.BankBranchRepository;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class AdminService {

    private ArrayList<ATM> atms;
    private ArrayList<BankBranch> bankBranches;


    public AdminService() {
        this.atms = new ArrayList<>();
        this.bankBranches = new ArrayList<>();
    }

    public ArrayList<ATM> getAtms() {
        return atms;
    }

    public void setAtms(ArrayList<ATM> atms) {
        this.atms = atms;
    }

    public ArrayList<BankBranch> getBankBranches() {
        return bankBranches;
    }

    public void setBankBranches(ArrayList<BankBranch> bankBranches) {
        this.bankBranches = bankBranches;
    }

    public void addATM(ATMRepository atmRepository, Address address, Scanner scanner) throws CloneNotSupportedException {

        System.out.println("Amount of cash:");
        double amount = scanner.nextDouble();

        scanner.nextLine();
        System.out.println("Bank Code:");
        String s = scanner.nextLine().toUpperCase();

        ATM atm = new ATM(address,amount,s);
        atmRepository.addATM(atm);
    }

    public void deleteATM(ATMRepository atmRepository, Scanner scanner) throws CloneNotSupportedException {
        scanner.nextLine();
        System.out.println("Street:");
        String s1 = scanner.nextLine();

        System.out.println("Number on street:");
        String s2 = scanner.nextLine();

        System.out.println("County:");
        String s3 = scanner.nextLine();

        System.out.println("City:");
        String s4 = scanner.nextLine();

        ATM atm = atmRepository.getATM(new Address(s1,s2,s3,s4));
        if(Objects.isNull(atm)) {
            System.out.println("There is no atm at indicated address!");
        }
        else {
            atmRepository.deleteATM(atm);
        }
    }

    public void updateATM(ATMRepository atmRepository, Scanner scanner) {
        scanner.nextLine();
        System.out.println("Street:");
        String s1 = scanner.nextLine();

        System.out.println("Number on street:");
        String s2 = scanner.nextLine();

        System.out.println("County:");
        String s3 = scanner.nextLine();

        System.out.println("City:");
        String s4 = scanner.nextLine();

        ATM atm = atmRepository.getATM(new Address(s1,s2,s3,s4));
        if (Objects.isNull(atm)){
            System.out.println("There is no atm at indicated address!");
        }
        else{
            System.out.print("Change the amount of cash disponible on ATM:");
            double newAmount = scanner.nextDouble();
            atmRepository.updateATM(atm,newAmount);
        }

    }

    public void showATMs(ATMRepository atmRepository){
        atmRepository.displayATMs();
    }

    public void addBanchBranch(BankBranchRepository bankBranchRepository, String telephone, Scanner scanner) throws CloneNotSupportedException {

        System.out.println("Street:");
        String s1 = scanner.nextLine();

        System.out.println("Number on street:");
        String s2 = scanner.nextLine();

        System.out.println("County:");
        String s3 = scanner.nextLine();

        System.out.println("City:");
        String s4 = scanner.nextLine();

        Address address = new Address(s1, s2, s3, s4);

        System.out.println("Bank Code:");
        String s = scanner.nextLine().toUpperCase();

        BankBranch bb = new BankBranch(address,telephone,s);
        bankBranchRepository.addBankBranch(bb);
    }

    public void deleteBanchBranch(BankBranchRepository bankBranchRepository, Scanner scanner) throws CloneNotSupportedException {

        scanner.nextLine();
        System.out.println("Telephone:");
        String telephone = scanner.nextLine();

        BankBranch bb = bankBranchRepository.getBankBranch(telephone);

        if(Objects.isNull(bb)) {
            System.out.println("There is no bank branch with this telephone number!");
        }
        else {
            bankBranchRepository.deleteBankBranch(bb);
        }
    }

    public void updateBanchBranch(BankBranchRepository bankBranchRepository, Scanner scanner) {
        scanner.nextLine();
        System.out.println("Telephone:");
        String telephone = scanner.nextLine();

        BankBranch bb = bankBranchRepository.getBankBranch(telephone);

        if(Objects.isNull(bb)) {
            System.out.println("There is no bank branch with this telephone number!");
        }
        else {
            System.out.print("Change the telephone number: ");
            String newTelephone = scanner.nextLine();
            bankBranchRepository.updateBankBranch(bb,newTelephone);
        }
    }

    public void showBanchBranches(BankBranchRepository bankBranchRepository) {
        bankBranchRepository.displayBankBranches();
    }

}
