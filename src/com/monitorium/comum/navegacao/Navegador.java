package com.monitorium.comum.navegacao;

import com.monitorium.comum.excecao.MonitoriumException;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

/** Troca a tela da janela atual. Os arquivos .fxml ficam em src/main/resources/view/. */
public final class Navegador {

    private Navegador() {
    }

    /** origem é qualquer componente da tela atual (um botão, por exemplo). */
    public static void irPara(Node origem, String arquivoFxml) {
        URL local = Navegador.class.getResource("/com/monitorium/view/usuario/" + arquivoFxml);
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
