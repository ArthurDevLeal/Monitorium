/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitorium.monitoria.model;

import monitorium.comum.util.Exigir;

/**
 *
 * @author arthu
 */
public class Disciplina {

    private final String id;
    private String nome;
    private String monitorId;

    public Disciplina(String id, String nome,String monitorId) {
        this.id = Exigir.exigir(id, "id");
        this.nome = Exigir.exigir(nome, "nome");
        this.monitorId = Exigir.exigir(monitorId,"id do monitor");
    }

    public String getId() {
        return id;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = Exigir.exigir(nome, "nome");
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = Exigir.exigir(monitorId, "id do monitor");
    }

}
