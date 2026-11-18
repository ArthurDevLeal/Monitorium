package monitorium.usuario.model;

import monitorium.comum.excecao.RegraNegocioException;

/**
 * Qualquer pessoa que usa o sistema. Aluno, Monitor e Administrador herdam daqui.
 * A senha é guardada em texto puro (sem hash).
 */

public abstract class Usuario {

    private final String id;
    private String nome;
    private String usuario;
    private String senha;
    private String email;

    protected Usuario(String id, String nome, String usuario, String senha, String email) {
        this.id = exigir(id, "id");
        this.nome = exigir(nome, "nome");
        this.usuario = exigir(usuario, "usuário");
        this.senha = exigir(senha, "senha");
        this.email = exigir(email, "e-mail");
    }

    public abstract String getTelaInicial();

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = exigir(senha, "senha");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = exigir(email, "e-mail");
    }
}
