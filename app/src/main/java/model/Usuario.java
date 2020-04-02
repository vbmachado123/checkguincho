package model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

import util.Base64Custom;
import util.ConfiguracaoFirebase;

public class Usuario implements Serializable {

    private int id;

    /* LOGIN */
    private String email = null;
    private String senha = null;

    /* EMPRESA */
    private String nomeEmpresa = null;
    private String cnpjEmpresa = null;

    /* MOTORISTA */
    private String nomeMorotista = null;
    private String rgMotorista = null;
    private String telefoneMotorista = null;

    /* IMAGENS */
    private String caminhoImagemLogo = null;
    private String caminhoAssinatura = null;

    public void Usuario(){}

    public String salvar(){
        String identificador = Base64Custom.codificarBase64(email);
        DatabaseReference refenciaFirebase = ConfiguracaoFirebase.getFirebaseDatabase();
        refenciaFirebase.child("usuarios").child(identificador).setValue(this);

        return identificador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public void setCnpjEmpresa(String cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public String getNomeMorotista() {
        return nomeMorotista;
    }

    public void setNomeMorotista(String nomeMorotista) {
        this.nomeMorotista = nomeMorotista;
    }

    public String getRgMotorista() {
        return rgMotorista;
    }

    public void setRgMotorista(String rgMotorista) {
        this.rgMotorista = rgMotorista;
    }

    public String getTelefoneMotorista() {
        return telefoneMotorista;
    }

    public void setTelefoneMotorista(String telefoneMotorista) {
        this.telefoneMotorista = telefoneMotorista;
    }

    public String getCaminhoImagemLogo() {
        return caminhoImagemLogo;
    }

    public void setCaminhoImagemLogo(String caminhoImagemLogo) {
        this.caminhoImagemLogo = caminhoImagemLogo;
    }

    public String getCaminhoAssinatura() {
        return caminhoAssinatura;
    }

    public void setCaminhoAssinatura(String caminhoAssinatura) {
        this.caminhoAssinatura = caminhoAssinatura;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
