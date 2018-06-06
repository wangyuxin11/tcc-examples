package org.tcc.examples.core.repository;

public class TransactionIOException extends RuntimeException {

    private static final long serialVersionUID = 6508064607297986329L;

    public TransactionIOException(String message) {
        super(message);
    }

    public TransactionIOException(Throwable e) {
        super(e);
    }
}
