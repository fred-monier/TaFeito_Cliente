package br.pe.recife.tafeito.negocio;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Agendamento implements Serializable {

    private long id;
    private Oferta oferta;
    private Cliente cliente;
    private Date dataHoraRealizado;
    private Date dataHoraCancelado;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Oferta getOferta() {
        return oferta;
    }

    public void setOferta(Oferta oferta) {
        this.oferta = oferta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Date getDataHoraRealizado() {
        return dataHoraRealizado;
    }

    public void setDataHoraRealizado(Date dataHoraRealizado) {
        this.dataHoraRealizado = dataHoraRealizado;
    }

    public Date getDataHoraCancelado() {
        return dataHoraCancelado;
    }

    public void setDataHoraCancelado(Date dataHoraCancelado) {
        this.dataHoraCancelado = dataHoraCancelado;
    }

    @Override
    public String toString() {

        String res = "";

        SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd:HH:mm");

        res = res + this.getCliente().getNome();
        res = res + " - ";
        res = res + this.getOferta().getServico().getNome() + " > " +
                df.format(this.getOferta().getDataHoraInicio()) + " < " +
                df.format(this.getOferta().getDataHoraFim());

        return res;
    }

    public String toPrint() {

        SimpleDateFormat df = new SimpleDateFormat("yy-MM-dd:HH:mm");

        String res = "ID: " + this.getId() + System.getProperty("line.separator");
        res = res + "Oferta: " + System.getProperty("line.separator");
        res = res + this.getOferta().toPrint() + System.getProperty("line.separator");
        res = res + "Cliente: " + System.getProperty("line.separator");
        res = res + this.getCliente().toPrint() + System.getProperty("line.separator");
        res = res + "Data Hora Realizado: ";
        if (this.getDataHoraRealizado() != null) {
            res = res + df.format(this.getDataHoraRealizado()) + System.getProperty("line.separator");
        } else {
            res = res + "null";
        }
        res = res + "Data Hora Cancelado: ";
        if (this.getDataHoraCancelado() != null) {
            res = res + df.format(this.getDataHoraCancelado());
        } else {
            res = res + "null";
        }

        return res;
    }
}
