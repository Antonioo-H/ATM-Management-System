package com.model;

import java.util.Date;

public abstract class Operation {

    protected Date operationDate;
    protected String operationDetails;
    protected boolean successfulOperation = false;
    protected double fees = 0;
}
