package br.ufma.sppg.services.exceptions;

public class ServicoRuntimeException extends RuntimeException {
    public ServicoRuntimeException(String msg) {
        super(msg);
    }
}