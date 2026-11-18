package monitorium.usuario.model;

import monitorium.comum.excecao.RegraNegocioException;
import monitorium.comum.util.Exigir;

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
        this.id = Exigir.exigir(id, "id");
        this.nome = Exigir.exigir(nome, "nome");
        this.usuario = Exigir.exigir(usuario, "usuário");
        this.senha = Exigir.exigir(senha, "senha");
        this.email = Exigir.exigir(email, "e-mail");
    }

    public abstract String getTelaInicial();

    public abstract String getPerfil();

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = Exigir.exigir(nome, "nome");
    }

    public String getUsuario() {
        return usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = Exigir.exigir(senha, "senha");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Exigir.exigir(email, "e-mail");
    }
}
