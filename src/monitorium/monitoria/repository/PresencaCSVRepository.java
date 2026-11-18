package monitorium.monitoria.repository;

import monitorium.comum.excecao.MonitoriumException;
import monitorium.comum.repository.CsvRepository;
import monitorium.monitoria.model.Presenca;

public class PresencaCSVRepository extends CsvRepository<Presenca> {

    public PresencaCSVRepository() {
        super("presencas.csv", "id;alunoId;sessaoId;presente");
    }

    @Override
    protected String[] paraCampos(Presenca p) {
        return new String[]{
            p.getId(),
            p.getAlunoId(),
            p.getSessaoId(),
            String.valueOf(p.isPresente())
        };
    }

    @Override
    protected Presenca deCampos(String[] c) {
        if (c.length != 4) {
            throw new MonitoriumException("Linha inválida em presencas.csv: esperava 4 campos e veio " + c.length);
        }
        String id = c[0];
        String alunoId = c[1];
        String sessaoId = c[2];
        boolean presente = Boolean.parseBoolean(c[3]);

        Presenca p = new Presenca(id, alunoId, sessaoId);
        if (presente) {
            p.marcarPresença();
        }
        return p;
    }

    @Override
    protected String getId(Presenca p) {
        return p.getId();
    }
}
