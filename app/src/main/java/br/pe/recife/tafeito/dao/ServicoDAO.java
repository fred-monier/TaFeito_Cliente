package br.pe.recife.tafeito.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.pe.recife.tafeito.negocio.Fornecedor;
import br.pe.recife.tafeito.negocio.Servico;
import br.pe.recife.tafeito.negocio.ServicoCategoria;
import br.pe.recife.tafeito.util.SQLHelperTaFeito;
import br.pe.recife.tafeito.util.Util;

public class ServicoDAO implements IDAO<Servico> {

    private static ServicoDAO instancia;
    private SQLHelperTaFeito bd;

    public static ServicoDAO getInstancia(Context context) {

        if (instancia == null) {
            instancia = new ServicoDAO(context);
        }

        return instancia;
    }

    private ServicoDAO(Context context) {
        this.bd = SQLHelperTaFeito.getInstancia(context);
    }

    private long inserir(Servico servico) {

        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA, servico.getServicoCategoria().getId());
        cv.put(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_FORNECEDOR, servico.getFornecedor().getId());
        cv.put(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_NOME, servico.getNome());
        cv.put(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_DESCRICAO, servico.getDescricao());

        long id = db.insert(SQLHelperTaFeito.TABELA_SERVICO, null, cv);

        if (id != -1) {
            servico.setId(id);
        }

        db.close();

