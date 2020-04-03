package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.FigurasInspecao;
import sql.Conexao;

public class FigurasDao {

    private Conexao conexao;
    private SQLiteDatabase banco;
    private FigurasInspecao figurasInspecao;

    public FigurasDao(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
        banco = conexao.getReadableDatabase();
    }

    public FigurasInspecao recupera(){
        Cursor cursor = banco.rawQuery("SELECT * FROM figurasInspecao", null);
        cursor.moveToFirst();

        while (cursor.moveToNext()){
            figurasInspecao = new FigurasInspecao();
            figurasInspecao.setId(cursor.getInt(0));
            figurasInspecao.setIdInspecao(cursor.getInt(1));
            figurasInspecao.setCaminhoFigura(cursor.getString(2));
            figurasInspecao.setCaminhoAssinaturaRetira(cursor.getString(3));
            figurasInspecao.setCaminhoAssinaturaEntrega(cursor.getString(4));
        }
        return figurasInspecao;
    }

    public long inserir(FigurasInspecao figurasInspecao){
        ContentValues values = new ContentValues();
        values.put("idInspecao", figurasInspecao.getIdInspecao());
        values.put("caminhoFigura", figurasInspecao.getCaminhoFigura());
        values.put("caminhoAssinaturaRetira", figurasInspecao.getCaminhoAssinaturaRetira());
        values.put("caminhoAssinaturaEntrega", figurasInspecao.getCaminhoAssinaturaEntrega());

      return banco.insert("figurasInspecao", null, values);
    }

    public void atualizar(FigurasInspecao figurasInspecao){
        ContentValues values = new ContentValues();
        values.put("idInspecao", figurasInspecao.getIdInspecao());
        values.put("caminhoFigura", figurasInspecao.getCaminhoFigura());
        values.put("caminhoAssinaturaRetira", figurasInspecao.getCaminhoAssinaturaRetira());
        values.put("caminhoAssinaturaEntrega", figurasInspecao.getCaminhoAssinaturaEntrega());

        banco.update("figurasInspecao", values, "id = ?",
                new String[]{String.valueOf(figurasInspecao.getId())});
    }

}
