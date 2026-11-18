package monitorium.usuario.repository;

import monitorium.comum.repository.CsvRepository;
import monitorium.comum.excecao.MonitoriumException;
import monitorium.usuario.model.Administrador;
import monitorium.usuario.model.Aluno;
import monitorium.usuario.model.Monitor;
import monitorium.usuario.model.Usuario;

public class UsuarioCsvRepository extends CsvRepository<Usuario> {

    public UsuarioCsvRepository() {
        super("usuarios.csv", "id;perfil;nome;usuario;senha;email");
    }

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
            u.getSenha(),
            u.getEmail()
        };
    }

    @Override
    protected Usuario deCampos(String[] c) {
        String id = c[0];
        String perfil = c[1];
        String nome = c[2];
        String usuario = c[3];
        String senha = c[4];
        String email = c[5];

        switch (perfil) {
            case Aluno.PERFIL:
                return new Aluno(id, nome, usuario, senha, email);
            case Monitor.PERFIL:
                return new Monitor(id, nome, usuario, senha, email);
            case Administrador.PERFIL:
                return new Administrador(id, nome, usuario, senha, email);
            default:
                throw new MonitoriumException("Perfil desconhecido em usuarios.csv: " + perfil);
        }
    }

    @Override
    protected String getId(Usuario u) {
        return u.getId();
    }
}
