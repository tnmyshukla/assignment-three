package com.assignment.exceptions;

/**
 * This class extends runtime exception and implements custom exception.
 */
public class InvalidInputException extends RuntimeException{
    /**
     * Constructor of custom runtime exception
     * @param message to be printed on console
     */
    public InvalidInputException(final String message){
        super(message);
    }
}
