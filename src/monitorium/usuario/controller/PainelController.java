package monitorium.usuario.controller;

import monitorium.comum.util.Mensagens;
import monitorium.comum.excecao.MonitoriumException;
import monitorium.comum.navegacao.Navegador;
import monitorium.comum.sessao.UsuarioLogado;
import monitorium.usuario.model.Usuario;
import monitorium.usuario.service.AuthService;

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
