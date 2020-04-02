package sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    private static final String name = "banco.db";
    private static final int version = 1;

    public Conexao(Context context){
        super(context, name, null, version);
    }

    private String tabelaUsuario = "CREATE TABLE IF NOT EXISTS usuario(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "email TEXT, senha TEXT, nomeEmpresa TEXT, cnpjEmpresa TEXT, nomeMotorista TEXT, rgMotorista TEXT, " +
            "telefoneMotorista TEXT, caminhoLogo TEXT, caminhoAssinatura TEXT)";

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(tabelaUsuario);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(tabelaUsuario);
    }
}
