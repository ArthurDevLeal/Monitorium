package monitorium.comum.navegacao;

import monitorium.comum.excecao.MonitoriumException;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

public final class Navegador {

    private Navegador() {
    }

    public static void irPara(Node origem, String arquivoFxml) {
        URL local = Navegador.class.getResource("/monitorium/view/usuario/" + arquivoFxml);
        if (local == null) {
            throw new MonitoriumException("Tela não encontrada: " + arquivoFxml);
        }
        try {
            Parent raiz = new FXMLLoader(local).load();
            origem.getScene().setRoot(raiz);
        } catch (IOException e) {
            throw new MonitoriumException("Não foi possível abrir a tela " + arquivoFxml, e);
        }
    }
}