        return id;
    }

    private int atualizar(Servico servico) {

        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA, servico.getServicoCategoria().getId());
        cv.put(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_FORNECEDOR, servico.getFornecedor().getId());
        cv.put(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_NOME, servico.getNome());
        cv.put(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_DESCRICAO, servico.getDescricao());

        int linhasAlteradas = db.update(SQLHelperTaFeito.TABELA_SERVICO, cv,
                SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID + " = ?",
                new String[]{String.valueOf(servico.getId())});
        db.close();

        return linhasAlteradas;
    }

    @Override
    public void salvar(Servico servico) {
        if (servico.getId() == 0) {
            this.inserir(servico);
        } else {
            this.atualizar(servico);
        }
    }

    @Override
    public Servico consultar(long id) {

        Servico res = null;

        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelperTaFeito.TABELA_SERVICO;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA + "." + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_FORNECEDOR;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_FORNECEDOR;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_USUARIO;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_USUARIO + "." + SQLHelperTaFeito.TABELA_USUARIO_COLUNA_ID;

        sql = sql + " WHERE " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID + " = ?";
        String args[] = new String[]{"" + id + ""};

        Cursor cursor = db.rawQuery(sql, args);
        if (cursor.moveToNext()) {

            //From SERVICO
            //long idCol = cursor.getLong(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID));
            //long idServCat = cursor.getLong(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA));
            //long idForn = cursor.getLong(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_FORNECEDOR));
            //String nomeCol = cursor.getString(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_NOME));
            //String descCol = cursor.getString(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_DESCRICAO));
            long idCol = cursor.getLong(0);
            long idServCat = cursor.getLong(1);
            long idForn = cursor.getLong(2);
            String nomeCol = cursor.getString(3);
            String descCol = cursor.getString(4);

            //From SERVICO_CATEGORIA
            String nomeSerCat = cursor.getString(6);
            String descSerCat = cursor.getString(7);

            //From FORNECEDOR
            String cnpjForn = cursor.getString(9);

            //From USUARIO
            int habUsu = cursor.getInt(11);
            String nomeUsu = cursor.getString(12);
            String endUsu = cursor.getString(13);
            String emailCol = cursor.getString(14);
            int telCol = cursor.getInt(15);

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
            servico.setId(idCol);
            servico.setServicoCategoria(servicoCategoria);
            servico.setFornecedor(fornecedor);
            servico.setNome(nomeCol);
            servico.setDescricao(descCol);

            res = servico;
        }

        cursor.close();

        return res;

    }

    @Override
    public int excluir(Servico servico) {

        SQLiteDatabase db = bd.getWritableDatabase();

        int linhasExcluidas = db.delete(SQLHelperTaFeito.TABELA_SERVICO,
                SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID + " = ?",
                new String[]{String.valueOf(servico.getId())});
        db.close();

        return linhasExcluidas;
    }

    @Override
    public List<Servico> listar() {

        List<Servico> res = new ArrayList<Servico>();

        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelperTaFeito.TABELA_SERVICO;

        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA + "." + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_FORNECEDOR;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_FORNECEDOR;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_USUARIO;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_USUARIO + "." + SQLHelperTaFeito.TABELA_USUARIO_COLUNA_ID;

        //sql = sql + " WHERE " + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_XXX + " = ?";
        //String args[] = new String[]{"" + "XXX" + ""};

        sql = sql + " ORDER BY " + "4";

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {

            //From SERVICO
            //long idCol = cursor.getLong(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID));
            //long idServCat = cursor.getLong(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA));
            //long idForn = cursor.getLong(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_FORNECEDOR));
            //String nomeCol = cursor.getString(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_NOME));
            //String descCol = cursor.getString(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_DESCRICAO));
            long idCol = cursor.getLong(0);
            long idServCat = cursor.getLong(1);
            long idForn = cursor.getLong(2);
            String nomeCol = cursor.getString(3);
            String descCol = cursor.getString(4);

            //From SERVICO_CATEGORIA
            String nomeSerCat = cursor.getString(6);
            String descSerCat = cursor.getString(7);

            //From FORNECEDOR
            String cnpjForn = cursor.getString(9);

            //From USUARIO
            int habUsu = cursor.getInt(11);
            String nomeUsu = cursor.getString(12);
            String endUsu = cursor.getString(13);
            String emailCol = cursor.getString(14);
            int telCol = cursor.getInt(15);

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
            servico.setId(idCol);
            servico.setServicoCategoria(servicoCategoria);
            servico.setFornecedor(fornecedor);
            servico.setNome(nomeCol);
            servico.setDescricao(descCol);

            res.add(servico);
        }

        cursor.close();

        return res;
    }

    public List<Servico> listarPorServicoCategoria(ServicoCategoria servicoCat) {

        List<Servico> res = new ArrayList<Servico>();

        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelperTaFeito.TABELA_SERVICO;

        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA + "." + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_FORNECEDOR;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_FORNECEDOR;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_USUARIO;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_USUARIO + "." + SQLHelperTaFeito.TABELA_USUARIO_COLUNA_ID;

        sql = sql + " WHERE " + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA + " = ?";
        String args[] = new String[]{"" + servicoCat.getId() + ""};

        sql = sql + " ORDER BY " + "4";

        Cursor cursor = db.rawQuery(sql, args);
        while (cursor.moveToNext()) {

            //From SERVICO
            //long idCol = cursor.getLong(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID));
            //long idServCat = cursor.getLong(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA));
            //long idForn = cursor.getLong(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_FORNECEDOR));
            //String nomeCol = cursor.getString(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_NOME));
            //String descCol = cursor.getString(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_DESCRICAO));
            long idCol = cursor.getLong(0);
            long idServCat = cursor.getLong(1);
            long idForn = cursor.getLong(2);
            String nomeCol = cursor.getString(3);
            String descCol = cursor.getString(4);

            //From SERVICO_CATEGORIA
            String nomeSerCat = cursor.getString(6);
            String descSerCat = cursor.getString(7);

            //From FORNECEDOR
            String cnpjForn = cursor.getString(9);

            //From USUARIO
            int habUsu = cursor.getInt(11);
            String nomeUsu = cursor.getString(12);
            String endUsu = cursor.getString(13);
            String emailCol = cursor.getString(14);
            int telCol = cursor.getInt(15);

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
            servico.setId(idCol);
            servico.setServicoCategoria(servicoCategoria);
            servico.setFornecedor(fornecedor);
            servico.setNome(nomeCol);
            servico.setDescricao(descCol);

            res.add(servico);
        }

        cursor.close();

        return res;
    }

    public List<Servico> listarPorFornecedor(Fornecedor forn) {

        List<Servico> res = new ArrayList<Servico>();

        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelperTaFeito.TABELA_SERVICO;

        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA + "." + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_FORNECEDOR;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_FORNECEDOR;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_USUARIO;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_USUARIO + "." + SQLHelperTaFeito.TABELA_USUARIO_COLUNA_ID;

        sql = sql + " WHERE " + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_FORNECEDOR + " = ?";
        String args[] = new String[]{"" + forn.getId() + ""};

        sql = sql + " ORDER BY " + "7, 4";

        Cursor cursor = db.rawQuery(sql, args);
        while (cursor.moveToNext()) {

            //From SERVICO
            //long idCol = cursor.getLong(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID));
            //long idServCat = cursor.getLong(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA));
            //long idForn = cursor.getLong(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_FORNECEDOR));
            //String nomeCol = cursor.getString(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_NOME));
            //String descCol = cursor.getString(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_DESCRICAO));
            long idCol = cursor.getLong(0);
            long idServCat = cursor.getLong(1);
            long idForn = cursor.getLong(2);
            String nomeCol = cursor.getString(3);
            String descCol = cursor.getString(4);

            //From SERVICO_CATEGORIA
            String nomeSerCat = cursor.getString(6);
            String descSerCat = cursor.getString(7);

            //From FORNECEDOR
            String cnpjForn = cursor.getString(9);

            //From USUARIO
            int habUsu = cursor.getInt(11);
            String nomeUsu = cursor.getString(12);
            String endUsu = cursor.getString(13);
            String emailCol = cursor.getString(14);
            int telCol = cursor.getInt(15);

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
            servico.setId(idCol);
            servico.setServicoCategoria(servicoCategoria);
            servico.setFornecedor(fornecedor);
            servico.setNome(nomeCol);
            servico.setDescricao(descCol);

            res.add(servico);
        }

        cursor.close();

        return res;
    }

    public List<Servico> listarPorServicoCategoriaPorFornecedor(ServicoCategoria servicoCat, Fornecedor forn) {

        List<Servico> res = new ArrayList<Servico>();

        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelperTaFeito.TABELA_SERVICO;

        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA + "." + SQLHelperTaFeito.TABELA_SERVICO_CATEGORIA_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_FORNECEDOR;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_SERVICO + "." + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_FORNECEDOR;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_USUARIO;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_USUARIO + "." + SQLHelperTaFeito.TABELA_USUARIO_COLUNA_ID;

        sql = sql + " WHERE " + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA + " = ?";
        sql = sql + " AND " + SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_FORNECEDOR + " = ?";
        String args[] = new String[]{"" + servicoCat.getId() + "","" + forn.getId() + ""};

        sql = sql + " ORDER BY " + "7, 4";

        Cursor cursor = db.rawQuery(sql, args);
        while (cursor.moveToNext()) {

            //From SERVICO
            //long idCol = cursor.getLong(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID));
            //long idServCat = cursor.getLong(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA));
            //long idForn = cursor.getLong(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_ID_FORNECEDOR));
            //String nomeCol = cursor.getString(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_NOME));
            //String descCol = cursor.getString(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_SERVICO_COLUNA_DESCRICAO));
            long idCol = cursor.getLong(0);
            long idServCat = cursor.getLong(1);
            long idForn = cursor.getLong(2);
            String nomeCol = cursor.getString(3);
            String descCol = cursor.getString(4);

            //From SERVICO_CATEGORIA
            String nomeSerCat = cursor.getString(6);
            String descSerCat = cursor.getString(7);

            //From FORNECEDOR
            String cnpjForn = cursor.getString(9);

            //From USUARIO
            int habUsu = cursor.getInt(11);
            String nomeUsu = cursor.getString(12);
            String endUsu = cursor.getString(13);
            String emailCol = cursor.getString(14);
            int telCol = cursor.getInt(15);

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
            servico.setId(idCol);
            servico.setServicoCategoria(servicoCategoria);
            servico.setFornecedor(fornecedor);
            servico.setNome(nomeCol);
            servico.setDescricao(descCol);

            res.add(servico);
        }

        cursor.close();

        return res;
    }


}
