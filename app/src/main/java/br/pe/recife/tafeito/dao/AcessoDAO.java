package br.pe.recife.tafeito.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.pe.recife.tafeito.negocio.Acesso;
import br.pe.recife.tafeito.util.SQLHelperTaFeito;

public class AcessoDAO {

    private static AcessoDAO instancia;
    private SQLHelperTaFeito bd;

    public static AcessoDAO getInstancia(Context context) {

        if (instancia == null) {
            instancia = new AcessoDAO(context);
        }

        return instancia;
    }

    private AcessoDAO(Context context) {
        this.bd = SQLHelperTaFeito.getInstancia(context);
    }

    public long inserir(Acesso acesso) {

        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(SQLHelperTaFeito.TABELA_ACESSO_COLUNA_ID, acesso.getId());
        cv.put(SQLHelperTaFeito.TABELA_ACESSO_COLUNA_LOGIN, acesso.getLogin());
        cv.put(SQLHelperTaFeito.TABELA_ACESSO_COLUNA_SENHA, acesso.getSenha());

        long id = db.insert(SQLHelperTaFeito.TABELA_ACESSO, null, cv);

        db.close();

        return id;
    }

    public int atualizar(Acesso acesso) {

        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(SQLHelperTaFeito.TABELA_ACESSO_COLUNA_LOGIN, acesso.getLogin());
        cv.put(SQLHelperTaFeito.TABELA_ACESSO_COLUNA_SENHA, acesso.getSenha());

        int linhasAlteradas = db.update(SQLHelperTaFeito.TABELA_ACESSO, cv,
                SQLHelperTaFeito.TABELA_ACESSO_COLUNA_ID + " = ?",
                new String[]{String.valueOf(acesso.getId())});
        db.close();

        return linhasAlteradas;
    }

    public long buscarPorLoginPorSenhaFornecedor(String login, String senha) {

        long res = 0;

        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelperTaFeito.TABELA_ACESSO;

        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_FORNECEDOR;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_ACESSO + "." + SQLHelperTaFeito.TABELA_ACESSO_COLUNA_ID;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_FORNECEDOR + "." + SQLHelperTaFeito.TABELA_FORNECEDOR_COLUNA_ID;

        sql = sql + " WHERE " + SQLHelperTaFeito.TABELA_ACESSO_COLUNA_LOGIN + " = ?";
        sql = sql + " AND " + SQLHelperTaFeito.TABELA_ACESSO_COLUNA_SENHA + " = ?";
        String args[] = new String[]{"" + login + "","" + senha + ""};

        Cursor cursor = db.rawQuery(sql, args);
        if (cursor.moveToNext()) {
            res = cursor.getLong(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_ACESSO_COLUNA_ID));
        }

        cursor.close();

        return res;

    }

    public long buscarPorLoginPorSenhaCliente(String login, String senha) {

        long res = 0;

        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelperTaFeito.TABELA_ACESSO;

        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_CLIENTE;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_ACESSO + "." + SQLHelperTaFeito.TABELA_ACESSO_COLUNA_ID;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_CLIENTE + "." + SQLHelperTaFeito.TABELA_CLIENTE_COLUNA_ID;

        sql = sql + " WHERE " + SQLHelperTaFeito.TABELA_ACESSO_COLUNA_LOGIN + " = ?";
        sql = sql + " AND " + SQLHelperTaFeito.TABELA_ACESSO_COLUNA_SENHA + " = ?";
        String args[] = new String[]{"" + login + "","" + senha + ""};

        Cursor cursor = db.rawQuery(sql, args);
        if (cursor.moveToNext()) {
            res = cursor.getLong(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_ACESSO_COLUNA_ID));
        }

        cursor.close();

        return res;

    }

    public boolean existePorLogin(String login) {

        boolean res = false;

        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelperTaFeito.TABELA_ACESSO;

        sql = sql + " WHERE " + SQLHelperTaFeito.TABELA_ACESSO_COLUNA_LOGIN + " = ?";
        String args[] = new String[]{"" + login + ""};

        Cursor cursor = db.rawQuery(sql, args);
        if (cursor.moveToNext()) {
            res = true;
        }

        cursor.close();

        return res;

    }

    public Acesso consultar(long id) {

        Acesso res = null;

        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelperTaFeito.TABELA_ACESSO;

        sql = sql + " WHERE " + SQLHelperTaFeito.TABELA_ACESSO + "." + SQLHelperTaFeito.TABELA_ACESSO_COLUNA_ID + " = ?";
        String args[] = new String[]{"" + id + ""};

        Cursor cursor = db.rawQuery(sql, args);
        if (cursor.moveToNext()) {

            long idCol = cursor.getLong(0);
            String loginCol = cursor.getString(1);
            String senhaCol = cursor.getString(6);

            Acesso acesso = new Acesso();
            acesso.setId(idCol);
            acesso.setLogin(loginCol);
            acesso.setSenha(senhaCol);

            res = acesso;
        }

        cursor.close();

        return res;

    }

    public int excluir(Acesso acesso) {

        SQLiteDatabase db = bd.getWritableDatabase();

        int linhasExcluidas = db.delete(SQLHelperTaFeito.TABELA_ACESSO,
                SQLHelperTaFeito.TABELA_ACESSO_COLUNA_ID + " = ?",
                new String[]{String.valueOf(acesso.getId())});
        db.close();

        return linhasExcluidas;
    }

    public List<Acesso> listar() {

        List<Acesso> res = new ArrayList<Acesso>();

        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelperTaFeito.TABELA_ACESSO;

        //sql = sql + " WHERE " + SQLHelperTaFeito.TABELA_ACESSO_COLUNA_XXX + " = ?";
        //String args[] = new String[]{"" + "XXX" + ""};

        sql = sql + " ORDER BY " + SQLHelperTaFeito.TABELA_ACESSO_COLUNA_ID;

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {

            long idCol = cursor.getLong(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_ACESSO_COLUNA_ID));
            String loginCol = cursor.getString(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_ACESSO_COLUNA_LOGIN));
            String senhaCol = cursor.getString(cursor.getColumnIndex(SQLHelperTaFeito.TABELA_ACESSO_COLUNA_SENHA));

            Acesso acesso = new Acesso();
            acesso.setId(idCol);
            acesso.setLogin(loginCol);
            acesso.setSenha(senhaCol);

            res.add(acesso);
        }

        cursor.close();

        return res;
    }


}
