package monitorium.monitoria.repository;

import monitorium.comum.excecao.MonitoriumException;
import monitorium.comum.repository.CsvRepository;
import monitorium.monitoria.model.SessaoMonitoria;

public class SessaoCSVRepository extends CsvRepository<SessaoMonitoria> {

    public SessaoCSVRepository() {
        super("sessoes.csv", "id;disciplinaId;monitorId;salaId;data;horario;vagas");
    }

    @Override
    protected String[] paraCampos(SessaoMonitoria s) {
        return new String[]{
            s.getId(),
            s.getDisciplinaId(),
            s.getMonitorId(),
            s.getSalaId(),
            s.getData(),
            String.valueOf(s.getHorario()),
            String.valueOf(s.getVagas())
        };
    }

    @Override
    protected SessaoMonitoria deCampos(String[] c) {
        if (c.length != 7) {
            throw new MonitoriumException("Linha inválida em sessoes.csv: esperava 7 campos e veio " + c.length);
        }
        String id = c[0];
        String disciplinaId = c[1];
        String monitorId = c[2];
        String salaId = c[3];
        String data = c[4];
        try {
            int horario = Integer.parseInt(c[5]);
            int vagas = Integer.parseInt(c[6]);

            return new SessaoMonitoria(id, disciplinaId, monitorId, salaId, data, horario, vagas);
        } catch (NumberFormatException e) {
            throw new MonitoriumException("Horário ou vagas inválidos em sessoes.csv.");
        }
    }

    @Override
    protected String getId(SessaoMonitoria s) {
        return s.getId();
    }
}
