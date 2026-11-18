/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package monitorium.monitoria.model;

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
        this.id = id;
        this.alunoId = alunoId;
        this.sessaoId = sessaoId;
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
        this.sessaoId = sessaoId;
    }

    public void setAlunoId(String alunoId) {
        this.alunoId = alunoId;
    }

    public boolean isPresente() {
        return presente;
    }

    public void marcarPresença() {
        this.presente = true;
    }

}
