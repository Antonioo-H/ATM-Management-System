package com.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ATM {

    private Address location;
    private double amountOfCashDisponible;
    private String bankCode;
    private final Menu menu;

    public ATM() {
        this.menu = Menu.getInstance();
    }

    public ATM(Address location, double amountOfCashDisponible, String bankCode) throws CloneNotSupportedException {
        this.location = (Address) location.clone();
        this.amountOfCashDisponible = amountOfCashDisponible;
        this.bankCode = bankCode;
        this.menu = Menu.getInstance();
    }

    public void runATM() {
        this.amountOfCashDisponible = menu.application(this.bankCode,this.amountOfCashDisponible);
    }
}
