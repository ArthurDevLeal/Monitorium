package com.monitorium.app;

import java.io.IOException;

import com.monitorium.comum.Mensagens;
import com.monitorium.comum.MonitoriumException;
import com.monitorium.usuario.service.AuthService;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** Abre o programa: garante o administrador inicial e mostra a tela de login. */
public class Main extends Application {

    @Override
    public void start(Stage janela) throws IOException {
        try {
            new AuthService().criarAdministradorInicial();
        } catch (MonitoriumException e) {
            Mensagens.erro(e.getMessage());
        }

        Parent raiz = new FXMLLoader(Main.class.getResource("/com/monitorium/view/usuario/login.fxml")).load();
        janela.setTitle("Monitorium");
        janela.setScene(new Scene(raiz, 460, 440));
        janela.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
