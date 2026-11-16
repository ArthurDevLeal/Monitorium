package com.monitorium.usuario.service;

import java.util.UUID;

import com.monitorium.comum.excecao.RegraNegocioException;
import com.monitorium.comum.util.Senha;
import com.monitorium.comum.sessao.UsuarioLogado;
import com.monitorium.usuario.model.Administrador;
import com.monitorium.usuario.model.Aluno;
import com.monitorium.usuario.model.Monitor;
import com.monitorium.usuario.model.Usuario;
import com.monitorium.usuario.repository.UsuarioCsvRepository;

/** Regras de cadastro e login (RF01 a RF04). */
public class AuthService {

    public static final String ADMIN_USUARIO = "admin";
    public static final String ADMIN_SENHA = "admin123";

    private static final int TAMANHO_MINIMO_SENHA = 6;

    private final UsuarioCsvRepository usuarioRepository = new UsuarioCsvRepository();

    /** RF04: na primeira execução, cria o administrador padrão. */
    public void criarAdministradorInicial() {
        for (Usuario u : usuarioRepository.listarTodos()) {
            if (u instanceof Administrador) {
                return;
            }
        }
        usuarioRepository.salvar(new Administrador(
                novoId(), "Administrador", ADMIN_USUARIO, Senha.hash(ADMIN_SENHA), "admin@monitorium.local"));
    }

    /** RF01: o aluno cria a própria conta. */
    public Aluno cadastrarAluno(String nome, String usuario, String senha, String email) {
        validarDadosDeCadastro(nome, usuario, senha, email);
        Aluno aluno = new Aluno(novoId(), nome, usuario, Senha.hash(senha), email);
        usuarioRepository.salvar(aluno);
        return aluno;
    }

    /** RF03: só o administrador cadastra monitores. */
    public Monitor cadastrarMonitor(String nome, String usuario, String senha, String email) {
        if (!(UsuarioLogado.get() instanceof Administrador)) {
            throw new RegraNegocioException("Somente o administrador pode cadastrar monitores.");
        }
        validarDadosDeCadastro(nome, usuario, senha, email);
        Monitor monitor = new Monitor(novoId(), nome, usuario, Senha.hash(senha), email);
        usuarioRepository.salvar(monitor);
        return monitor;
    }

    /** RF02: confere usuário e senha. Se estiver certo, guarda quem entrou em UsuarioLogado. */
    public Usuario login(String nomeUsuario, String senha) {
        if (vazio(nomeUsuario) || senha == null || senha.isEmpty()) {
            throw new RegraNegocioException("Informe usuário e senha.");
        }
        Usuario encontrado = usuarioRepository.buscarPorNomeUsuario(nomeUsuario.trim());
        // mesma mensagem nos dois casos, para não revelar quais usuários existem
        if (encontrado == null || !Senha.confere(senha, encontrado.getSenhaHash())) {
            throw new RegraNegocioException("Usuário ou senha inválidos.");
        }
        UsuarioLogado.set(encontrado);
        return encontrado;
    }

    public void logout() {
        UsuarioLogado.limpar();
    }

    // ---------------------------------------------------------------- validação

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

    private String novoId() {
        return UUID.randomUUID().toString();
    }
}
