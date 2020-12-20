package com.example.myapplication.model;

import java.io.Serializable;

public class User implements Serializable {

    private Integer idUsuario;
    private String dsNome;
    private String dsLogin;
    private String dsEmail;
    private String dsSenha;
    private String tpPessoa;
    private String noCpfCnpj;
    private String noTelefoneCel;
    private String noTelefoneRes;
    private String noTelefoneAux;

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario( Integer idUsuario ) {
        this.idUsuario = idUsuario;
    }

    public String getDsNome() {
        return dsNome;
    }

    public void setDsNome(String dsNome) {
        this.dsNome = dsNome;
    }

    public String getDsLogin() {
        return dsLogin;
    }

    public void setDsLogin(String dsLogin) {
        this.dsLogin = dsLogin;
    }

    public String getDsEmail() {
        return dsEmail;
    }

    public void setDsEmail( String dsEmail ) {
        this.dsEmail = dsEmail;
    }

    public String getDsSenha() { return tpPessoa; }

    public void setDsSenha( String dsSenha ) { this.dsSenha = dsSenha; }

    public String getTpPessoa() {
        return tpPessoa;
    }

    public void setTpPessoa( String tpPessoa ) {
        this.tpPessoa = tpPessoa;
    }

    public String getNoCpfCnpj() { return noCpfCnpj; }

    public void setNoCpfCnpj( String noCpfCnpj ) { this.noCpfCnpj = noCpfCnpj; }

    public String getNoTelefoneCel() { return noTelefoneCel; }

    public void setNoTelefoneCel( String noTelefoneCel ) { this.noTelefoneCel = noTelefoneCel; }

    public String getNoTelefoneRes() { return noTelefoneRes; }

    public void setNoTelefoneRes( String noTelefoneRes ) { this.noTelefoneRes = noTelefoneRes; }

    public String getNoTelefoneAux() { return noTelefoneAux; }

    public void setNoTelefoneAux( String noTelefoneAux ) { this.noTelefoneAux = noTelefoneAux; }
}