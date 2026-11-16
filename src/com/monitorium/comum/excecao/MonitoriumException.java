package com.monitorium.comum.excecao;

/**
 * Erro base do sistema. Herda de RuntimeException, por isso o Java não obriga
 * try/catch: quem escreve o controller precisa lembrar de capturar.
 */
public class MonitoriumException extends RuntimeException {

    public MonitoriumException(String mensagem) {
        super(mensagem);
    }

    public MonitoriumException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
