package br.pe.recife.tafeito.negocio;

import java.io.Serializable;

public class Autenticacao implements Serializable {

    private long idAcesso;
    private String token;

    public long getIdAcesso() {
        return idAcesso;
    }

    public void setIdAcesso(long idAcesso) {
        this.idAcesso = idAcesso;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
