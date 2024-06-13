package com.supamenu.www.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DuplicateRecordException extends Exception {
    private String message = "exceptions.duplicateRecord";
    private Object[] args;

    public DuplicateRecordException(Object... args) {
        this.args = args;
    }
}