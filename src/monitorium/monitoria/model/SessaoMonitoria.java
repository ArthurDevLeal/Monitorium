/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitorium.monitoria.model;

import monitorium.comum.excecao.MonitoriumException;
import monitorium.comum.util.Exigir;

public class SessaoMonitoria {

    private final String id;
    private String disciplinaId;
    private String monitorId;
    private String salaId;
    private String data;
    private int horario;
    private int vagas;

    public SessaoMonitoria(String id, String disciplinaId, String monitorId, String salaId, String data, int horario, int vagas) {
        this.id = Exigir.exigir(id, "id");
        this.disciplinaId = Exigir.exigir(disciplinaId, "disciplina");
        this.monitorId = Exigir.exigir(monitorId, "monitor");
        this.salaId = Exigir.exigir(salaId, "sala");
        this.data = Exigir.exigir(data, "data");
        this.horario = Exigir.exigir(horario, "horário");
        if (vagas <= 0) {
            throw new MonitoriumException("Número de vagas inválido");
        } else {
            this.vagas = vagas;
        }
    }

    public boolean validarSessao(int horarioAtual) {
        return horario < horarioAtual;
    }

    public String getData() {
        return data;
    }

    public String getDisciplinaId() {
        return disciplinaId;
    }

    public int getHorario() {
        return horario;
    }

    public String getId() {
        return id;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public String getSalaId() {
        return salaId;
    }

    public int getVagas() {
        return vagas;
    }

    public void setHorario(int horario) {
        this.horario = horario;
    }

    public void setData(String data) {
        this.data = Exigir.exigir(data, "data");
    }

    public void setDisciplinaId(String disciplinaId) {
        this.disciplinaId = Exigir.exigir(disciplinaId, "disciplina");
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = Exigir.exigir(monitorId, "monitor");
    }

    public void setSalaId(String salaId) {
        this.salaId = Exigir.exigir(salaId, "sala");
    }

    public void setVagas(int vagas) {
         if (vagas <= 0) {
            throw new MonitoriumException("Número de vagas inválido");
        } else {
            this.vagas = vagas;
        }
    }

}
