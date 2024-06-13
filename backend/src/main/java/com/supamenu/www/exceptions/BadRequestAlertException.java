package com.supamenu.www.exceptions;

import lombok.Getter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.io.Serial;

@Getter
public class BadRequestAlertException extends AbstractThrowableProblem {

    @Serial
    private static final long serialVersionUID = 1L;
    private final String entityName;

    private Object[] args;

    private final String errorKey;


    public BadRequestAlertException(String customMessage) {
        super(ErrorConstants.INVALID_INPUT__EXCEPTION_KEY, customMessage, Status.BAD_REQUEST);
        this.entityName = "";
        this.errorKey = String.valueOf(Status.BAD_REQUEST);
    }

}