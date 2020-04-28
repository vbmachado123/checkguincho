package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.Pdf;
import sql.Conexao;

public class PdfDao {

    private Conexao conexao;
    private SQLiteDatabase banco;
    private Pdf pdf;

    public PdfDao(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
        banco = conexao.getReadableDatabase();
    }

    public Pdf getById(int id){
        Cursor cursor = banco.rawQuery("SELECT * FROM pdf WHERE idInspecao = " + id, null);

        if(cursor.moveToFirst()){
            pdf = new Pdf();
            pdf.setCaminhoDocumento(cursor.getString(2));
        }
        return pdf;
    }

    public Pdf recupera(){
        Cursor cursor = banco.rawQuery("SELECT * FROM pdf", null);
       // cursor.moveToFirst();

        while (cursor.moveToNext()){
         pdf = new Pdf();
         pdf.setId(cursor.getInt(0));
         pdf.setIdInspecao(cursor.getInt(1));
         pdf.setCaminhoDocumento(cursor.getString(2));
        }
        return pdf;
    }

    public long inserir(Pdf pdf){
        ContentValues values = new ContentValues();
        values.put("idInspecao", pdf.getIdInspecao());
        values.put("caminhoDocumento", pdf.getCaminhoDocumento());
        return banco.insert("pdf", null, values);
    }
}
