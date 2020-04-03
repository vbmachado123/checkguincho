package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import model.Checklist;
import sql.Conexao;

public class ChecklistDao {
    private Conexao conexao;
    private SQLiteDatabase banco;
    private Checklist checklist;

    public ChecklistDao(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
        banco = conexao.getReadableDatabase();
    }

    public Checklist recupera(){
        Cursor cursor = banco.rawQuery("SELECT * FROM checklist", null);
        cursor.moveToFirst();
        while (cursor.moveToNext()){
            checklist = new Checklist();
            checklist.setId(cursor.getInt(0));
            checklist.setIdInspecao(cursor.getInt(1));
            checklist.setRadio(cursor.getString(2));
            checklist.setEstepe(cursor.getString(3));
            checklist.setMacaco(cursor.getString(4));
            checklist.setChaveRodas(cursor.getString(5));
            checklist.setTriangulo(cursor.getString(6));
            checklist.setExtintor(cursor.getString(7));
            checklist.setCalotas(cursor.getString(8));
            checklist.setTapetesBorracha(cursor.getString(9));
            checklist.setTapetesCarpete(cursor.getString(10));
            checklist.setRodaLiga(cursor.getString(11));
            checklist.setFaroisAux(cursor.getString(12));
            checklist.setBateria(cursor.getString(13));
            checklist.setRetrovisorEletrico(cursor.getString(14));
            checklist.setBagageiro(cursor.getString(15));
            checklist.setRack(cursor.getString(16));
            checklist.setProtetorCarter(cursor.getString(17));
            checklist.setDocumentos(cursor.getString(18));
            checklist.setChaves(cursor.getString(19));
            checklist.setMoto(cursor.getString(20));
        }
        return checklist;
    }

    public long inserir(Checklist checklist){
        ContentValues values = new ContentValues();
        values.put("idInspecao", checklist.getIdInspecao());
        values.put("radio", checklist.getRadio());
        values.put("estepe", checklist.getEstepe());
        values.put("macaco", checklist.getMacaco());
        values.put("chaveRodas", checklist.getChaveRodas());
        values.put("triangulo", checklist.getTriangulo());
        values.put("extintor", checklist.getExtintor());
        values.put("calotas", checklist.getCalotas());
        values.put("tapetesBorracha", checklist.getTapetesBorracha());
        values.put("tapetesCarpete", checklist.getTapetesCarpete());
        values.put("rodaLiga", checklist.getRodaLiga());
        values.put("faroisAux", checklist.getFaroisAux());
        values.put("bateria", checklist.getBateria());
        values.put("retrovisorEletrico", checklist.getRetrovisorEletrico());
        values.put("bagageiro", checklist.getBagageiro());
        values.put("rack", checklist.getRack());
        values.put("protetorCarter", checklist.getProtetorCarter());
        values.put("documentos", checklist.getDocumentos());
        values.put("chaves", checklist.getChaves());
        values.put("moto", checklist.getMoto());

        return banco.insert("checklist", null, values);
    }
}
