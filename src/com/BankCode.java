package com;

public enum BankCode {

    BANCA_TRANSILVANIA("BTRL"),
    BCR("RNCB"),
    BRD("BRDE"),
    CEC_BANK("CECE"),
    ING_BANK("INGB"),
    RAIFFEISEN_BANK("RZBR"),
    UNICREDIT_BANK("BACX");

    private final String bankCode;

    private BankCode() {
        this.bankCode = null;
    }

    private BankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public static String getEnumByString(String bankCode) {
        for(BankCode e : BankCode.values()) {
            if(e.getBankCode().equals(bankCode)) {
                return e.name();
            }
        }
        return null;
    }

}
