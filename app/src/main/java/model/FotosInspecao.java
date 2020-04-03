package model;

import java.io.Serializable;

public class FotosInspecao  implements Serializable {
    private int id;
    private int idInspecao;
    private String caminhoFotoPainel;
    private String caminhoFotoFrente;
    private String caminhoFotoLado;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdInspecao() {
        return idInspecao;
    }

    public void setIdInspecao(int idInspecao) {
        this.idInspecao = idInspecao;
    }

    public String getCaminhoFotoPainel() {
        return caminhoFotoPainel;
    }

    public void setCaminhoFotoPainel(String caminhoFotoPainel) {
        this.caminhoFotoPainel = caminhoFotoPainel;
    }

    public String getCaminhoFotoFrente() {
        return caminhoFotoFrente;
    }

    public void setCaminhoFotoFrente(String caminhoFotoFrente) {
        this.caminhoFotoFrente = caminhoFotoFrente;
    }

    public String getCaminhoFotoLado() {
        return caminhoFotoLado;
    }

    public void setCaminhoFotoLado(String caminhoFotoLado) {
        this.caminhoFotoLado = caminhoFotoLado;
    }
}
