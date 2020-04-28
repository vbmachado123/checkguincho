package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.FotosInspecao;
import sql.Conexao;

public class FotosDao {

    private Conexao conexao;
    private SQLiteDatabase banco;
    private FotosInspecao fotosInspecao;

    public FotosDao(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
        banco = conexao.getReadableDatabase();
    }

    public FotosInspecao recupera(){
        Cursor cursor = banco.rawQuery("SELECT * FROM fotosInspecao", null);
        //cursor.moveToFirst();

        while (cursor.moveToNext()){
            fotosInspecao = new FotosInspecao();
            fotosInspecao.setId(cursor.getInt(0));
            fotosInspecao.setIdInspecao(cursor.getInt(1));
            fotosInspecao.setCaminhoFotoPainel(cursor.getString(2));
            fotosInspecao.setCaminhoFotoFrente(cursor.getString(3));
            fotosInspecao.setCaminhoFotoLado(cursor.getString(4));
        }
        return fotosInspecao;
    }

    public long inserir(FotosInspecao fotosInspecao){
        ContentValues values = new ContentValues();
        values.put("idInspecao", fotosInspecao.getIdInspecao());
        values.put("caminhoFotoPainel", fotosInspecao.getCaminhoFotoPainel());
        values.put("caminhoFotoFrente", fotosInspecao.getCaminhoFotoFrente());
        values.put("caminhoFotoLado", fotosInspecao.getCaminhoFotoLado());
        return banco.insert("fotosInspecao", null, values);
    }
}
