package monitorium.comum.excecao;


public class MonitoriumException extends RuntimeException {

    public MonitoriumException(String mensagem) {
        super(mensagem);
    }

    public MonitoriumException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
