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
public class Inscricao {
    private final String id;
    private String alunoId;
    private String sessaoId;

    public Inscricao(String id, String alunoId, String sessaoId) {
        this.id = Exigir.exigir(id, "id");
        this.alunoId = alunoId;
        this.sessaoId = sessaoId;
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

    public void setAlunoId(String alunoId) {
        this.alunoId = alunoId;
    }

    public void setSessaoId(String sessaoId) {
        this.sessaoId = sessaoId;
    }
    
}
