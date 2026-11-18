package monitorium.app;

import java.io.IOException;

import monitorium.comum.util.Mensagens;
import monitorium.comum.excecao.MonitoriumException;
import monitorium.usuario.service.AuthService;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage janela) throws IOException {
        try {
            new AuthService().criarAdministradorInicial();
        } catch (MonitoriumException e) {
            Mensagens.erro(e.getMessage());
        }

        Parent raiz = new FXMLLoader(Main.class.getResource("/monitorium/view/usuario/login.fxml")).load();
        janela.setTitle("Monitorium");
        janela.setScene(new Scene(raiz, 460, 440));
        janela.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
