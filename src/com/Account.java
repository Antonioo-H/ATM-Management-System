package com;

import lombok.*;
import org.iban4j.Iban;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Account {

    private Iban iban;
    private double balance;
}