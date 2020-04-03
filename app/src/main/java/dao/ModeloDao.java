package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.Modelo;
import sql.Conexao;

public class ModeloDao {

    private Conexao conexao;
    private SQLiteDatabase banco;
    private Modelo modelo;

    public ModeloDao(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
        banco = conexao.getReadableDatabase();
    }

    public Modelo recupera(){
        Cursor cursor = banco.rawQuery("SELECT * FROM modelo", null);
        cursor.moveToFirst();

        while (cursor.moveToNext()){
            modelo = new Modelo();
            modelo.setId(cursor.getInt(0));
            modelo.setIdMarca(cursor.getInt(1));
            modelo.setModelo(cursor.getString(2));
        }
        return modelo;
    }

    public long inserir(Modelo modelo){
        ContentValues values = new ContentValues();
        values.put("idMarca", modelo.getIdMarca());
        values.put("modelo", modelo.getModelo());
        return banco.insert("modelo", null, values);
    }

}
