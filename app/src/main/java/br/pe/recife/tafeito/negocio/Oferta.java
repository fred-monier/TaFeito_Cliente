package br.pe.recife.tafeito.negocio;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Oferta implements Serializable {

    private long id;
    private Servico servico;
    private Date dataHoraInicio;
    private Date dataHoraFim;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Date getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(Date dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public Date getDataHoraFim() {
        return dataHoraFim;
    }

    public void setDataHoraFim(Date dataHoraFim) {
        this.dataHoraFim = dataHoraFim;
    }

    @Override
    public String toString() {

        SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd:HH:mm");

        return this.getServico().getNome() + " > " +
                df.format(this.getDataHoraInicio()) + " < " + df.format(this.getDataHoraFim());
    }

    public String toPrint() {

        SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd:HH:mm");

        String res = "ID: " + this.getId() + System.getProperty("line.separator");
        res = res + "Serviço: " + System.getProperty("line.separator");
        res = res + this.getServico().toPrint() + System.getProperty("line.separator");
        res = res + "Data Hora Início: " + df.format(this.getDataHoraInicio()) + System.getProperty("line.separator");
        res = res + "Data Hora Fim: " + df.format(this.getDataHoraFim());

        return res;
    }
}
