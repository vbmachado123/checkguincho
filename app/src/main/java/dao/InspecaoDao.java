package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Inspecao;
import sql.Conexao;

public class InspecaoDao {

    private Conexao conexao;
    private SQLiteDatabase banco;
    private Inspecao inspecao;

    public InspecaoDao(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
        banco = conexao.getReadableDatabase();
    }

    public Inspecao recupera(){
        Cursor cursor = banco.rawQuery("SELECT * FROM inspecao", null);
        cursor.moveToFirst();

        while (cursor.moveToNext()){
         inspecao = new Inspecao();
         inspecao.setId(cursor.getInt(0));
         inspecao.setIdMarca(cursor.getInt(1));
         inspecao.setIdModelo(cursor.getInt(2));
         inspecao.setIdCor(cursor.getInt(3));
         inspecao.setIdUsuario(cursor.getInt(4));
         inspecao.setNomeProprietario(cursor.getString(5));
         inspecao.setTelefone(cursor.getString(6));
         inspecao.setAno(cursor.getInt(7));
         inspecao.setPlaca(cursor.getString(8));
         inspecao.setInspecao(cursor.getInt(9));
         inspecao.setCaminhoAssinaturaRecusa(cursor.getString(10));
        }
        return inspecao;
    }

    public long insere(Inspecao inspecao){
        ContentValues values = new ContentValues();
        values.put("idMarca", inspecao.getIdMarca());
        values.put("idModelo", inspecao.getIdModelo());
        values.put("idCor", inspecao.getIdCor());
        values.put("idUsuario", inspecao.getIdUsuario());
        values.put("nomeProprietario", inspecao.getNomeProprietario());
        values.put("telefone", inspecao.getTelefone());
        values.put("ano", inspecao.getAno());
        values.put("placa", inspecao.getPlaca());
        values.put("inspecao", inspecao.getInspecao());
        values.put("caminhoAssinaturaRecusa", inspecao.getCaminhoAssinaturaRecusa());

        return banco.insert("inspecao", null, values);
    }

    public List<Inspecao> obterTodos(){
        List<Inspecao> inspecoes = new ArrayList<>();
        Cursor cursor = banco.query("inspecao", new String[]{"id", "idMarca", "idModelo", "idCor", "idUsuario",
           "nomeProprietario", "telefone", "ano", "placa", "inspecao", "caminhoAssinaturaRecusa"}, null, null, null, null, null);

        cursor.moveToFirst();

        while (cursor.moveToNext()){
            inspecao = new Inspecao();
            inspecao.setId(cursor.getInt(0));
            inspecao.setIdMarca(cursor.getInt(1));
            inspecao.setIdModelo(cursor.getInt(2));
            inspecao.setIdCor(cursor.getInt(3));
            inspecao.setIdUsuario(cursor.getInt(4));
            inspecao.setNomeProprietario(cursor.getString(5));
            inspecao.setAno(cursor.getInt(6));
            inspecao.setPlaca(cursor.getString(7));
            inspecao.setInspecao(cursor.getInt(8));
            inspecao.setCaminhoAssinaturaRecusa(cursor.getString(9));

            inspecoes.add(inspecao);
        }
        return inspecoes;
    }
}
