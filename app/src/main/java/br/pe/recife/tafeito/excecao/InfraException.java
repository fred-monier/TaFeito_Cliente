package br.pe.recife.tafeito.excecao;

/**
 * Created by HP on 21/11/2017.
 */

public class InfraException extends Exception {

    private String mensagem;
    private Exception excecaoOriginal;

    public InfraException(String mensagem, Exception excecaoOriginal) {
        this.mensagem = mensagem;
        this.excecaoOriginal = excecaoOriginal;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Exception getExcecaoOriginal() {
        return excecaoOriginal;
    }

    public void setExcecaoOriginal(Exception excecaoOriginal) {
        this.excecaoOriginal = excecaoOriginal;
    }
}
