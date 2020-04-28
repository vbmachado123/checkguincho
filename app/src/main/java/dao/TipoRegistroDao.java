package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.TipoRegistro;
import sql.Conexao;

public class TipoRegistroDao {

    private Conexao conexao;
    private SQLiteDatabase banco;
    private TipoRegistro tipoRegistro;

    public TipoRegistroDao(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
        banco = conexao.getReadableDatabase();
    }

    public TipoRegistro recupera(){
        Cursor cursor = banco.rawQuery("SELECT * FROM tipoRegistro", null);
        //cursor.moveToFirst();
        while (cursor.moveToNext()){
            tipoRegistro = new TipoRegistro();
            tipoRegistro.setId(cursor.getInt(0));
            tipoRegistro.setTipoRegistro(cursor.getString(1));
        }
            return tipoRegistro;
    }

    public long inserir(TipoRegistro tipoRegistro){
        ContentValues values = new ContentValues();
        values.put("tipoRegistro", tipoRegistro.getTipoRegistro());
        return banco.insert("tipoRegistro", null, values);
    }
}
