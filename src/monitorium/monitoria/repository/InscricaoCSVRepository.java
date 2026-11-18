package monitorium.monitoria.repository;

import monitorium.comum.excecao.MonitoriumException;
import monitorium.comum.repository.CsvRepository;
import monitorium.monitoria.model.Inscricao;

public class InscricaoCSVRepository extends CsvRepository<Inscricao> {

    public InscricaoCSVRepository() {
        super("inscricoes.csv", "id;alunoId;sessaoId");
    }

    @Override
    protected String[] paraCampos(Inscricao i) {
        return new String[]{
            i.getId(),
            i.getAlunoId(),
            i.getSessaoId()
        };
    }

    @Override
    protected Inscricao deCampos(String[] c) {
        if (c.length != 3) {
            throw new MonitoriumException("Linha inválida em inscricoes.csv: esperava 3 campos e veio " + c.length);
        }
        String id = c[0];
        String alunoId = c[1];
        String sessaoId = c[2];

        return new Inscricao(id, alunoId, sessaoId);
    }

    @Override
    protected String getId(Inscricao i) {
        return i.getId();
    }
}
