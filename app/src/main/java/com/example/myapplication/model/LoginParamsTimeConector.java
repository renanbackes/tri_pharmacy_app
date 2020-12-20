package com.example.myapplication.model;

public class LoginParamsTimeConector {

    private String dsSenha;
    private String dsLogin;

    public LoginParamsTimeConector( String usuario, String senha ) {
        this.dsLogin = usuario;
        this.dsSenha = senha;
    }

    public String getDsSenha() {
        return this.dsSenha;
    }

    public String getDsLogin() {
        return this.dsLogin;
    }

    public void setDsSenha( String dsSenha ) {
        this.dsSenha = dsSenha;
    }

    public void setDsLogin(String dsLogin ) {
        this.dsLogin = dsLogin;
    }
}
