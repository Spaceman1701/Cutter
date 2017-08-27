package org.x2a.cutter.processor;

public class CutterCompileException extends RuntimeException {

    public CutterCompileException(String message, Throwable cause) {
        super(message, cause);
    }

    public CutterCompileException(String message) {
        super(message);
    }
}
