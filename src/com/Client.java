package com;

import lombok.*;

import java.util.Random;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Client {

    private String surname;
    private String firstName;
    private boolean sex; // false - man, true - woman
    private String CNP;
    private String telephone;
    private String email;

    @Override
    public String toString() {
        return "\nClient{" +
                "\n\tsurname='" + surname + '\'' +
                "\n\tfirstName='" + firstName + '\'' +
                "\n\tCNP='" + CNP + '\'' +
                "\n\ttelephoneNumber='" + telephone + '\'' +
                "\n\temail='" + email + '\'' +
                "\n\t}";
    }

    public static String generateCNP(boolean sex) {
        Random random = new Random();
        if(!sex) {
            return String.valueOf(random.nextLong(6000000000000L - 5000000000000L) + 5000000000000L);
        }
        return String.valueOf(random.nextLong(7000000000000L - 6000000000000L) + 6000000000000L);
    }

    public static String generateTelephone() {
        Random random = new Random();
        return "07" + random.nextInt(10000000, 100000000);
    }
}
