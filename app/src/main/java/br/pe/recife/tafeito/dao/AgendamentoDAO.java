package br.pe.recife.tafeito.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import br.pe.recife.tafeito.negocio.Agendamento;
import br.pe.recife.tafeito.negocio.Cliente;
import br.pe.recife.tafeito.negocio.Fornecedor;
import br.pe.recife.tafeito.negocio.Oferta;
import br.pe.recife.tafeito.negocio.Servico;
import br.pe.recife.tafeito.negocio.ServicoCategoria;
import br.pe.recife.tafeito.util.SQLHelperTaFeito;
import br.pe.recife.tafeito.util.Util;

public class AgendamentoDAO implements IDAO<Agendamento> {

    private static AgendamentoDAO instancia;
    private SQLHelperTaFeito bd;

    public static AgendamentoDAO getInstancia(Context context) {

        if (instancia == null) {
            instancia = new AgendamentoDAO(context);
        }

        return instancia;
    }

    private AgendamentoDAO(Context context) {
        this.bd = SQLHelperTaFeito.getInstancia(context);
    }

    private long inserir(Agendamento agendamento) {

        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_ID_OFERTA, agendamento.getOferta().getId());
        cv.put(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_ID_CLIENTE, agendamento.getCliente().getId());

        long id = db.insert(SQLHelperTaFeito.TABELA_AGENDAMENTO, null, cv);

        if (id != -1) {
            agendamento.setId(id);
        }

        db.close();

