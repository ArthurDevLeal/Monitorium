package com.monitorium.comum;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Base de todos os repositories. Guarda um objeto por linha em dados/NOME.csv,
 * com os campos separados por ponto e vírgula.
 *
 * Textos que contêm ; aspas ou quebra de linha são protegidos entre aspas
 * (padrão CSV), então ninguém precisa usar split(";") por conta própria.
 *
 * Quem herda só ensina a transformar o objeto em campos e o contrário.
 */
public abstract class CsvRepository<T> implements Repository<T> {

    private static final char SEPARADOR = ';';
    private static final Path PASTA_DADOS = Paths.get("dados");

    private final Path arquivo;
    private final String cabecalho;

    protected CsvRepository(String nomeArquivo, String cabecalho) {
        this.arquivo = PASTA_DADOS.resolve(nomeArquivo);
        this.cabecalho = cabecalho;
        garantirArquivo();
    }

    /** Transforma o objeto em campos de texto, na ordem do cabeçalho. */
    protected abstract String[] paraCampos(T objeto);

    /** Faz o caminho contrário: monta o objeto a partir dos campos. */
    protected abstract T deCampos(String[] campos);

    protected abstract String getId(T objeto);

    @Override
    public synchronized List<T> listarTodos() {
        List<String[]> registros = lerRegistros();
        List<T> lista = new ArrayList<>();
        // índice 0 é o cabeçalho
        for (int i = 1; i < registros.size(); i++) {
            lista.add(deCampos(registros.get(i)));
        }
        return lista;
    }

    @Override
    public synchronized T buscarPorId(String id) {
        for (T objeto : listarTodos()) {
            if (getId(objeto).equals(id)) {
                return objeto;
            }
        }
        return null;
    }

    @Override
    public synchronized void salvar(T objeto) {
        List<T> lista = listarTodos();
        boolean atualizou = false;
        for (int i = 0; i < lista.size(); i++) {
            if (getId(lista.get(i)).equals(getId(objeto))) {
                lista.set(i, objeto);
                atualizou = true;
                break;
            }
        }
        if (!atualizou) {
            lista.add(objeto);
        }
        gravarTodos(lista);
    }

    @Override
    public synchronized void remover(String id) {
        List<T> lista = listarTodos();
        lista.removeIf(objeto -> getId(objeto).equals(id));
        gravarTodos(lista);
    }

    // ---------------------------------------------------------------- arquivo

    private void garantirArquivo() {
        try {
            Files.createDirectories(PASTA_DADOS);
            if (!Files.exists(arquivo)) {
                Files.writeString(arquivo, cabecalho + "\n", StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new ArquivoException("Não foi possível criar o arquivo de dados.", e);
        }
    }

    private void gravarTodos(List<T> lista) {
        StringBuilder texto = new StringBuilder(cabecalho).append('\n');
        for (T objeto : lista) {
            String[] campos = paraCampos(objeto);
            for (int i = 0; i < campos.length; i++) {
                if (i > 0) {
                    texto.append(SEPARADOR);
                }
                texto.append(proteger(campos[i]));
            }
            texto.append('\n');
        }
        try {
            Files.writeString(arquivo, texto.toString(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new ArquivoException("Não foi possível salvar os dados.", e);
        }
    }

    private String proteger(String campo) {
        if (campo == null) {
            return "";
        }
        boolean precisaProteger = campo.indexOf(SEPARADOR) >= 0
                || campo.indexOf('"') >= 0
                || campo.indexOf('\n') >= 0
                || campo.indexOf('\r') >= 0;
        if (!precisaProteger) {
            return campo;
        }
        return "\"" + campo.replace("\"", "\"\"") + "\"";
    }

    /** Lê o arquivo inteiro e devolve uma lista de registros (cada um é um array de campos). */
    private List<String[]> lerRegistros() {
        String texto;
        try {
            texto = Files.readString(arquivo, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new ArquivoException("Não foi possível ler os dados.", e);
        }
        if (texto.startsWith("﻿")) { // marca que o Excel às vezes coloca no começo
            texto = texto.substring(1);
        }

        List<String[]> registros = new ArrayList<>();
        List<String> campos = new ArrayList<>();
        StringBuilder atual = new StringBuilder();
        boolean entreAspas = false;
        int tamanho = texto.length();

        for (int i = 0; i < tamanho; i++) {
            char c = texto.charAt(i);
            if (entreAspas) {
                if (c == '"') {
                    if (i + 1 < tamanho && texto.charAt(i + 1) == '"') {
                        atual.append('"'); // "" dentro de aspas vira "
                        i++;
                    } else {
                        entreAspas = false;
                    }
                } else {
                    atual.append(c);
                }
            } else if (c == '"') {
                entreAspas = true;
            } else if (c == SEPARADOR) {
                campos.add(atual.toString());
                atual.setLength(0);
            } else if (c == '\n' || c == '\r') {
                if (c == '\r' && i + 1 < tamanho && texto.charAt(i + 1) == '\n') {
                    i++;
                }
                if (campos.isEmpty() && atual.length() == 0) {
                    continue; // linha em branco
                }
                campos.add(atual.toString());
                atual.setLength(0);
                registros.add(campos.toArray(new String[0]));
                campos.clear();
            } else {
                atual.append(c);
            }
        }
        if (!campos.isEmpty() || atual.length() > 0) { // última linha sem quebra no fim
            campos.add(atual.toString());
            registros.add(campos.toArray(new String[0]));
        }
        return registros;
    }
}
