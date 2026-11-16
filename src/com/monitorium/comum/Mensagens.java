package com.monitorium.comum;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/** Caixas de mensagem prontas para os controllers. */
public final class Mensagens {

    private Mensagens() {
    }

    public static void sucesso(String texto) {
        mostrar(AlertType.INFORMATION, "Sucesso", texto);
    }

    public static void erro(String texto) {
        mostrar(AlertType.ERROR, "Erro", texto);
    }

    private static void mostrar(AlertType tipo, String titulo, String texto) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(texto);
        alerta.showAndWait();
    }
}
