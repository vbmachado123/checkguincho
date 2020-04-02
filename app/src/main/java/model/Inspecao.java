package model;

public class Inspecao {
    private int id;
    private int idMarca;
    private int idModelo;
    private int idCor;
    private int idUsuario;
    private String nomeProprietario;
    private int ano;
    private String placa;
    private int inspecao;
    private String caminhoAssinaturaRecusa = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public int getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(int idModelo) {
        this.idModelo = idModelo;
    }

    public int getIdCor() {
        return idCor;
    }

    public void setIdCor(int idCor) {
        this.idCor = idCor;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeProprietario() {
        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getInspecao() {
        return inspecao;
    }

    public void setInspecao(int inspecao) {
        this.inspecao = inspecao;
    }

    public String getCaminhoAssinaturaRecusa() {
        return caminhoAssinaturaRecusa;
    }

    public void setCaminhoAssinaturaRecusa(String caminhoAssinaturaRecusa) {
        this.caminhoAssinaturaRecusa = caminhoAssinaturaRecusa;
    }
}
