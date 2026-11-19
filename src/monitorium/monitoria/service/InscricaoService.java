package monitorium.monitoria.service;

import java.util.ArrayList;
import java.util.List;
import monitorium.comum.excecao.RegraNegocioException;
import monitorium.comum.sessao.UsuarioLogado;
import monitorium.comum.util.GerarId;
import monitorium.monitoria.model.Inscricao;
import monitorium.monitoria.model.SessaoMonitoria;
import monitorium.monitoria.repository.InscricaoCSVRepository;
import monitorium.usuario.model.Aluno;

public class InscricaoService {

    private final InscricaoCSVRepository inscricaoRepository = new InscricaoCSVRepository();
    private final SessaoService sessaoService = new SessaoService();

    public Inscricao inscrever(String sessaoId) {
        Aluno aluno = exigirAluno();
        SessaoMonitoria sessao = sessaoService.buscarSessao(sessaoId);
        validarSessaoAberta(sessao);
        validarNaoInscrito(aluno, sessao);
        validarVagas(sessao);

        Inscricao inscricao = new Inscricao(GerarId.gerarId(), aluno.getId(), sessao.getId());
        inscricaoRepository.salvar(inscricao);
        return inscricao;
    }

    public void cancelar(String inscricaoId) {
        Aluno aluno = exigirAluno();
        Inscricao inscricao = buscar(inscricaoId);
        if (!inscricao.getAlunoId().equals(aluno.getId())) {
            throw new RegraNegocioException("Você só pode cancelar as suas inscrições.");
        }
        validarSessaoAberta(sessaoService.buscarSessao(inscricao.getSessaoId()));

        inscricaoRepository.remover(inscricao.getId());
    }

    public Inscricao buscar(String id) {
        Inscricao inscricao = inscricaoRepository.buscarPorId(id);
        if (inscricao == null) {
            throw new RegraNegocioException("Inscrição não encontrada.");
        }
        return inscricao;
    }

    public List<Inscricao> listarPorSessao(String sessaoId) {
        List<Inscricao> inscricoes = new ArrayList<>();
        for (Inscricao i : inscricaoRepository.listarTodos()) {
            if (i.getSessaoId().equals(sessaoId)) {
                inscricoes.add(i);
            }
        }
        return inscricoes;
    }

    public List<Inscricao> listarPorAluno(String alunoId) {
        List<Inscricao> inscricoes = new ArrayList<>();
        for (Inscricao i : inscricaoRepository.listarTodos()) {
            if (i.getAlunoId().equals(alunoId)) {
                inscricoes.add(i);
            }
        }
        return inscricoes;
    }

    private Aluno exigirAluno() {
        if (!(UsuarioLogado.get() instanceof Aluno)) {
            throw new RegraNegocioException("Somente alunos podem se inscrever em sessões.");
        }
        return (Aluno) UsuarioLogado.get();
    }

    private void validarSessaoAberta(SessaoMonitoria sessao) {
        if (sessao.jaComecou()) {
            throw new RegraNegocioException("Esta sessão já começou.");
        }
    }

    private void validarNaoInscrito(Aluno aluno, SessaoMonitoria sessao) {
        for (Inscricao i : listarPorSessao(sessao.getId())) {
            if (i.getAlunoId().equals(aluno.getId())) {
                throw new RegraNegocioException("Você já está inscrito nesta sessão.");
            }
        }
    }

    private void validarVagas(SessaoMonitoria sessao) {
        if (listarPorSessao(sessao.getId()).size() >= sessao.getVagas()) {
            throw new RegraNegocioException("Esta sessão está lotada.");
        }
    }
}
