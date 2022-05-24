package com;
import lombok.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Operation implements Cloneable {

    private Date operationDate;
    private String operationDetails;
    private boolean successfulOperation = false;
    private double fees = 0;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
