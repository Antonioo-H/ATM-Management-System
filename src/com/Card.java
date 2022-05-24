package com;
import lombok.*;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public final class Card implements Cloneable {

    private final String bankName;
    private final String cardNumber;
    private final String cardHolder;
    private final String validThru;
    private final String cvvCode;
    private String PIN;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
