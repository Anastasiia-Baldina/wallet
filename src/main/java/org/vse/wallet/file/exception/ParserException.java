package org.vse.wallet.file.exception;

public class ParserException extends RuntimeException {
    public ParserException(String msg) {
        super(msg);
    }

    public ParserException(String msg, Throwable th) {
        super(msg, th);
    }
}
