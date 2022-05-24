package com;

public enum AdditionalFees {

    WITHDRWAL(5),
    DEPOSIT(10),
    CHECK_BALANCE(3),
    TRANSFER(8);

    private final int fee;

    private AdditionalFees() {
        this.fee = 0;
    }

    private AdditionalFees(int fee) {
        this.fee = fee;
    }

    public int getFee() {
        return fee;
    }

}
