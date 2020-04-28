package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.Marca;
import sql.Conexao;

public class MarcaDao {

    private Conexao conexao;
    private SQLiteDatabase banco;
    private Marca marca;

    public MarcaDao(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
        banco = conexao.getReadableDatabase();
    }

    public Marca recupera(){
        Cursor cursor = banco.rawQuery("SELECT * FROM marca", null);
        //cursor.moveToFirst();

        while (cursor.moveToNext()){
            marca = new Marca();
            marca.setId(cursor.getInt(0));
            marca.setMarca(cursor.getString(1));
        }
        return marca;

    }

    public long inserir(Marca marca){
        ContentValues values = new ContentValues();
        values.put("marca", marca.getMarca());
        return banco.insert("marca", null, values);
    }
}
