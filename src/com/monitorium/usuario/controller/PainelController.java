package com.monitorium.usuario.controller;

import com.monitorium.comum.util.Mensagens;
import com.monitorium.comum.excecao.MonitoriumException;
import com.monitorium.comum.navegacao.Navegador;
import com.monitorium.comum.sessao.UsuarioLogado;
import com.monitorium.usuario.model.Usuario;
import com.monitorium.usuario.service.AuthService;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * Painel provisório, só para testar o login: mostra quem entrou e oferece "Sair".
 * Os painéis reais (aluno, monitor, administrador) substituem esses .fxml depois.
 */
public class PainelController {

    private final AuthService authService = new AuthService();

    @FXML private Label lblBoasVindas;
    @FXML private Label lblPerfil;
    @FXML private Button btnSair;

    @FXML
    private void initialize() {
        Usuario usuario = UsuarioLogado.get();
        if (usuario == null) {
            lblBoasVindas.setText("Ninguém está logado.");
            lblPerfil.setText("");
            return;
        }
        lblBoasVindas.setText("Olá, " + usuario.getNome() + "!");
        lblPerfil.setText("Perfil: " + usuario.getPerfil() + "  |  Usuário: " + usuario.getUsuario());
    }

    @FXML
    private void aoClicarSair() {
        try {
            authService.logout();
            Navegador.irPara(btnSair, "login.fxml");
        } catch (MonitoriumException e) {
            Mensagens.erro(e.getMessage());
        }
    }
}
