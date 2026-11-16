package com.monitorium.usuario.repository;

import com.monitorium.comum.CsvRepository;
import com.monitorium.comum.MonitoriumException;
import com.monitorium.usuario.model.Administrador;
import com.monitorium.usuario.model.Aluno;
import com.monitorium.usuario.model.Monitor;
import com.monitorium.usuario.model.Usuario;

/** Guarda os usuários em dados/usuarios.csv. Só lê e grava, não decide regra. */
public class UsuarioCsvRepository extends CsvRepository<Usuario> {

    public UsuarioCsvRepository() {
        super("usuarios.csv", "id;perfil;nome;usuario;senhaHash;email");
    }

    /** Busca pelo nome de login, ignorando maiúsculas. Retorna null se não existir. */
    public Usuario buscarPorNomeUsuario(String nomeUsuario) {
        for (Usuario u : listarTodos()) {
            if (u.getUsuario().equalsIgnoreCase(nomeUsuario)) {
                return u;
            }
        }
        return null;
    }

    @Override
    protected String[] paraCampos(Usuario u) {
        return new String[] {
            u.getId(),
            u.getPerfil(),
            u.getNome(),
            u.getUsuario(),
            u.getSenhaHash(),
            u.getEmail()
        };
    }

    @Override
    protected Usuario deCampos(String[] c) {
        String id = c[0];
        String perfil = c[1];
        String nome = c[2];
        String usuario = c[3];
        String senhaHash = c[4];
        String email = c[5];

        switch (perfil) {
            case Aluno.PERFIL:
                return new Aluno(id, nome, usuario, senhaHash, email);
            case Monitor.PERFIL:
                return new Monitor(id, nome, usuario, senhaHash, email);
            case Administrador.PERFIL:
                return new Administrador(id, nome, usuario, senhaHash, email);
            default:
                throw new MonitoriumException("Perfil desconhecido em usuarios.csv: " + perfil);
        }
    }

    @Override
    protected String getId(Usuario u) {
        return u.getId();
    }
}
