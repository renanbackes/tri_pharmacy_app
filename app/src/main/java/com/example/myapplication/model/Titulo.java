package com.example.myapplication.model;

public class Titulo {

    private Integer idTitulo;
    private Integer idTime;
    private String dsTitulo;
    private String dsQuantidade;
    private String dsAno;

    public Integer getIdTitulo() {
        return idTitulo;
    }

    public void setIdTitulo(Integer idTitulo) {
        this.idTitulo = idTitulo;
    }

    public Integer getIdTime() {
        return idTime;
    }

    public void setIdTime(Integer idTime) {
        this.idTime = idTime;
    }

    public String getDsTitulo() {
        return dsTitulo;
    }

    public void setDsTitulo(String dsTitulo) {
        this.dsTitulo = dsTitulo;
    }

    public String getDsQuantidade() {
        return dsQuantidade;
    }

    public void setDsQuantidade(String dsQuantidade) {
        this.dsQuantidade = dsQuantidade;
    }

    public String getDsAno() {
        return dsAno;
    }

    public void setDsAno(String dsAno) {
        this.dsAno = dsAno;
    }
}
