package com.supamenu.www.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

@Getter
@AllArgsConstructor
public class ConflictAlertException extends AbstractThrowableProblem {
    private Object[] args;

    public ConflictAlertException(String message) {
        super(ErrorConstants.INVALID_INPUT__EXCEPTION_KEY, message, Status.CONFLICT);
    }
}