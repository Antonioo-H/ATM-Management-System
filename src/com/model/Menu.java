package com.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Menu {

    private List<Operation> operations;
    private List<Operation> operationsHistory;
    private static Menu instance = null;
    private Scanner scanner;

    private Menu(){}

    private void Setup() {
        scanner = new Scanner(System.in);
        operations = new ArrayList<>();
        operationsHistory = new ArrayList<>();

        operations.add(new Withdrawl());
        operations.add(new Deposit());
        operations.add(new CheckBalance());
        operations.add(new Transfer());
    }

    public static Menu getInstance() {
        if(instance == null) {
            instance = new Menu();
        }
        return instance;
    }

    public void setOperations(List<Operation> operations) {this.operations = operations;}

    private void welcome() {
        System.out.println("---------------------------------------------------");
        System.out.println("----------------WELCOME TO ATM---------------------");
        System.out.println("---------------------------------------------------");
    }

    private Account validateCredentials() {

        String ID = getClientInputString("Introduce your card ID:");
        Card tempCard;
        if(ID.length() == 16) {
            for(Bank bank : AssociatedBanks.getDatabaseAboutBanks()) {
                tempCard = bank.findCardByID(ID);
                if(tempCard != null) {
                    String PIN = getClientInputString("Introduce PIN:");
                    if(PIN.length() == 4 && tempCard.getPIN().equals(PIN)) {
                        Account tempAccout = bank.findAccount(tempCard);
                        if(tempAccout != null) {
                            return tempAccout;
                        }
                    }
                }
            }
        }
        return null;
    }

    private void displayOptions() {
        System.out.println();
        System.out.println("---------------------------------------------------");
        System.out.println("----------------CHOOSE AN ACTION-------------------");
        System.out.println("---------------------------------------------------");
        System.out.println("0. EXIT");
        System.out.println("1. WITHDRAWL");
        System.out.println("2. DEPOSIT");
        System.out.println("3. CHECK BALANCE");
        System.out.println("4. TRANSFER");
    }

    private void displayHistoryOperations() {
        for(Operation it : operationsHistory) {
            System.out.println("\t" + it.toString());
        }
    }

    private double executeOperations(Account account, String bankCodeATM, double amountOfCashDisponible) {
        displayOptions();

        int clientInput = getClientInputNumber("Choose an action: ");

        while(clientInput != 0) {
            if(clientInput >= 1 && clientInput <= 4) {
                double amountTemp = switch (operations.get(clientInput - 1).getClass().getSimpleName()) {
                    case "Withdrawl" -> ((Withdrawl) operations.get(clientInput - 1)).displayOperation(account, bankCodeATM, amountOfCashDisponible);
                    case "Deposit" -> ((Deposit) operations.get(clientInput-1)).displayOperation(account, bankCodeATM);
                    case "CheckBalance" -> ((CheckBalance) operations.get(clientInput-1)).displayOperation(account, bankCodeATM);
                    case "Transfer" -> ((Transfer) operations.get(clientInput-1)).displayOperation(account, bankCodeATM);
                    default -> 0;
                };

                operationsHistory.add(operations.get(clientInput - 1));

                switch (operations.get(clientInput - 1).getClass().getSimpleName()) {
                    case "Withdrawl" -> operations.set(clientInput-1, new Withdrawl());
                    case "Deposit" -> operations.set(clientInput-1, new Deposit());
                    case "CheckBalance" -> operations.set(clientInput-1, new CheckBalance());
                    case "Transfer" -> operations.set(clientInput-1, new Transfer());
                }
                amountOfCashDisponible += amountTemp;
                displayOptions();
                clientInput = getClientInputNumber("Choose an action: ");
            }
            else {
                System.out.print("Not a valid option! Try again:");
                clientInput = getClientInputNumber(" ");
                while(clientInput < 0 || clientInput > 4) {
                    System.out.print("Not a valid option! Try again:");
                    clientInput = getClientInputNumber(" ");
                }
            }
        }
        return amountOfCashDisponible;
    }

    private String getClientInputString(String displayMessage) {
        System.out.printf("%s ", displayMessage);
        return scanner.nextLine();
    }

    private int getClientInputNumber(String displayMessage) {
        System.out.printf("%s ", displayMessage);
        return scanner.nextInt();
    }

    double application(String bankCode, double amountOfCashDisponible) {
        this.Setup();
        this.welcome();
        Account tempAccount = this.validateCredentials();
        if(tempAccount != null) {
            amountOfCashDisponible = this.executeOperations(tempAccount,bankCode,amountOfCashDisponible);
            System.out.println("\nHave a nice day!");
            System.out.println("\nThe list with the operations executed on your account:");
            if(operationsHistory.size() > 0) {
                this.displayHistoryOperations();
            }
            else {
                System.out.println("No action was found!");
            }
        }
        else {
            System.out.println("The credentials introduced are wrong!");
        }

        return amountOfCashDisponible;
    }
}
