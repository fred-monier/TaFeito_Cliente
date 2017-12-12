package br.pe.recife.tafeito.negocio;

public class Cliente extends Usuario {

    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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
        res = res + "Telefone: " + this.getTelefone() + System.getProperty("line.separator");
        res = res + "CPF: " + this.getCpf();

        return res;
    }

}
