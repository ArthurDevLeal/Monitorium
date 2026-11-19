package monitorium.monitoria.service;

import java.util.ArrayList;
import java.util.List;
import monitorium.comum.excecao.ArquivoException;
import monitorium.comum.excecao.RegraNegocioException;
import monitorium.comum.sessao.UsuarioLogado;
import monitorium.comum.util.GerarId;
import monitorium.monitoria.model.Disciplina;
import monitorium.monitoria.repository.DisciplinaCSVRepository;
import monitorium.usuario.model.Administrador;

public class DisciplinaService {

    private final DisciplinaCSVRepository disciplinaRepository = new DisciplinaCSVRepository();

    public Disciplina criar(String nome, String monitorId) {
        exigirAdministrador();
        validarNome(nome, null);
        validarMonitor(monitorId);

        Disciplina disciplina = new Disciplina(GerarId.gerarId(), nome, monitorId);
        disciplinaRepository.salvar(disciplina);
        return disciplina;
    }

    public List<Disciplina> listar() {
        List<Disciplina> disciplinas = new ArrayList<>();
        for (Disciplina d : disciplinaRepository.listarTodos()) {
            disciplinas.add(d);
        }
        return disciplinas;
    }

    public void remover(String id) {
        try {
            disciplinaRepository.remover(id);
        } catch (ArquivoException e) {
            throw new ArquivoException("Erro ao remover disciplina", e);
        }
    }

    public void alterarNome(String id, String novoNome) {
        exigirAdministrador();
        Disciplina disciplina = buscar(id);
        validarNome(novoNome, id);

        disciplina.setNome(novoNome);
        disciplinaRepository.salvar(disciplina);
    }

    public void alterarMonitor(String id, String novoMonitorId) {
        exigirAdministrador();
        Disciplina disciplina = buscar(id);
        validarMonitor(novoMonitorId);

        disciplina.setMonitorId(novoMonitorId);
        disciplinaRepository.salvar(disciplina);
    }

    public Disciplina buscar(String id) {
        Disciplina disciplina = disciplinaRepository.buscarPorId(id);
        if (disciplina == null) {
            throw new RegraNegocioException("Disciplina não encontrada.");
        }
        return disciplina;
    }

    private void exigirAdministrador() {
        if (!(UsuarioLogado.get() instanceof Administrador)) {
            throw new RegraNegocioException("Somente o administrador pode alterar disciplinas.");
        }
    }

    private void validarNome(String nome, String idAtual) {
        if (vazio(nome)) {
            throw new RegraNegocioException("Informe o nome da disciplina.");
        }
        for (Disciplina d : disciplinaRepository.listarTodos()) {
            boolean outraDisciplina = !d.getId().equals(idAtual);
            if (outraDisciplina && d.getNome().equalsIgnoreCase(nome.trim())) {
                throw new RegraNegocioException("Já existe uma disciplina com esse nome.");
            }
        }
    }

    private void validarMonitor(String monitorId) {
        if (vazio(monitorId)) {
            // TODO: aplicar a verificação se existe um monitor com esse ID
            throw new RegraNegocioException("Escolha o monitor responsável.");
        }
    }

    private boolean vazio(String texto) {
        return texto == null || texto.isBlank();
    }
}
