package com.model;

import lombok.*;
import org.iban4j.CountryCode;
import org.iban4j.Iban;

import java.text.SimpleDateFormat;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BankBranch implements Cloneable {

    private Address bankBranchAddress;
    private String bankBranchTelephone;
    private String bankCode;
    private record confidentialInformation(Client client, Account account, Card card) {}
    private ArrayList<confidentialInformation> database;


    public BankBranch(Address bankBranchAddress, String bankBranchTelephone, String bankCode) throws CloneNotSupportedException {
        this.bankBranchAddress = (Address) bankBranchAddress.clone();
        this.bankBranchTelephone = bankBranchTelephone;
        this.bankCode = bankCode;
        database = new ArrayList<>();
    }

    private Card newCard(Client client, Account account) throws CloneNotSupportedException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, 4);
        date = c.getTime();
        Card newCard = new Card(BankCode.getEnumByString(bankCode.toUpperCase()),generateIDCard(),client.getSurname() + " " + client.getFirstName(), formatter.format(date), BankBranch.generateCvv(), "1234");
        return (Card) newCard.clone();
    }

    public void becomeClient(Client client, double amount) throws CloneNotSupportedException {

        Account newAccount = new Account();
        Iban newIban = new Iban.Builder().countryCode(CountryCode.RO).bankCode(bankCode).buildRandom();
        newAccount.setIban(newIban);
        newAccount.setBalance(amount);
        Card newCard = newCard(client, newAccount);
        confidentialInformation ob = new confidentialInformation(client, newAccount, newCard);
        database.add(ob);
    }

    public Client findClient(String CNP) {
        for(confidentialInformation iterator : database) {
            if(iterator.client.getCNP().equals(CNP)) {
                return iterator.client;
            }
        }
        return null;
    }

    public ArrayList<Card> findCard(String CNP) {
        ArrayList<Card> cards = null;
        int ok = 0;
        for(confidentialInformation iterator : database) {
            if(iterator.client.getCNP().equals(CNP)) {
                if(ok == 0) {
                    cards = new ArrayList<>();
                    cards.add(iterator.card);
                    ok = 1;
                }
                else {
                    cards.add(iterator.card);
                }
            }
        }
        return cards;
    }

    public ArrayList<Account> findAccount(String CNP) {
        ArrayList<Account> accounts = null;
        int ok = 0;
        for(confidentialInformation iterator : database) {
            if(iterator.client.getCNP().equals(CNP)) {
                if(ok == 0) {
                    accounts = new ArrayList<>();
                    accounts.add(iterator.account);
                    ok = 1;
                }
                else {
                    accounts.add(iterator.account);
                }
            }
        }
        return accounts;
    }

    public Account findAccount(Iban iban) {
        for(confidentialInformation iterator : database) {
            if(iterator.account.getIban().equals(iban)) {
                return iterator.account;
            }
        }
        return null;
    }

    public Account findAccount(Card card) {
        for(confidentialInformation iterator : database) {
            if(iterator.card.equals(card)) {
                return iterator.account;
            }
        }
        return null;
    }

    public Card findCardByID(String ID) {

        for(confidentialInformation iterator : database) {
            if(iterator.card.getCardNumber().equals(ID)) {
                return iterator.card;
            }
        }
        return null;
    }

    public static String generateTelephone() {
        Random random = new Random();

        return "07" + random.nextInt(10000000, 100000000);
    }

    public static String generateIDCard() {
        Random random = new Random();

        return Long.toString(random.nextLong(1000000000000000L, 10000000000000000L)); // 16 digits
    }

    public static String generatePinCode() {
        Random random = new Random();

        return Integer.toString(random.nextInt(1000, 10000)); // 4 digits
    }

    public static String generateCvv() {
        Random random = new Random();

        return Integer.toString(random.nextInt(100, 1000)); // 3 digits
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        BankBranch bankBranchClone = (BankBranch)super.clone();
        bankBranchClone.setBankBranchAddress((Address) bankBranchClone.getBankBranchAddress().clone());
        bankBranchClone.setDatabase((ArrayList<confidentialInformation>) bankBranchClone.getDatabase().clone());
        return bankBranchClone;
    }
}
