package com.supamenu.www.exceptions;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class InternalServerErrorAlertException extends AbstractThrowableProblem {
    public InternalServerErrorAlertException(String message) {
        super(ErrorConstants.DEFAULT_TYPE, message, Status.INTERNAL_SERVER_ERROR);
    }
}