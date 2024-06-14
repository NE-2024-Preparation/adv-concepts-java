package com.supamenu.www.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
@Getter
@AllArgsConstructor
public class UnAuthorizedAlertException extends RuntimeException {
    private String message = "exceptions.unauthorized";
    private Object[] args;

    public UnAuthorizedAlertException(Object... args) {
        super("Message");
        this.args = args;
    }
}