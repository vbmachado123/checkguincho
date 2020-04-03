package model;

import java.io.Serializable;

public class Pdf  implements Serializable {
    private int id;
    private int idInspecao;
    private String caminhoDocumento;

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

    public String getCaminhoDocumento() {
        return caminhoDocumento;
    }

    public void setCaminhoDocumento(String caminhoDocumento) {
        this.caminhoDocumento = caminhoDocumento;
    }
}
