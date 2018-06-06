package org.tcc.examples.core;

public class CancellingException extends RuntimeException {

    public CancellingException(Throwable cause) {
        super(cause);
    }
}
