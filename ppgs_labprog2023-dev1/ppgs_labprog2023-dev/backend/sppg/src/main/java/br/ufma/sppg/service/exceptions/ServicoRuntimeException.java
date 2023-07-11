package br.ufma.sppg.service.exceptions;

public class ServicoRuntimeException extends RuntimeException {
    public ServicoRuntimeException(String msg) {
        super(msg);
    }
}
