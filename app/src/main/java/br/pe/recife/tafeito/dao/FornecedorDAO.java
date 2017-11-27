package br.pe.recife.tafeito.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.pe.recife.tafeito.negocio.Fornecedor;
import br.pe.recife.tafeito.util.SQLHelperTaFeito;
import br.pe.recife.tafeito.util.Util;

public class FornecedorDAO implements IDAOson<Fornecedor> {

    private static FornecedorDAO instancia;
    private SQLHelperTaFeito bd;

    public static FornecedorDAO getInstancia(Context context) {

        if (instancia == null) {
            instancia = new FornecedorDAO(context);
        }

        return instancia;
    }

    private FornecedorDAO(Context context) {
        this.bd = SQLHelperTaFeito.getInstancia(context);
    }

    private long inserir(Fornecedor fornecedor) {

        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID, fornecedor.getId());
        cv.put(SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_CNPJ, fornecedor.getCnpj());

        long id = db.insert(SQLHelperTaFeito.TABELA_FORNECEDOR, null, cv);

        if (id != -1) {
            fornecedor.setId(id);
        }

        db.close();

        return id;
    }

    private int atualizar(Fornecedor fornecedor) {

        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_CNPJ, fornecedor.getCnpj());

        int linhasAlteradas = db.update(SQLHelperTaFeito.TABELA_FORNECEDOR, cv,
                SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID + " = ?",
                new String[]{String.valueOf(fornecedor.getId())});
        db.close();

        return linhasAlteradas;
    }

    @Override
    public void salvar(Fornecedor fornecedor, boolean novo) {
        if (novo) {
            this.inserir(fornecedor);
        } else {
            this.atualizar(fornecedor);
        }
    }

    @Override
    public Fornecedor consultar(long id) {

        Fornecedor res = null;

        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelperTaFeito.TABELA_FORNECEDOR;

        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_USUARIO;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_USUARIO + "." + SQLHelperTaFeito.TABELA_USUARIO_COLUNA_ID;

        sql = sql + " WHERE " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID + " = ?";
        String args[] = new String[]{"" + id + ""};

        Cursor cursor = db.rawQuery(sql, args);
        if (cursor.moveToNext()) {

            //
            long idCol = cursor.getLong(0);
            String cnpjCol = cursor.getString(1);

            //From USUARIO
            int habUsu = cursor.getInt(3);
            String nomeUsu = cursor.getString(4);
            String endUsu = cursor.getString(5);
            String emailCol = cursor.getString(6);
            int telCol = cursor.getInt(7);

            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setId(idCol);
            fornecedor.setHabilitado(Util.valorBooleano(habUsu));
            fornecedor.setNome(nomeUsu);
            fornecedor.setEndereco(endUsu);
            fornecedor.setEmail(emailCol);
            fornecedor.setTelefone(telCol);
            fornecedor.setCnpj(cnpjCol);


            res = fornecedor;
        }

        cursor.close();

        return res;

    }

    @Override
    public int excluir(Fornecedor fornecedor) {

        SQLiteDatabase db = bd.getWritableDatabase();

        int linhasExcluidas = db.delete(SQLHelperTaFeito.TABELA_FORNECEDOR,
                SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID + " = ?",
                new String[]{String.valueOf(fornecedor.getId())});
        db.close();

        return linhasExcluidas;
    }

    @Override
    public List<Fornecedor> listar() {

        List<Fornecedor> res = new ArrayList<Fornecedor>();

        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelperTaFeito.TABELA_FORNECEDOR;

        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_USUARIO;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_USUARIO + "." + SQLHelperTaFeito.TABELA_USUARIO_COLUNA_ID;

        //sql = sql + " WHERE " + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_XXX + " = ?";
        //String args[] = new String[]{"" + "XXX" + ""};

        sql = sql + " ORDER BY " + "5";

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {

            //
            long idCol = cursor.getLong(0);
            String cnpjCol = cursor.getString(1);

            //From USUARIO
            int habUsu = cursor.getInt(3);
            String nomeUsu = cursor.getString(4);
            String endUsu = cursor.getString(5);
            String emailCol = cursor.getString(6);
            int telCol = cursor.getInt(7);

            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setId(idCol);
            fornecedor.setHabilitado(Util.valorBooleano(habUsu));
            fornecedor.setNome(nomeUsu);
            fornecedor.setEndereco(endUsu);
            fornecedor.setEmail(emailCol);
            fornecedor.setTelefone(telCol);
            fornecedor.setCnpj(cnpjCol);

            res.add(fornecedor);
        }

        cursor.close();

        return res;
    }
}
