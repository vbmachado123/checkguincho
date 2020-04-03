package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.Cor;
import sql.Conexao;

public class CorDao {

    private Conexao conexao;
    private SQLiteDatabase banco;
    private Cor cor;

    public CorDao(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
        banco = conexao.getReadableDatabase();
    }

    public Cor recupera(){
        Cursor cursor = banco.rawQuery("SELECT * FROM cor", null);
        cursor.moveToFirst();

        while (cursor.moveToNext()){
            cor = new Cor();
            cor.setId(cursor.getInt(0));
            cor.setCor(cursor.getString(1));
        }
        return cor;
    }

    public long inserir(Cor cor){
        ContentValues values = new ContentValues();
        values.put("cor", cor.getCor());
        return banco.insert("cor", null, values);
    }
}
