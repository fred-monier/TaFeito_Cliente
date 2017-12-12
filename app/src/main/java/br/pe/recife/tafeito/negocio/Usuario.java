package br.pe.recife.tafeito.negocio;

import java.io.Serializable;

public class Usuario implements Serializable {

    private long id;
    private boolean habilitado;
    private String nome;
    private String endereco;
    private String email;
    private int telefone;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefone() {
        return telefone;
    }

    public void setTelefone(int telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return this.getNome();
    }

    public String toPrint() {

        String res = "ID: " + this.getId() + System.getProperty("line.separator");
        res = res + "Habilitado: " + this.isHabilitado() + System.getProperty("line.separator");
        res = res + "Nome: " + this.getNome() + System.getProperty("line.separator");
        res = res + "Endereço: " + this.getEndereco() + System.getProperty("line.separator");
        res = res + "Email: " + this.getEmail() + System.getProperty("line.separator");
        res = res + "Telefone: " + this.getTelefone();

        return res;
    }
}
