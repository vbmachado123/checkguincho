package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.FotosInspecao;
import model.Localizacao;
import sql.Conexao;

public class LocalizacaoDao {

    private Conexao conexao;
    private SQLiteDatabase banco;
    private Localizacao localizacao;

    public LocalizacaoDao(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
        banco = conexao.getReadableDatabase();
    }

    public Localizacao recupera(){
        Cursor cursor = banco.rawQuery("SELECT * FROM localizacao",null);
        cursor.moveToFirst();

        while (cursor.moveToNext()){
            localizacao = new Localizacao();
            localizacao.setId(cursor.getInt(0));
            localizacao.setIdTipoRegistro(cursor.getInt(1));
            localizacao.setIdInspecao(cursor.getInt(2));
            localizacao.setData(cursor.getString(3));
            localizacao.setLatitude(cursor.getString(4));
            localizacao.setLongitude(cursor.getString(5));
            localizacao.setEndereco(cursor.getString(6));
        }
        return localizacao;
    }

    public Localizacao getById(int id){
        Cursor cursor = banco.rawQuery("SELECT * FROM localizacao WHERE id =" + id, null);

        if(cursor.moveToFirst()){
            localizacao = new Localizacao();
            localizacao.setId(cursor.getInt(0));
            localizacao.setIdTipoRegistro(cursor.getInt(1));
            localizacao.setIdInspecao(cursor.getInt(2));
            localizacao.setData(cursor.getString(3));
            localizacao.setLatitude(cursor.getString(4));
            localizacao.setLongitude(cursor.getString(5));
            localizacao.setEndereco(cursor.getString(6));
        }
        return localizacao;
    }

    public long insere(Localizacao localizacao){
        ContentValues values = new ContentValues();

        values.put("idTipoRegistro", localizacao.getIdTipoRegistro());
        values.put("idInspecao", localizacao.getIdInspecao());
        values.put("data", localizacao.getData());
        values.put("latitude", localizacao.getLatitude());
        values.put("longitude", localizacao.getLongitude());
        values.put("endereco", localizacao.getEndereco());

     return banco.insert("localizacao", null, values);
    }

    public void atualizar(Localizacao localizacao){
        ContentValues values = new ContentValues();

        values.put("idTipoRegistro", localizacao.getIdTipoRegistro());
        values.put("idInspecao", localizacao.getIdInspecao());
        values.put("data", localizacao.getData());
        values.put("latitude", localizacao.getLatitude());
        values.put("longitude", localizacao.getLongitude());
        values.put("endereco", localizacao.getEndereco());

        banco.update("localizacao", values, "id = ?",
                new String[]{String.valueOf(localizacao.getId())});
    }


}
