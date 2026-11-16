package com.monitorium.comum.excecao;

/** O usuário pediu algo que o sistema não permite, ou preencheu algo errado. */
public class RegraNegocioException extends MonitoriumException {

    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