        return id;
    }

    private int atualizar(Agendamento agendamento) {

        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues cv = new ContentValues();

        Calendar calRealizado = null;
        Calendar calCancelado = null;
        if (agendamento.getDataHoraRealizado() != null) {
            calRealizado = Calendar.getInstance();
            calRealizado.setTime(agendamento.getDataHoraRealizado());
        }
        if (agendamento.getDataHoraCancelado() != null) {
            calCancelado = Calendar.getInstance();
            calCancelado.setTime(agendamento.getDataHoraCancelado());
        }


        cv.put(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_ID_OFERTA, agendamento.getOferta().getId());
        cv.put(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_ID_CLIENTE, agendamento.getCliente().getId());

        if (calRealizado != null) {
            cv.put(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_ANO_REALIZADO, calRealizado.get(Calendar.YEAR));
            cv.put(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_MES_REALIZADO, calRealizado.get(Calendar.MONTH));
            cv.put(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_DIA_REALIZADO, calRealizado.get(Calendar.DAY_OF_MONTH));
            cv.put(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_HORA_REALIZADO, calRealizado.get(Calendar.HOUR_OF_DAY));
            cv.put(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_MINUTO_REALIZADO, calRealizado.get(Calendar.MINUTE));
        } else {
            cv.putNull(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_ANO_REALIZADO);
            cv.putNull(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_MES_REALIZADO);
            cv.putNull(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_DIA_REALIZADO);
            cv.putNull(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_HORA_REALIZADO);
            cv.putNull(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_MINUTO_REALIZADO);
        }
        if (calCancelado != null) {
            cv.put(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_ANO_CANCELADO, calCancelado.get(Calendar.YEAR));
            cv.put(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_MES_CANCELADO, calCancelado.get(Calendar.MONTH));
            cv.put(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_DIA_CANCELADO, calCancelado.get(Calendar.DAY_OF_MONTH));
            cv.put(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_HORA_CANCELADO, calCancelado.get(Calendar.HOUR_OF_DAY));
            cv.put(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_MINUTO_CANCELADO, calCancelado.get(Calendar.MINUTE));
        } else {
            cv.putNull(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_ANO_CANCELADO);
            cv.putNull(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_MES_CANCELADO);
            cv.putNull(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_DIA_CANCELADO);
            cv.putNull(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_HORA_CANCELADO);
            cv.putNull(SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_MINUTO_CANCELADO);
        }

        int linhasAlteradas = db.update(SQLHelperTaFeito.TABELA_AGENDAMENTO, cv,
                SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_ID + " = ?",
                new String[]{String.valueOf(agendamento.getId())});
        db.close();

        return linhasAlteradas;
    }

    @Override
    public void salvar(Agendamento agendamento) {
        if (agendamento.getId() == 0) {
            this.inserir(agendamento);
        } else {
            this.atualizar(agendamento);
        }
    }

    @Override
    public Agendamento consultar(long id) {

        Agendamento res = null;

        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelperTaFeito.TABELA_AGENDAMENTO;

        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_OFERTA;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_AGENDAMENTO + "." + SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_ID_OFERTA;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_OFERTA + "." + SQLHelperTaFeito.TABELA_OFERTA_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_CLIENTE;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_AGENDAMENTO + "." + SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_ID_CLIENTE;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_CLIENTE + "." + SQLHelperTaFeito.TABELA_CLIENTE_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_USUARIO;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_CLIENTE + "." + SQLHelperTaFeito.TABELA_CLIENTE_COLUNA_ID;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_USUARIO + "." + SQLHelperTaFeito.TABELA_USUARIO_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_SERVICO;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_OFERTA + "." + SQLHelperTaFeito.TABELA_OFERTA_COLUNA_ID_SERVICO;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA + "." + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_FORNECEDOR;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_FORNECEDOR;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_USUARIO + " " + SQLHelperTaFeito.TABELA_USUARIO + "2";
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_USUARIO + "2." + SQLHelperTaFeito.TABELA_USUARIO_COLUNA_ID;

        sql = sql + " WHERE " + SQLHelperTaFeito.TABELA_AGENDAMENTO + "." + SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_ID + " = ?";
        String args[] = new String[]{"" + id + ""};

        Cursor cursor = db.rawQuery(sql, args);
        if (cursor.moveToNext()) {

            //From AGENDAMENTO
            long idCol = cursor.getLong(0);
            long idOferta = cursor.getLong(1);
            long idCliente = cursor.getLong(2);
            int anoRealizado = cursor.getInt(3);
            int mesRealizado = cursor.getInt(4);
            int diaRealizado = cursor.getInt(5);
            int horaRealizado = cursor.getInt(6);
            int minutoRealizado = cursor.getInt(7);
            int anoCancelado = cursor.getInt(8);
            int mesCancelado = cursor.getInt(9);
            int diaCancelado = cursor.getInt(10);
            int horaCancelado = cursor.getInt(11);
            int minutoCancelado = cursor.getInt(12);

            //From OFERTA
            long idServ = cursor.getLong(14);
            int anoInicio = cursor.getInt(15);
            int mesInicio = cursor.getInt(16);
            int diaInicio = cursor.getInt(17);
            int horaInicio = cursor.getInt(18);
            int minutoInicio = cursor.getInt(19);
            int anoFim = cursor.getInt(20);
            int mesFim= cursor.getInt(21);
            int diaFim = cursor.getInt(22);
            int horaFim = cursor.getInt(23);
            int minutoFim = cursor.getInt(24);

            //From Cliente
            String cpfCliente = cursor.getString(26);

            //From USUARIO (Cliente)
            int habUsuCliente = cursor.getInt(28);
            String nomeUsuCliente = cursor.getString(29);
            String endUsuCliente = cursor.getString(30);
            String emailColCliente = cursor.getString(31);
            int telColCliente = cursor.getInt(32);

            //From SERVICO
            long idServCat = cursor.getLong(34);
            long idForn = cursor.getLong(35);
            String nomeSer = cursor.getString(36);
            String descSer= cursor.getString(37);

            //From SERVICO_CATEGORIA
            String nomeSerCat = cursor.getString(39);
            String descSerCat = cursor.getString(40);

            //From FORNECEDOR
            String cnpjForn = cursor.getString(42);

            //From USUARIO (Fornecedor)
            int habUsuForn = cursor.getInt(44);
            String nomeUsuForn = cursor.getString(45);
            String endUsuForn = cursor.getString(46);
            String emailColForn = cursor.getString(47);
            int telColForn = cursor.getInt(48);

            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setId(idForn);
            fornecedor.setHabilitado(Util.valorBooleano(habUsuForn));
            fornecedor.setNome(nomeUsuForn);
            fornecedor.setEndereco(endUsuForn);
            fornecedor.setEmail(emailColForn);
            fornecedor.setTelefone(telColForn);
            fornecedor.setCnpj(cnpjForn);

            ServicoCategoria servicoCategoria = new ServicoCategoria();
            servicoCategoria.setId(idServCat);
            servicoCategoria.setNome(nomeSerCat);
            servicoCategoria.setDescricao(descSerCat);

            Servico servico = new Servico();
            servico.setId(idServ);
            servico.setServicoCategoria(servicoCategoria);
            servico.setFornecedor(fornecedor);
            servico.setNome(nomeSer);
            servico.setDescricao(descSer);

            GregorianCalendar gcInicio = new GregorianCalendar(anoInicio, mesInicio, diaInicio, horaInicio, minutoInicio);
            GregorianCalendar gcFim = new GregorianCalendar(anoFim, mesFim, diaFim, horaFim, minutoFim);

            Oferta oferta = new Oferta();
            oferta.setId(idOferta);
            oferta.setServico(servico);
            oferta.setDataHoraInicio(gcInicio.getTime());
            oferta.setDataHoraFim(gcFim.getTime());

            Cliente cliente = new Cliente();
            cliente.setId(idCliente);
            cliente.setHabilitado(Util.valorBooleano(habUsuCliente));
            cliente.setNome(nomeUsuCliente);
            cliente.setEndereco(endUsuCliente);
            cliente.setEmail(emailColCliente);
            cliente.setTelefone(telColCliente);
            cliente.setCpf(cpfCliente);

            GregorianCalendar gcRealizado = new GregorianCalendar(anoRealizado, mesRealizado, diaRealizado, horaRealizado, minutoRealizado);
            GregorianCalendar gcCancelado = new GregorianCalendar(anoCancelado, mesCancelado, diaCancelado, horaCancelado, minutoCancelado);

            Agendamento agendamento = new Agendamento();
            agendamento.setId(idCol);
            agendamento.setOferta(oferta);
            agendamento.setCliente(cliente);
            agendamento.setDataHoraRealizado(gcRealizado.getTime());
            agendamento.setDataHoraCancelado(gcCancelado.getTime());

            res = agendamento;
        }

        cursor.close();

        return res;

    }

    @Override
    public int excluir(Agendamento agendamento) {

        SQLiteDatabase db = bd.getWritableDatabase();

        int linhasExcluidas = db.delete(SQLHelperTaFeito.TABELA_AGENDAMENTO,
                SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_ID + " = ?",
                new String[]{String.valueOf(agendamento.getId())});
        db.close();

        return linhasExcluidas;
    }

    @Override
    public List<Agendamento> listar() {

        List<Agendamento> res = new ArrayList<Agendamento>();

        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelperTaFeito.TABELA_AGENDAMENTO;

        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_OFERTA;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_AGENDAMENTO + "." + SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_ID_OFERTA;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_OFERTA + "." + SQLHelperTaFeito.TABELA_OFERTA_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_CLIENTE;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_AGENDAMENTO + "." + SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_ID_CLIENTE;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_CLIENTE + "." + SQLHelperTaFeito.TABELA_CLIENTE_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_USUARIO;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_CLIENTE + "." + SQLHelperTaFeito.TABELA_CLIENTE_COLUNA_ID;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_USUARIO + "." + SQLHelperTaFeito.TABELA_USUARIO_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_SERVICO;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_OFERTA + "." + SQLHelperTaFeito.TABELA_OFERTA_COLUNA_ID_SERVICO;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA + "." + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_FORNECEDOR;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_FORNECEDOR;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_USUARIO + " " + SQLHelperTaFeito.TABELA_USUARIO + "2";
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_USUARIO + "2." + SQLHelperTaFeito.TABELA_USUARIO_COLUNA_ID;

        //sql = sql + " WHERE " + SQLHelperTaFeito.TABELA_AGENDAMENTO_COLUNA_XXX + " = ?";
        //String args[] = new String[]{"" + "XXX" + ""};

        sql = sql + " ORDER BY " + "16, 17, 18, 19, 20";

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {

            //From AGENDAMENTO
            long idCol = cursor.getLong(0);
            long idOferta = cursor.getLong(1);
            long idCliente = cursor.getLong(2);
            int anoRealizado = cursor.getInt(3);
            int mesRealizado = cursor.getInt(4);
            int diaRealizado = cursor.getInt(5);
            int horaRealizado = cursor.getInt(6);
            int minutoRealizado = cursor.getInt(7);
            int anoCancelado = cursor.getInt(8);
            int mesCancelado = cursor.getInt(9);
            int diaCancelado = cursor.getInt(10);
            int horaCancelado = cursor.getInt(11);
            int minutoCancelado = cursor.getInt(12);

            //From OFERTA
            long idServ = cursor.getLong(14);
            int anoInicio = cursor.getInt(15);
            int mesInicio = cursor.getInt(16);
            int diaInicio = cursor.getInt(17);
            int horaInicio = cursor.getInt(18);
            int minutoInicio = cursor.getInt(19);
            int anoFim = cursor.getInt(20);
            int mesFim= cursor.getInt(21);
            int diaFim = cursor.getInt(22);
            int horaFim = cursor.getInt(23);
            int minutoFim = cursor.getInt(24);

            //From Cliente
            String cpfCliente = cursor.getString(26);

            //From USUARIO (Cliente)
            int habUsuCliente = cursor.getInt(28);
            String nomeUsuCliente = cursor.getString(29);
            String endUsuCliente = cursor.getString(30);
            String emailColCliente = cursor.getString(31);
            int telColCliente = cursor.getInt(32);

            //From SERVICO
            long idServCat = cursor.getLong(34);
            long idForn = cursor.getLong(35);
            String nomeSer = cursor.getString(36);
            String descSer= cursor.getString(37);

            //From SERVICO_CATEGORIA
            String nomeSerCat = cursor.getString(39);
            String descSerCat = cursor.getString(40);

            //From FORNECEDOR
            String cnpjForn = cursor.getString(42);

            //From USUARIO (Fornecedor)
            int habUsuForn = cursor.getInt(44);
            String nomeUsuForn = cursor.getString(45);
            String endUsuForn = cursor.getString(46);
            String emailColForn = cursor.getString(47);
            int telColForn = cursor.getInt(48);

            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setId(idForn);
            fornecedor.setHabilitado(Util.valorBooleano(habUsuForn));
            fornecedor.setNome(nomeUsuForn);
            fornecedor.setEndereco(endUsuForn);
            fornecedor.setTelefone(telColForn);
            fornecedor.setCnpj(cnpjForn);
            fornecedor.setCnpj(cnpjForn);

            ServicoCategoria servicoCategoria = new ServicoCategoria();
            servicoCategoria.setId(idServCat);
            servicoCategoria.setNome(nomeSerCat);
            servicoCategoria.setDescricao(descSerCat);

            Servico servico = new Servico();
            servico.setId(idServ);
            servico.setServicoCategoria(servicoCategoria);
            servico.setFornecedor(fornecedor);
            servico.setNome(nomeSer);
            servico.setDescricao(descSer);

            GregorianCalendar gcInicio = new GregorianCalendar(anoInicio, mesInicio, diaInicio, horaInicio, minutoInicio);
            GregorianCalendar gcFim = new GregorianCalendar(anoFim, mesFim, diaFim, horaFim, minutoFim);

            Oferta oferta = new Oferta();
            oferta.setId(idOferta);
            oferta.setServico(servico);
            oferta.setDataHoraInicio(gcInicio.getTime());
            oferta.setDataHoraFim(gcFim.getTime());

            Cliente cliente = new Cliente();
            cliente.setId(idCliente);
            cliente.setHabilitado(Util.valorBooleano(habUsuCliente));
            cliente.setNome(nomeUsuCliente);
            cliente.setEndereco(endUsuCliente);
            cliente.setEmail(emailColCliente);
            cliente.setTelefone(telColCliente);
            cliente.setCpf(cpfCliente);

            GregorianCalendar gcRealizado = new GregorianCalendar(anoRealizado, mesRealizado, diaRealizado, horaRealizado, minutoRealizado);
            GregorianCalendar gcCancelado = new GregorianCalendar(anoCancelado, mesCancelado, diaCancelado, horaCancelado, minutoCancelado);

            Agendamento agendamento = new Agendamento();
            agendamento.setId(idCol);
            agendamento.setOferta(oferta);
            agendamento.setCliente(cliente);
            agendamento.setDataHoraRealizado(gcRealizado.getTime());
            agendamento.setDataHoraCancelado(gcCancelado.getTime());

            res.add(agendamento);
        }

        cursor.close();

        return res;
    }

}
