package br.pe.recife.tafeito.excecao;

/**
 * Created by HP on 21/11/2017.
 */

public class NegocioException extends Exception {

    private String mensagem;


    public NegocioException(String mensagem) {
        this.mensagem = mensagem;

    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }


}
