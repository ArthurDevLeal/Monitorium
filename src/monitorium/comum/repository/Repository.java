package monitorium.comum.repository;

import java.util.List;

/** Contrato de todo repository do sistema. T é o tipo do objeto guardado. */
public interface Repository<T> {

    List<T> listarTodos();

    /** Retorna null se não encontrar. */
    T buscarPorId(String id);

    /** Cria o objeto, ou atualiza se já existir um com o mesmo id. */
    void salvar(T objeto);

    void remover(String id);
}
