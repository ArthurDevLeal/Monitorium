package monitorium.usuario.model;

public class Administrador extends Usuario {

    public static final String PERFIL = "ADMINISTRADOR";

    public Administrador(String id, String nome, String usuario, String senha, String email) {
        super(id, nome, usuario, senha, email);
    }

    @Override
    public String getTelaInicial() {
        return "painel-administrador.fxml";
    }

    @Override
    public String getPerfil() {
        return PERFIL;
    }
}
