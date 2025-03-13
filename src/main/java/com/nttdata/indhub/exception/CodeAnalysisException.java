package com.nttdata.indhub.exception;

/**
 * Excepción personalizada para errores específicos durante el análisis de código.
 */
public class CodeAnalysisException extends RuntimeException {

    // Constructor sin argumentos
    public CodeAnalysisException() {
        super();
    }

    // Constructor con mensaje de error
    public CodeAnalysisException(String message) {
        super(message);
    }

    // Constructor con mensaje de error y causa (excepción original)
    public CodeAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor con causa (excepción original)
    public CodeAnalysisException(Throwable cause) {
        super(cause);
    }
}
