package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class Time {

    private Integer idTime;
    private String nome;
    private String dsLogo;

    public Integer getIdTime() {
        return idTime;
    }

    public void setIdTime( Integer idTime ) {
        this.idTime = idTime;
    }

    public String getNome() {
        return nome;
    }

    public void setNome( String nome ) {
        this.nome = nome;
    }

    public String getDsLogo() {
        return dsLogo;
    }

    public void setDsLogo( String dsLogo ) {
        this.dsLogo = dsLogo;
    }

    @Override
    public String toString() {
        return nome;
    }
}
