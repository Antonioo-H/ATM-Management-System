package com;
import lombok.*;
import org.iban4j.Iban;
import java.util.ArrayList;
import java.util.Locale;

@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Bank {

    private String bankName;
    private String bankTelephone;
    private String email;
    private String website;
    private ArrayList<BankBranch> bankBranches;

    public Bank(String bankName, String bankTelephone, String email, String website, ArrayList<BankBranch> bankBranches) throws CloneNotSupportedException {
        this.bankName = bankName.toUpperCase(Locale.ROOT);
        this.bankTelephone = bankTelephone;
        this.email = email;
        this.website = website;

        this.bankBranches = new ArrayList<>();
        for (BankBranch bankBranch : bankBranches) {
            this.bankBranches.add((BankBranch) bankBranch.clone());
        }
    }

    Account findAccount(Card card) {
        Account tempAccount;
        for(BankBranch branch : bankBranches) {
            tempAccount = null;
            tempAccount = branch.findAccount(card);
            if(tempAccount != null) {
                return branch.findAccount(card);
            }
        }
        return null;
    }

    Account findAccount(Iban iban) {
        Account tempAccount;
        for(BankBranch branch : bankBranches) {
            tempAccount = null;
            tempAccount = branch.findAccount(iban);
            if(tempAccount != null) {
                return branch.findAccount(iban);
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
}