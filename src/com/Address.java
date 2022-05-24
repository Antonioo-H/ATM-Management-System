package com;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Address implements Cloneable {

    private String street;
    private String number;
    private String county;
    private String city;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
