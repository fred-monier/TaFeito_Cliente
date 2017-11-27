package br.pe.recife.tafeito.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelperTaFeito extends SQLiteOpenHelper {

    private static SQLHelperTaFeito instancia;

    private static final String NOME_BANCO = "DB_TAFEITO";
    private static final int VERSAO_BANCO = '4';

    public static final String TABELA_ACESSO = "ACESSO";
    public static final String TABELA_ACESSO_COLUNA_ID = "ID";
    public static final String TABELA_ACESSO_COLUNA_LOGIN = "LOGIN";
    public static final String TABELA_ACESSO_COLUNA_SENHA = "SENHA";

    public static final String TABELA_USUARIO = "USUARIO";
    public static final String TABELA_USUARIO_COLUNA_ID = "ID";
    public static final String TABELA_USUARIO_COLUNA_HABILITADO = "HABILITADO";
    public static final String TABELA_USUARIO_COLUNA_NOME = "NOME";
    public static final String TABELA_USUARIO_COLUNA_ENDERECO = "ENDERECO";
    public static final String TABELA_USUARIO_COLUNA_EMAIL = "EMAIL";
    public static final String TABELA_USUARIO_COLUNA_TELEFONE = "TELEFONE";

    public static final String TABELA_FORNECEDOR = "FORNECEDOR";
    public static final String TABELA_FORNECEDOR_COLUNA_ID = "ID";
    public static final String TABELA_FORNECEDOR_COLUNA_CNPJ = "CNPJ";

    public static final String TABELA_CLIENTE = "CLIENTE";
    public static final String TABELA_CLIENTE_COLUNA_ID = "ID";
    public static final String TABELA_CLIENTE_COLUNA_CPF = "CPF";

    public static final String TABELA_SERVICO_CATEGORIA = "SERVICO_CATEGORIA";
    public static final String TABELA_SERVICO_CATEGORIA_COLUNA_ID = "ID";
    public static final String TABELA_SERVICO_CATEGORIA_COLUNA_NOME = "NOME";
    public static final String TABELA_SERVICO_CATEGORIA_COLUNA_DESCRICAO = "DESCRICAO";

    public static final String TABELA_SERVICO = "SERVICO";
    public static final String TABELA_SERVICO_COLUNA_ID = "ID";
    public static final String TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA = "ID_SERVICO_CATEGORIA";
    public static final String TABELA_SERVICO_COLUNA_ID_FORNECEDOR = "ID_FORNECEDOR";
    public static final String TABELA_SERVICO_COLUNA_NOME = "NOME";
    public static final String TABELA_SERVICO_COLUNA_DESCRICAO = "DESCRICAO";

    public static final String TABELA_OFERTA = "OFERTA";
    public static final String TABELA_OFERTA_COLUNA_ID = "ID";
    public static final String TABELA_OFERTA_COLUNA_ID_SERVICO = "ID_SERVICO";
    public static final String TABELA_OFERTA_COLUNA_ANO_INICIO = "ANO_INICIO";
    public static final String TABELA_OFERTA_COLUNA_MES_INICIO = "MES_INICIO";
    public static final String TABELA_OFERTA_COLUNA_DIA_INICIO = "DIA_INICIO";
    public static final String TABELA_OFERTA_COLUNA_HORA_INICIO = "HORA_INICIO";
    public static final String TABELA_OFERTA_COLUNA_MINUTO_INICIO = "MINUTO_INICIO";
    public static final String TABELA_OFERTA_COLUNA_ANO_FIM = "ANO_FIM";
    public static final String TABELA_OFERTA_COLUNA_MES_FIM = "MES_FIM";
    public static final String TABELA_OFERTA_COLUNA_DIA_FIM = "DIA_FIM";
    public static final String TABELA_OFERTA_COLUNA_HORA_FIM = "HORA_FIM";
    public static final String TABELA_OFERTA_COLUNA_MINUTO_FIM = "MINUTO_FIM";

    public static final String TABELA_AGENDAMENTO = "AGENDAMENTO";
    public static final String TABELA_AGENDAMENTO_COLUNA_ID = "ID";
    public static final String TABELA_AGENDAMENTO_COLUNA_ID_OFERTA = "ID_OFERTA";
    public static final String TABELA_AGENDAMENTO_COLUNA_ID_CLIENTE = "ID_AGENDAMENTO";
    public static final String TABELA_AGENDAMENTO_COLUNA_ANO_REALIZADO = "ANO_REALIZADO";
    public static final String TABELA_AGENDAMENTO_COLUNA_MES_REALIZADO = "MES_REALIZADO";
    public static final String TABELA_AGENDAMENTO_COLUNA_DIA_REALIZADO = "DIA_REALIZADO";
    public static final String TABELA_AGENDAMENTO_COLUNA_HORA_REALIZADO = "HORA_REALIZADO";
    public static final String TABELA_AGENDAMENTO_COLUNA_MINUTO_REALIZADO = "MINUTO_REALIZADO";
    public static final String TABELA_AGENDAMENTO_COLUNA_ANO_CANCELADO = "ANO_CANCELADO";
    public static final String TABELA_AGENDAMENTO_COLUNA_MES_CANCELADO = "MES_CANCELADO";
    public static final String TABELA_AGENDAMENTO_COLUNA_DIA_CANCELADO = "DIA_CANCELADO";
    public static final String TABELA_AGENDAMENTO_COLUNA_HORA_CANCELADO = "HORA_CANCELADO";
    public static final String TABELA_AGENDAMENTO_COLUNA_MINUTO_CANCELADO = "MINUTO_CANCELADO";

    public static SQLHelperTaFeito getInstancia(Context context) {
        SQLHelperTaFeito res;
        if (instancia == null) {
            res = new SQLHelperTaFeito(context);
        } else {
            res = instancia;
        }
        return res;
    }

    private SQLHelperTaFeito(Context context) {
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABELA_ACESSO + " (" +
                TABELA_ACESSO_COLUNA_ID + " INTEGER PRIMARY KEY, " +
                TABELA_ACESSO_COLUNA_LOGIN + " TEXT NOT NULL, " +
                TABELA_ACESSO_COLUNA_SENHA + " TEXT NOT NULL, " +
                "CONSTRAINT IND_LOGIN UNIQUE (" + TABELA_ACESSO_COLUNA_LOGIN + "))"
        );

        db.execSQL("CREATE TABLE " + TABELA_USUARIO + " (" +
                TABELA_USUARIO_COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABELA_USUARIO_COLUNA_HABILITADO + " INTEGER NOT NULL DEFAULT 0, " +
                TABELA_USUARIO_COLUNA_NOME + " TEXT NOT NULL, " +
                TABELA_USUARIO_COLUNA_ENDERECO + " TEXT NOT NULL, " +
                TABELA_USUARIO_COLUNA_EMAIL + " TEXT NOT NULL, " +
                TABELA_USUARIO_COLUNA_TELEFONE + " INTEGER NOT NULL)"
        );

        db.execSQL("CREATE TABLE " + TABELA_FORNECEDOR + " (" +
                TABELA_FORNECEDOR_COLUNA_ID + " INTEGER PRIMARY KEY, " +
                TABELA_FORNECEDOR_COLUNA_CNPJ + " TEXT, " +
                "FOREIGN KEY(" + TABELA_FORNECEDOR_COLUNA_ID + ") REFERENCES " +
                TABELA_USUARIO + "(" + TABELA_USUARIO_COLUNA_ID + "))"
        );

        db.execSQL("CREATE TABLE " + TABELA_CLIENTE + " (" +
                TABELA_CLIENTE_COLUNA_ID + " INTEGER PRIMARY KEY, " +
                TABELA_CLIENTE_COLUNA_CPF + " TEXT, " +
                "FOREIGN KEY(" + TABELA_CLIENTE_COLUNA_ID + ") REFERENCES " +
                TABELA_USUARIO + "(" + TABELA_USUARIO_COLUNA_ID + "))"
        );

        db.execSQL("CREATE TABLE " + TABELA_SERVICO_CATEGORIA + " (" +
                TABELA_SERVICO_CATEGORIA_COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABELA_SERVICO_CATEGORIA_COLUNA_NOME + " TEXT NOT NULL, " +
                TABELA_SERVICO_CATEGORIA_COLUNA_DESCRICAO + " TEXT)"
        );

        db.execSQL("CREATE TABLE " + TABELA_SERVICO + " (" +
                TABELA_SERVICO_COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA + " INTEGER NOT NULL, " +
                TABELA_SERVICO_COLUNA_ID_FORNECEDOR + " INTEGER NOT NULL, " +
                TABELA_SERVICO_COLUNA_NOME + " TEXT NOT NULL, " +
                TABELA_SERVICO_COLUNA_DESCRICAO + " TEXT, " +
                "FOREIGN KEY(" + TABELA_SERVICO_COLUNA_ID_SERVICO_CATEGORIA + ") REFERENCES " +
                        TABELA_SERVICO_CATEGORIA + "(" + TABELA_SERVICO_CATEGORIA_COLUNA_ID + "), " +
                "FOREIGN KEY(" + TABELA_SERVICO_COLUNA_ID_FORNECEDOR + ") REFERENCES " +
                TABELA_FORNECEDOR + "(" + TABELA_FORNECEDOR_COLUNA_ID + "))"
        );

        db.execSQL("CREATE TABLE " + TABELA_OFERTA + " (" +
                TABELA_OFERTA_COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABELA_OFERTA_COLUNA_ID_SERVICO + " INTEGER NOT NULL, " +
                TABELA_OFERTA_COLUNA_ANO_INICIO + " INTEGER NOT NULL, " +
                TABELA_OFERTA_COLUNA_MES_INICIO + " INTEGER NOT NULL, " +
                TABELA_OFERTA_COLUNA_DIA_INICIO + " INTEGER NOT NULL, " +
                TABELA_OFERTA_COLUNA_HORA_INICIO + " INTEGER NOT NULL, " +
                TABELA_OFERTA_COLUNA_MINUTO_INICIO + " INTEGER NOT NULL, " +
                TABELA_OFERTA_COLUNA_ANO_FIM + " INTEGER NOT NULL, " +
                TABELA_OFERTA_COLUNA_MES_FIM + " INTEGER NOT NULL, " +
                TABELA_OFERTA_COLUNA_DIA_FIM + " INTEGER NOT NULL, " +
                TABELA_OFERTA_COLUNA_HORA_FIM + " INTEGER NOT NULL, " +
                TABELA_OFERTA_COLUNA_MINUTO_FIM + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + TABELA_OFERTA_COLUNA_ID_SERVICO + ") REFERENCES " +
                TABELA_SERVICO + "(" + TABELA_SERVICO_COLUNA_ID + "))"
        );

        db.execSQL("CREATE TABLE " + TABELA_AGENDAMENTO + " (" +
                TABELA_AGENDAMENTO_COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TABELA_AGENDAMENTO_COLUNA_ID_OFERTA + " INTEGER NOT NULL, " +
                TABELA_AGENDAMENTO_COLUNA_ID_CLIENTE + " INTEGER NOT NULL, " +
                TABELA_AGENDAMENTO_COLUNA_ANO_REALIZADO + " INTEGER, " +
                TABELA_AGENDAMENTO_COLUNA_MES_REALIZADO + " INTEGER, " +
                TABELA_AGENDAMENTO_COLUNA_DIA_REALIZADO + " INTEGER, " +
                TABELA_AGENDAMENTO_COLUNA_HORA_REALIZADO + " INTEGER, " +
                TABELA_AGENDAMENTO_COLUNA_MINUTO_REALIZADO + " INTEGER, " +
                TABELA_AGENDAMENTO_COLUNA_ANO_CANCELADO + " INTEGER, " +
                TABELA_AGENDAMENTO_COLUNA_MES_CANCELADO + " INTEGER, " +
                TABELA_AGENDAMENTO_COLUNA_DIA_CANCELADO + " INTEGER, " +
                TABELA_AGENDAMENTO_COLUNA_HORA_CANCELADO + " INTEGER, " +
                TABELA_AGENDAMENTO_COLUNA_MINUTO_CANCELADO + " INTEGER, " +
                "FOREIGN KEY(" + TABELA_AGENDAMENTO_COLUNA_ID_OFERTA + ") REFERENCES " +
                TABELA_OFERTA + "(" + TABELA_OFERTA_COLUNA_ID + "), " +
                "FOREIGN KEY(" + TABELA_AGENDAMENTO_COLUNA_ID_CLIENTE + ") REFERENCES " +
                        TABELA_CLIENTE + "(" + TABELA_CLIENTE_COLUNA_ID + "), " +
                "CONSTRAINT IND_OFERTA_CLIENTE UNIQUE (" + TABELA_AGENDAMENTO_COLUNA_ID_OFERTA +
                ", " + TABELA_AGENDAMENTO_COLUNA_ID_CLIENTE + "))"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABELA_AGENDAMENTO);
        db.execSQL("DROP TABLE " + TABELA_OFERTA);
        db.execSQL("DROP TABLE " + TABELA_SERVICO);
        db.execSQL("DROP TABLE " + TABELA_SERVICO_CATEGORIA);
        db.execSQL("DROP TABLE " + TABELA_CLIENTE);
        db.execSQL("DROP TABLE " + TABELA_FORNECEDOR);
        db.execSQL("DROP TABLE " + TABELA_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_ACESSO);
        onCreate(db);
    }
}
