package br.pe.recife.tafeito.negocio;

import java.io.Serializable;

public class ServicoCategoria implements Serializable {

    private long id;
    private String nome;
    private String descricao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.getNome();
    }

    public String toPrint() {

        String res = "ID: " + this.getId() + System.getProperty("line.separator");
        res = res + "Nome: " + this.getNome() + System.getProperty("line.separator");
        res = res + "Descrição: " + this.getDescricao();

        return res;
    }
}
