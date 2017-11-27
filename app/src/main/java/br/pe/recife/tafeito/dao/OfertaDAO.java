package br.pe.recife.tafeito.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import br.pe.recife.tafeito.negocio.Fornecedor;
import br.pe.recife.tafeito.negocio.Oferta;
import br.pe.recife.tafeito.negocio.Servico;
import br.pe.recife.tafeito.negocio.ServicoCategoria;
import br.pe.recife.tafeito.util.SQLHelperTaFeito;
import br.pe.recife.tafeito.util.Util;

public class OfertaDAO implements IDAO<Oferta> {

    private static OfertaDAO instancia;
    private SQLHelperTaFeito bd;

    public static OfertaDAO getInstancia(Context context) {

        if (instancia == null) {
            instancia = new OfertaDAO(context);
        }

        return instancia;
    }

    private OfertaDAO(Context context) {
        this.bd = SQLHelperTaFeito.getInstancia(context);
    }

    private long inserir(Oferta oferta) {

        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues cv = new ContentValues();

        Calendar calInicio = Calendar.getInstance();
        Calendar calFim = Calendar.getInstance();
        calInicio.setTime(oferta.getDataHoraInicio());
        calFim.setTime(oferta.getDataHoraFim());

        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_ID_SERVICO, oferta.getServico().getId());
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_ANO_INICIO, calInicio.get(Calendar.YEAR));
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_MES_INICIO, calInicio.get(Calendar.MONTH));
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_DIA_INICIO, calInicio.get(Calendar.DAY_OF_MONTH));
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_HORA_INICIO, calInicio.get(Calendar.HOUR_OF_DAY));
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_MINUTO_INICIO, calInicio.get(Calendar.MINUTE));
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_ANO_FIM, calFim.get(Calendar.YEAR));
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_MES_FIM, calFim.get(Calendar.MONTH));
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_DIA_FIM, calFim.get(Calendar.DAY_OF_MONTH));
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_HORA_FIM, calFim.get(Calendar.HOUR_OF_DAY));
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_MINUTO_FIM, calFim.get(Calendar.MINUTE));

        long id = db.insert(SQLHelperTaFeito.TABELA_OFERTA, null, cv);

        if (id != -1) {
            oferta.setId(id);
        }

        db.close();

        return id;
    }

    private int atualizar(Oferta oferta) {

        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues cv = new ContentValues();

        Calendar calInicio = Calendar.getInstance();
        Calendar calFim = Calendar.getInstance();
        calInicio.setTime(oferta.getDataHoraInicio());
        calFim.setTime(oferta.getDataHoraFim());

        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_ID_SERVICO, oferta.getServico().getId());
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_ANO_INICIO, calInicio.get(Calendar.YEAR));
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_MES_INICIO, calInicio.get(Calendar.MONTH));
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_DIA_INICIO, calInicio.get(Calendar.DAY_OF_MONTH));
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_HORA_INICIO, calInicio.get(Calendar.HOUR_OF_DAY));
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_MINUTO_INICIO, calInicio.get(Calendar.MINUTE));
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_ANO_FIM, calFim.get(Calendar.YEAR));
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_MES_FIM, calFim.get(Calendar.MONTH));
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_DIA_FIM, calFim.get(Calendar.DAY_OF_MONTH));
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_HORA_FIM, calFim.get(Calendar.HOUR_OF_DAY));
        cv.put(SQLHelperTaFeito.TABELA_OFERTA_COLUNA_MINUTO_FIM, calFim.get(Calendar.MINUTE));

        int linhasAlteradas = db.update(SQLHelperTaFeito.TABELA_OFERTA, cv,
                SQLHelperTaFeito.TABELA_OFERTA_COLUNA_ID + " = ?",
                new String[]{String.valueOf(oferta.getId())});
        db.close();

        return linhasAlteradas;
    }

    @Override
    public void salvar(Oferta oferta) {
        if (oferta.getId() == 0) {
            this.inserir(oferta);
        } else {
            this.atualizar(oferta);
        }
    }

    @Override
    public Oferta consultar(long id) {

        Oferta res = null;

        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelperTaFeito.TABELA_OFERTA;

        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_SERVICO;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_OFERTA + "." + SQLHelperTaFeito.TABELA_OFERTA_COLUNA_ID_SERVICO;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA + "." + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_FORNECEDOR;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_FORNECEDOR;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_USUARIO;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_USUARIO + "." + SQLHelperTaFeito.TABELA_USUARIO_COLUNA_ID;

        sql = sql + " WHERE " + SQLHelperTaFeito.TABELA_OFERTA + "." + SQLHelperTaFeito.TABELA_OFERTA_COLUNA_ID + " = ?";
        String args[] = new String[]{"" + id + ""};

        Cursor cursor = db.rawQuery(sql, args);
        if (cursor.moveToNext()) {

            //From OFERTA
            long idCol = cursor.getLong(0);
            long idServ = cursor.getLong(1);
            int anoInicio = cursor.getInt(2);
            int mesInicio = cursor.getInt(3);
            int diaInicio = cursor.getInt(4);
            int horaInicio = cursor.getInt(5);
            int minutoInicio = cursor.getInt(6);
            int anoFim = cursor.getInt(7);
            int mesFim= cursor.getInt(8);
            int diaFim = cursor.getInt(9);
            int horaFim = cursor.getInt(10);
            int minutoFim = cursor.getInt(11);

            //From SERVICO
            long idServCat = cursor.getLong(13);
            long idForn = cursor.getLong(14);
            String nomeSer = cursor.getString(15);
            String descSer= cursor.getString(16);

            //From SERVICO_CATEGORIA
            String nomeSerCat = cursor.getString(18);
            String descSerCat = cursor.getString(19);

            //From FORNECEDOR
            String cnpjForn = cursor.getString(21);

            //From USUARIO
            int habUsu = cursor.getInt(23);
            String nomeUsu = cursor.getString(24);
            String endUsu = cursor.getString(25);
            String emailCol = cursor.getString(26);
            int telCol = cursor.getInt(27);

            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setId(idForn);
            fornecedor.setHabilitado(Util.valorBooleano(habUsu));
            fornecedor.setNome(nomeUsu);
            fornecedor.setEndereco(endUsu);
            fornecedor.setEmail(emailCol);
            fornecedor.setTelefone(telCol);
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
            oferta.setId(idCol);
            oferta.setServico(servico);
            oferta.setDataHoraInicio(gcInicio.getTime());
            oferta.setDataHoraFim(gcFim.getTime());

            res = oferta;
        }

        cursor.close();

        return res;

    }

    @Override
    public int excluir(Oferta oferta) {

        SQLiteDatabase db = bd.getWritableDatabase();

        int linhasExcluidas = db.delete(SQLHelperTaFeito.TABELA_OFERTA,
                SQLHelperTaFeito.TABELA_OFERTA_COLUNA_ID + " = ?",
                new String[]{String.valueOf(oferta.getId())});
        db.close();

        return linhasExcluidas;
    }

    @Override
    public List<Oferta> listar() {

        List<Oferta> res = new ArrayList<Oferta>();

        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelperTaFeito.TABELA_OFERTA;

        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_SERVICO;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_OFERTA + "." + SQLHelperTaFeito.TABELA_OFERTA_COLUNA_ID_SERVICO;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA + "." + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_FORNECEDOR;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_FORNECEDOR;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_USUARIO;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_USUARIO + "." + SQLHelperTaFeito.TABELA_USUARIO_COLUNA_ID;

        //sql = sql + " WHERE " + SQLHelperTaFeito.TABELA_OFERTA_COLUNA_XXX + " = ?";
        //String args[] = new String[]{"" + "XXX" + ""};

        sql = sql + " ORDER BY " + "3, 4, 5, 6, 7";

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {

            //From OFERTA
            long idCol = cursor.getLong(0);
            long idServ = cursor.getLong(1);
            int anoInicio = cursor.getInt(2);
            int mesInicio = cursor.getInt(3);
            int diaInicio = cursor.getInt(4);
            int horaInicio = cursor.getInt(5);
            int minutoInicio = cursor.getInt(6);
            int anoFim = cursor.getInt(7);
            int mesFim= cursor.getInt(8);
            int diaFim = cursor.getInt(9);
            int horaFim = cursor.getInt(10);
            int minutoFim = cursor.getInt(11);

            //From SERVICO
            long idServCat = cursor.getLong(13);
            long idForn = cursor.getLong(14);
            String nomeSer = cursor.getString(15);
            String descSer= cursor.getString(16);

            //From SERVICO_CATEGORIA
            String nomeSerCat = cursor.getString(18);
            String descSerCat = cursor.getString(19);

            //From FORNECEDOR
            String cnpjForn = cursor.getString(21);

            //From USUARIO
            int habUsu = cursor.getInt(23);
            String nomeUsu = cursor.getString(24);
            String endUsu = cursor.getString(25);
            String emailCol = cursor.getString(26);
            int telCol = cursor.getInt(27);

            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setId(idForn);
            fornecedor.setHabilitado(Util.valorBooleano(habUsu));
            fornecedor.setNome(nomeUsu);
            fornecedor.setEndereco(endUsu);
            fornecedor.setEmail(emailCol);
            fornecedor.setTelefone(telCol);
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
            oferta.setId(idCol);
            oferta.setServico(servico);
            oferta.setDataHoraInicio(gcInicio.getTime());
            oferta.setDataHoraFim(gcFim.getTime());

            res.add(oferta);
        }

        cursor.close();

        return res;
    }

}
