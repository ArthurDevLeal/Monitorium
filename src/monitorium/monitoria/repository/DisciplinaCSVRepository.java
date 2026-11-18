package monitorium.monitoria.repository;


import monitorium.comum.excecao.MonitoriumException;
import monitorium.comum.repository.CsvRepository;
import monitorium.monitoria.model.Disciplina;



public class DisciplinaCSVRepository extends CsvRepository<Disciplina> {

    public DisciplinaCSVRepository() {
        super("disciplinas.csv", "id;nome;monitorId");
    }

    @Override
    protected String[] paraCampos(Disciplina d) {
        return new String[]{
            d.getId(),
            d.getNome(),
            d.getMonitorId()
        };
    }

    @Override
    protected Disciplina deCampos(String[] c) {
        if (c.length != 3) {
            throw new MonitoriumException("Linha inválida em disciplinas.csv: esperava 3 campos e veio " + c.length);
        }
        String id = c[0];
        String nome = c[1];
        String monitorId = c[2];

        return new Disciplina(id, nome, monitorId);
    }
    
    @Override
    protected String getId(Disciplina d) {
        return d.getId();
    }
}
