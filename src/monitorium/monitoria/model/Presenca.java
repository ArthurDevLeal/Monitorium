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
public class Presenca {

    private final String id;
    private String alunoId;
    private String sessaoId;
    private boolean presente;

    public Presenca(String id, String alunoId, String sessaoId) {
        this.id = Exigir.exigir(id, "id");
        this.alunoId = Exigir.exigir(alunoId, "aluno");
        this.sessaoId = Exigir.exigir(sessaoId, "sessão");
        this.presente = false;
    }

    public String getAlunoId() {
        return alunoId;
    }

    public String getId() {
        return id;
    }

    public String getSessaoId() {
        return sessaoId;
    }

    public void setSessaoId(String sessaoId) {
        this.sessaoId = Exigir.exigir(sessaoId, "sessão");
    }

    public void setAlunoId(String alunoId) {
        this.alunoId = Exigir.exigir(alunoId, "aluno");
    }

    public boolean isPresente() {
        return presente;
    }

    public void marcarPresença() {
        this.presente = true;
    }

}
