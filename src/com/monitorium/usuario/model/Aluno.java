package com.monitorium.usuario.model;

public class Aluno extends Usuario {

    public static final String PERFIL = "ALUNO";

    public Aluno(String id, String nome, String usuario, String senhaHash, String email) {
        super(id, nome, usuario, senhaHash, email);
    }

    @Override
    public String getTelaInicial() {
        return "painel-aluno.fxml";
    }

    @Override
    public String getPerfil() {
        return PERFIL;
    }
}
