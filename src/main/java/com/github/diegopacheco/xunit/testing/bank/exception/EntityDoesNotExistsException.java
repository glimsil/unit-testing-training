package com.github.diegopacheco.xunit.testing.bank.exception;

public class EntityDoesNotExistsException extends Exception{
    public EntityDoesNotExistsException(String message) {
        super(message);
    }
}
