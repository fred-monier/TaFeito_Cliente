package br.pe.recife.tafeito.negocio;

import java.io.Serializable;

public class Acesso implements Serializable {

    private long id;
    private String login;
    private String senha;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString() {
        return this.getLogin();
    }

    public String toPrint() {

        String res = "ID: " + this.getId() + System.getProperty("line.separator");
        res = res + "Login: " + this.getLogin() + System.getProperty("line.separator");
        res = res + "Senha: " + this.getSenha();

        return res;
    }
}
