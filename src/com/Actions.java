package com;
import org.iban4j.Iban;

public interface Actions {

    Operation withdrawal(Card card, double amount) throws CloneNotSupportedException;
    Operation deposit(Card card, double amount) throws CloneNotSupportedException;
    Operation checkBalance(Card card) throws CloneNotSupportedException;
    Operation transfer(Card card, double amount, Iban toIban) throws CloneNotSupportedException;
}
