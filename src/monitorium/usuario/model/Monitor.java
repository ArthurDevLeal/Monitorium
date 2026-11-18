package monitorium.usuario.model;

public class Monitor extends Usuario {

    public static final String PERFIL = "MONITOR";

    public Monitor(String id, String nome, String usuario, String senha, String email) {
        super(id, nome, usuario, senha, email);
    }

    @Override
    public String getTelaInicial() {
        return "painel-monitor.fxml";
    }

    @Override
    public String getPerfil() {
        return PERFIL;
    }
}
