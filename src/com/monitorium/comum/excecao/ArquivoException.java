package com.monitorium.comum.excecao;

public class ArquivoException extends MonitoriumException {

    public ArquivoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
