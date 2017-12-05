package br.pe.recife.tafeito.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.pe.recife.tafeito.negocio.Cliente;
import br.pe.recife.tafeito.util.SQLHelperTaFeito;
import br.pe.recife.tafeito.util.Util;

public class ClienteDAO implements IDAOson<Cliente> {

    private static ClienteDAO instancia;
    private SQLHelperTaFeito bd;

    public static ClienteDAO getInstancia(Context context) {

        if (instancia == null) {
            instancia = new ClienteDAO(context);
        }

        return instancia;
    }

    private ClienteDAO(Context context) {
        this.bd = SQLHelperTaFeito.getInstancia(context);
    }

    private long inserir(Cliente cliente) {

        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(SQLHelperTaFeito.TABELA_CLIENTE_COLUNA_ID, cliente.getId());
        cv.put(SQLHelperTaFeito.TABELA_CLIENTE_COLUNA_CPF, cliente.getCpf());

        long id = db.insert(SQLHelperTaFeito.TABELA_CLIENTE, null, cv);

        if (id != -1) {
            cliente.setId(id);
        }

        db.close();

        return id;
    }

    private int atualizar(Cliente cliente) {

        SQLiteDatabase db = bd.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(SQLHelperTaFeito.TABELA_CLIENTE_COLUNA_CPF, cliente.getCpf());

        int linhasAlteradas = db.update(SQLHelperTaFeito.TABELA_CLIENTE, cv,
                SQLHelperTaFeito.TABELA_CLIENTE_COLUNA_ID + " = ?",
                new String[]{String.valueOf(cliente.getId())});
        db.close();

        return linhasAlteradas;
    }

    @Override
    public void salvar(Cliente cliente, boolean novo) {
        if (novo) {
            this.inserir(cliente);
        } else {
            this.atualizar(cliente);
        }
    }

    @Override
    public Cliente consultar(long id) {

        Cliente res = null;

        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelperTaFeito.TABELA_CLIENTE;

        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_USUARIO;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_CLIENTE + "." + SQLHelperTaFeito.TABELA_CLIENTE_COLUNA_ID;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_USUARIO + "." + SQLHelperTaFeito.TABELA_USUARIO_COLUNA_ID;

        sql = sql + " WHERE " + SQLHelperTaFeito.TABELA_CLIENTE + "." + SQLHelperTaFeito.TABELA_CLIENTE_COLUNA_ID + " = ?";
        String args[] = new String[]{"" + id + ""};

        Cursor cursor = db.rawQuery(sql, args);
        if (cursor.moveToNext()) {

            //From CLIENTE
            long idCol = cursor.getLong(0);
            String cpfCol = cursor.getString(1);

            //From USUARIO
            int habUsuCliente = cursor.getInt(3);
            String nomeUsuCliente = cursor.getString(4);
            String endUsuCliente = cursor.getString(5);
            String emailCol = cursor.getString(6);
            int telCol = cursor.getInt(7);

            Cliente cliente = new Cliente();
            cliente.setId(idCol);
            cliente.setHabilitado(Util.valorBooleano(habUsuCliente));
            cliente.setNome(nomeUsuCliente);
            cliente.setEndereco(endUsuCliente);
            cliente.setEmail(emailCol);
            cliente.setTelefone(telCol);
            cliente.setCpf(cpfCol);

            res = cliente;
        }

        cursor.close();

        return res;

    }

    @Override
    public int excluir(Cliente cliente) {

        SQLiteDatabase db = bd.getWritableDatabase();

        int linhasExcluidas = db.delete(SQLHelperTaFeito.TABELA_CLIENTE,
                SQLHelperTaFeito.TABELA_CLIENTE_COLUNA_ID + " = ?",
                new String[]{String.valueOf(cliente.getId())});
        db.close();

        return linhasExcluidas;
    }

    @Override
    public List<Cliente> listar() {

        List<Cliente> res = new ArrayList<Cliente>();

        SQLiteDatabase db = bd.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelperTaFeito.TABELA_CLIENTE;

        sql = sql + " INNER JOIN " + SQLHelperTaFeito.TABELA_USUARIO;
        sql = sql + " ON " + SQLHelperTaFeito.TABELA_CLIENTE + "." + SQLHelperTaFeito.TABELA_CLIENTE_COLUNA_ID;
        sql = sql + " = " + SQLHelperTaFeito.TABELA_USUARIO + "." + SQLHelperTaFeito.TABELA_USUARIO_COLUNA_ID;

        //sql = sql + " WHERE " + SQLHelperTaFeito.TABELA_CLIENTE_COLUNA_XXX + " = ?";
        //String args[] = new String[]{"" + "XXX" + ""};

        sql = sql + " ORDER BY " + "5";

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {

            //From CLIENTE
            long idCol = cursor.getLong(0);
            String cpfCol = cursor.getString(1);

            //From USUARIO
            int habUsuCliente = cursor.getInt(3);
            String nomeUsuCliente = cursor.getString(4);
            String endUsuCliente = cursor.getString(5);
            String emailCol = cursor.getString(6);
            int telCol = cursor.getInt(7);

            Cliente cliente = new Cliente();
            cliente.setId(idCol);
            cliente.setHabilitado(Util.valorBooleano(habUsuCliente));
            cliente.setNome(nomeUsuCliente);
            cliente.setEndereco(endUsuCliente);
            cliente.setEmail(emailCol);
            cliente.setTelefone(telCol);
            cliente.setCpf(cpfCol);

            res.add(cliente);
        }

        cursor.close();

        return res;
    }

}
