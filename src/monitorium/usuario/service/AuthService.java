package monitorium.usuario.service;

import monitorium.comum.excecao.RegraNegocioException;
import monitorium.comum.sessao.UsuarioLogado;
import monitorium.comum.util.GerarId;
import monitorium.usuario.model.Administrador;
import monitorium.usuario.model.Aluno;
import monitorium.usuario.model.Monitor;
import monitorium.usuario.model.Usuario;
import monitorium.usuario.repository.UsuarioCsvRepository;

public class AuthService {

    public static final String ADMIN_USUARIO = "admin";
    public static final String ADMIN_SENHA = "admin123";

    private static final int TAMANHO_MINIMO_SENHA = 6;

    private final UsuarioCsvRepository usuarioRepository = new UsuarioCsvRepository();

    public void criarAdministradorInicial() {
        for (Usuario u : usuarioRepository.listarTodos()) {
            if (u instanceof Administrador) {
                return;
            }
        }
        usuarioRepository.salvar(new Administrador(
                GerarId.gerarId(), "Administrador", ADMIN_USUARIO, ADMIN_SENHA, "admin@monitorium.local"));
    }

    public Aluno cadastrarAluno(String nome, String usuario, String senha, String email) {
        validarDadosDeCadastro(nome, usuario, senha, email);
        Aluno aluno = new Aluno(GerarId.gerarId(), nome, usuario, senha, email);
        usuarioRepository.salvar(aluno);
        return aluno;
    }

    public Monitor cadastrarMonitor(String nome, String usuario, String senha, String email) {
        if (!(UsuarioLogado.get() instanceof Administrador)) {
            throw new RegraNegocioException("Somente o administrador pode cadastrar monitores.");
        }
        validarDadosDeCadastro(nome, usuario, senha, email);
        Monitor monitor = new Monitor(GerarId.gerarId(), nome, usuario, senha, email);
        usuarioRepository.salvar(monitor);
        return monitor;
    }

    public Usuario login(String nomeUsuario, String senha) {
        if (vazio(nomeUsuario) || senha == null || senha.isEmpty()) {
            throw new RegraNegocioException("Informe usuário e senha.");
        }
        Usuario encontrado = usuarioRepository.buscarPorNomeUsuario(nomeUsuario.trim());
        if (encontrado == null || !senha.equals(encontrado.getSenha())) {
            throw new RegraNegocioException("Usuário ou senha inválidos.");
        }
        UsuarioLogado.set(encontrado);
        return encontrado;
    }

    public void logout() {
        UsuarioLogado.limpar();
    }

    private void validarDadosDeCadastro(String nome, String usuario, String senha, String email) {
        if (vazio(nome) || vazio(usuario) || vazio(email) || senha == null || senha.isEmpty()) {
            throw new RegraNegocioException("Preencha todos os campos.");
        }
        if (usuario.trim().contains(" ")) {
            throw new RegraNegocioException("O usuário não pode ter espaços.");
        }
        if (!emailValido(email.trim())) {
            throw new RegraNegocioException("E-mail inválido.");
        }
        if (senha.length() < TAMANHO_MINIMO_SENHA) {
            throw new RegraNegocioException(
                    "A senha precisa ter pelo menos " + TAMANHO_MINIMO_SENHA + " caracteres.");
        }
        if (usuarioRepository.buscarPorNomeUsuario(usuario.trim()) != null) {
            throw new RegraNegocioException("Este usuário já está em uso.");
        }
    }

    private boolean emailValido(String email) {
        int arroba = email.indexOf('@');
        return arroba > 0
                && arroba == email.lastIndexOf('@')
                && email.indexOf('.', arroba) > arroba + 1
                && !email.endsWith(".")
                && !email.contains(" ");
    }

    private boolean vazio(String texto) {
        return texto == null || texto.isBlank();
    }

}
