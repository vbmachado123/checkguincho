package model;

public class FigurasInspecao {
    private int id;
    private int idInspecao;
    private String caminhoFigura;
    private String caminhoAssinaturaRetira;
    private String caminhoAssinaturaEntrega;

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

    public String getCaminhoFigura() {
        return caminhoFigura;
    }

    public void setCaminhoFigura(String caminhoFigura) {
        this.caminhoFigura = caminhoFigura;
    }

    public String getCaminhoAssinaturaRetira() {
        return caminhoAssinaturaRetira;
    }

    public void setCaminhoAssinaturaRetira(String caminhoAssinaturaRetira) {
        this.caminhoAssinaturaRetira = caminhoAssinaturaRetira;
    }

    public String getCaminhoAssinaturaEntrega() {
        return caminhoAssinaturaEntrega;
    }

    public void setCaminhoAssinaturaEntrega(String caminhoAssinaturaEntrega) {
        this.caminhoAssinaturaEntrega = caminhoAssinaturaEntrega;
    }
}
