package com.example.myapplication.model;

public class BearerTokenUser {

    private String token;
    private String tipo;
    private Integer id;

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.tipo + " " + this.token;
    }
}
