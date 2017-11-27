package br.pe.recife.tafeito.negocio;

import java.io.Serializable;

public class Servico implements Serializable {

    private long id;
    private ServicoCategoria servicoCategoria;
    private Fornecedor fornecedor;
    private String nome;
    private String descricao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ServicoCategoria getServicoCategoria() {
        return servicoCategoria;
    }

    public void setServicoCategoria(ServicoCategoria servicoCategoria) {
        this.servicoCategoria = servicoCategoria;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
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
        res = res + "Serviço Categoria: " + System.getProperty("line.separator");
        res = res + this.getServicoCategoria().toPrint() + System.getProperty("line.separator");
        res = res + "Fornecedor: " +  System.getProperty("line.separator");
        res = res + this.getFornecedor().toPrint() + System.getProperty("line.separator");
        res = res + "Nome: " + this.getNome() + System.getProperty("line.separator");
        res = res + "Descrição: " + this.getDescricao();

        return res;
    }
}
