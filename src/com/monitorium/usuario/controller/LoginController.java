package com.monitorium.usuario.controller;

import com.monitorium.comum.util.Mensagens;
import com.monitorium.comum.excecao.MonitoriumException;
import com.monitorium.comum.navegacao.Navegador;
import com.monitorium.usuario.model.Usuario;
import com.monitorium.usuario.service.AuthService;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

/** Tela de login e cadastro de aluno. Só lê os campos, chama o service e mostra o resultado. */
public class LoginController {

    private final AuthService authService = new AuthService();

    @FXML private TabPane abas;

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtSenha;

    @FXML private TextField txtCadNome;
    @FXML private TextField txtCadUsuario;
    @FXML private TextField txtCadEmail;
    @FXML private PasswordField txtCadSenha;
    @FXML private PasswordField txtCadConfirmar;

    @FXML
    private void aoClicarEntrar() {
        try {
            Usuario usuario = authService.login(txtUsuario.getText(), txtSenha.getText());
            Navegador.irPara(txtUsuario, usuario.getTelaInicial());
        } catch (MonitoriumException e) {
            Mensagens.erro(e.getMessage());
        }
    }

    @FXML
    private void aoClicarCadastrar() {
        try {
            if (!txtCadSenha.getText().equals(txtCadConfirmar.getText())) {
                Mensagens.erro("As senhas não são iguais.");
                return;
            }
            authService.cadastrarAluno(
                    txtCadNome.getText(),
                    txtCadUsuario.getText(),
                    txtCadSenha.getText(),
                    txtCadEmail.getText());

            Mensagens.sucesso("Conta criada! Agora é só entrar.");
            txtUsuario.setText(txtCadUsuario.getText().trim());
            limparCadastro();
            txtSenha.clear();
            abas.getSelectionModel().select(0);
        } catch (MonitoriumException e) {
            Mensagens.erro(e.getMessage());
        }
    }

    private void limparCadastro() {
        txtCadNome.clear();
        txtCadUsuario.clear();
        txtCadEmail.clear();
        txtCadSenha.clear();
        txtCadConfirmar.clear();
    }
}
