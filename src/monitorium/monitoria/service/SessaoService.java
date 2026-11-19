package monitorium.monitoria.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import monitorium.comum.excecao.RegraNegocioException;
import monitorium.comum.sessao.UsuarioLogado;
import monitorium.comum.util.GerarId;
import monitorium.monitoria.model.Disciplina;
import monitorium.monitoria.model.SessaoMonitoria;
import monitorium.monitoria.repository.SessaoCSVRepository;
import monitorium.usuario.model.Monitor;

public class SessaoService {

    private static final int PRIMEIRO_HORARIO = 7;
    private static final int ULTIMO_HORARIO = 21;

    private final SessaoCSVRepository sessaoRepository = new SessaoCSVRepository();
    private final DisciplinaService disciplinaService = new DisciplinaService();

    public SessaoMonitoria criar(String disciplinaId, String salaId, String data, int horario, int vagas) {
        Monitor monitor = exigirMonitor();
        validarDisciplina(disciplinaId, monitor);
        validarSala(salaId);
        validarData(data);
        validarHorario(horario);
        validarVagas(vagas);
        // TODO: quando o módulo de salas do Erik existir
        //  recusar vagas maiores que a capacidade da sala 
        //  reservar a sala nesse horário pelo ReservaService

        SessaoMonitoria sessao = new SessaoMonitoria(
                GerarId.gerarId(), disciplinaId, monitor.getId(), salaId, data, horario, vagas);
        sessaoRepository.salvar(sessao);
        return sessao;
    }
    
    public SessaoMonitoria buscarSessao(String sessaoId){
        if (vazio(sessaoId)) {
            throw new RegraNegocioException("Digite corretamente o id da sessão");
        }
       SessaoMonitoria sessaoMonitoria = sessaoRepository.buscarPorId(sessaoId);
       if (sessaoMonitoria == null) {
           throw new RegraNegocioException("Sessão não encontrada.");
       }

       return sessaoMonitoria;
    }
    
    private Monitor exigirMonitor() {
        if (!(UsuarioLogado.get() instanceof Monitor)) {
            throw new RegraNegocioException("Somente o monitor pode criar sessões.");
        }
        return (Monitor) UsuarioLogado.get();
    }

    private void validarDisciplina(String disciplinaId, Monitor monitor) {
        if (vazio(disciplinaId)) {
            throw new RegraNegocioException("Escolha a disciplina.");
        }
        Disciplina disciplina = disciplinaService.buscar(disciplinaId);
        if (!disciplina.getMonitorId().equals(monitor.getId())) {
            throw new RegraNegocioException("Você não é o monitor responsável por esta disciplina.");
        }
    }

    private void validarSala(String salaId) {
        if (vazio(salaId)) {
            throw new RegraNegocioException("Escolha a sala.");
        }
    }

    private void validarData(String data) {
        if (vazio(data)) {
            throw new RegraNegocioException("Informe a data.");
        }
        try {
            LocalDate.parse(data.trim());
        } catch (DateTimeParseException e) {
            throw new RegraNegocioException("Data inválida. Use o formato 2026-12-08.");
        }
    }

    private void validarHorario(int horario) {
        if (horario < PRIMEIRO_HORARIO || horario > ULTIMO_HORARIO) {
            throw new RegraNegocioException("O horário deve ser entre 7h e 21h.");
        }
    }

    private void validarVagas(int vagas) {
        if (vagas <= 0) {
            throw new RegraNegocioException("O número de vagas deve ser maior que zero.");
        }
    }

    private boolean vazio(String texto) {
        return texto == null || texto.isBlank();
    }
}
