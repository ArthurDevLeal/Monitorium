package com.monitorium.usuario.model;

import com.monitorium.comum.RegraNegocioException;

/**
 * Qualquer pessoa que usa o sistema. Aluno, Monitor e Administrador herdam daqui.
 * A senha nunca é guardada como foi digitada: só o hash SHA-256.
 */

public abstract class Usuario {

    private final String id;
    private String nome;
    private String usuario;
    private String senhaHash;
    private String email;

    protected Usuario(String id, String nome, String usuario, String senhaHash, String email) {
        this.id = exigir(id, "id");
        this.nome = exigir(nome, "nome");
        this.usuario = exigir(usuario, "usuário");
        this.senhaHash = exigir(senhaHash, "senha");
        this.email = exigir(email, "e-mail");
    }

    /** Polimorfismo: cada perfil responde com a sua tela inicial. */
    public abstract String getTelaInicial();

    /** Nome do perfil, gravado no CSV. */
    public abstract String getPerfil();

    private static String exigir(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new RegraNegocioException("O campo " + campo + " é obrigatório.");
        }
        return valor.trim();
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = exigir(nome, "nome");
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = exigir(senhaHash, "senha");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = exigir(email, "e-mail");
    }
}
