package com;

import com.model.*;

import java.util.*;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {

        Client client1 = new Client("Ionescu", "Mihai", false, "5010905123154", Client.generateTelephone(), "ionescumihai@gmail.com");
        Client client2 = new Client("Popescu", "Maria", true, "6015948752148", Client.generateTelephone(), "maria.popescu@gmail.com");
        Client client3 = new Client("Chiriac", "George", false, "5020227441854", Client.generateTelephone(), "george.chiriac@gmail.com");
        Client client4 = new Client("Dan", "Claudia", true, "6030628043646", Client.generateTelephone(), "claudia.dan@gmail.com");
        Client client5 = new Client("Stoiciu", "Alex", false, "5000807031490", Client.generateTelephone(), "stoiciualexx@gmail.com");
        Client client6 = new Client("Alexandru", "Diana", true, "6031225069180", Client.generateTelephone(), "diana.alex@gmail.com");
        Client client7 = new Client("Georgescu", "Eva", true, "6031129023399", Client.generateTelephone(), "evva_georgescu@gmail.com");
        Client client8 = new Client("Baban", "Madalin", false, "5020816429161", Client.generateTelephone(), "madalin.baban@gmail.com");
        Client client9 = new Client("Ardelean", "Paul", false, Client.generateCNP(false), Client.generateTelephone(), "paul.ardelean@gmail.com");
        Client client10 = new Client("Badulescu", "Sonia", true, Client.generateCNP(true), Client.generateTelephone(), "sonia.badulescu@gmail.com");
        Client client11 = new Client("Caramitru", "Ciprian", false, Client.generateCNP(false), Client.generateTelephone(), "caramitru.ciprian@gmail.com");
        Client client12 = new Client("Dulgheru", "Lucian", false, Client.generateCNP(false), Client.generateTelephone(), "lucian01.dulgheru@gmail.com");
        Client client13 = new Client("Lascu", "Oana", true, Client.generateCNP(true), Client.generateTelephone(), "l.a.s.c.u.-oana@gmail.com");
        Client client14 = new Client("Ghita", "Bianca", true, Client.generateCNP(true), Client.generateTelephone(), "biancaGh05@gmail.com");
        Client client15 = new Client("Burtea", "Ana", true, Client.generateCNP(true), Client.generateTelephone(), "ana.burtea@gmail.com");

        Address a1 = new Address("Soarelui", "8", "Bucuresti", "Sector 3");
        Address a2 = new Address("Mihai-Voda", "54", "Bucuresti", "Sector 5");
        Address a3 = new Address("Calea Dobrogei", "23", "Constanta", "Navodari");
        Address a4 = new Address("Furnicii", "11", "Arad", "Nadlac");
        Address a5 = new Address("Violetelor", "2", "Iasi", "Targu Frumos");

        BankBranch bcrDristor = new BankBranch(a1, "0778679760", BankCode.BCR.getBankCode());
        BankBranch bcrCotroceni = new BankBranch(a2, "0778679760", BankCode.BCR.getBankCode());
        BankBranch transilvaniaNavodari = new BankBranch(a3, "0778679760", BankCode.BANCA_TRANSILVANIA.getBankCode());
        BankBranch brdNadlac = new BankBranch(a4, "0778679760", BankCode.BRD.getBankCode());
        BankBranch ingTgFrumos = new BankBranch(a5, "0778679760", BankCode.ING_BANK.getBankCode());

        bcrDristor.becomeClient(client1, 2000d);
        bcrDristor.becomeClient(client2, 19200d);
        bcrDristor.becomeClient(client3, 500.5d);
        bcrCotroceni.becomeClient(client4, 985d);
        bcrCotroceni.becomeClient(client5, 7600d);
        bcrCotroceni.becomeClient(client6, 2548.74d);
        transilvaniaNavodari.becomeClient(client7, 5555d);
        transilvaniaNavodari.becomeClient(client8, 897.47d);
        transilvaniaNavodari.becomeClient(client9,1000d);
        brdNadlac.becomeClient(client10,8000d);
        brdNadlac.becomeClient(client11, 7700d);
        brdNadlac.becomeClient(client12, 200d);
        ingTgFrumos.becomeClient(client13,70d);
        ingTgFrumos.becomeClient(client14, 987d);
        ingTgFrumos.becomeClient(client15, 9000d);

        Bank BCR = new Bank("BCR", "0724316397", "bcr@gmail.com", "www.bcr.ro", new ArrayList<>(List.of(bcrDristor,bcrCotroceni)));
        Bank BT = new Bank("BANCA_TRANSILVANIA", "0744430345 ", "bt@gmail.com", "www.bt.ro", new ArrayList<>(List.of(transilvaniaNavodari)));
        Bank BRD = new Bank("BRD", "0735166997", "brd@gmail.com", "www.brd.ro", new ArrayList<>(List.of(brdNadlac)));
        Bank ING = new Bank("ING_BANK", "0720441797 ", "ing@gmail.com", "www.ing.ro", new ArrayList<>(List.of(ingTgFrumos)));

        AssociatedBanks.addBank(BCR);
        AssociatedBanks.addBank(BT);
        AssociatedBanks.addBank(BRD);
        AssociatedBanks.addBank(ING);

        Address a6 = new Address("Energeticienilor", "21", "Bucuresti", "Sector 3");
        ATM atmBT = new ATM(a6, 100000d, BankCode.BANCA_TRANSILVANIA.getBankCode());

        System.out.println(BCR.findCardByCNP("5010905123154").getCardNumber());
        System.out.println(BCR.findAccount(BCR.findCardByCNP("6015948752148")).getIban());

        atmBT.runATM();

        System.out.println();
        System.out.println("Disponibile cash ATM: " + atmBT.getAmountOfCashDisponible());
        System.out.println(BCR.findAccount(BCR.findCardByCNP("6015948752148")).getBalance());

    }
}