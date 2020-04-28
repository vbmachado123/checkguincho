package dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import model.Usuario;
import sql.Conexao;

public class UsuarioDao {

    private Conexao conexao;
    private SQLiteDatabase banco;
    private Usuario usuario;

    public UsuarioDao(Context context){
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
        banco = conexao.getReadableDatabase();
    }

    public Usuario recupera(){

        Cursor cursor = banco.rawQuery("SELECT * FROM usuario", null);
       //cursor.moveToFirst();
        while (cursor.moveToNext()){
            usuario = new Usuario();
            usuario.setId(cursor.getInt(0));
            usuario.setEmail(cursor.getString(1));
            usuario.setNomeEmpresa(cursor.getString(3));
            usuario.setCnpjEmpresa(cursor.getString(4));
            usuario.setNomeMorotista(cursor.getString(5));
            usuario.setRgMotorista(cursor.getString(6));
            usuario.setTelefoneMotorista(cursor.getString(7));
            usuario.setCaminhoImagemLogo(cursor.getString(8));
            usuario.setCaminhoAssinatura(cursor.getString(9));
        }
        return usuario;
    }

    public Usuario getById(int id){
        Cursor cursor = banco.rawQuery("SELECT * FROM usuario WHERE id =" + id, null);

        if (cursor.moveToFirst()){
            usuario = new Usuario();
            usuario.setId(cursor.getInt(0));
            usuario.setEmail(cursor.getString(1));
            usuario.setNomeEmpresa(cursor.getString(3));
            usuario.setCnpjEmpresa(cursor.getString(4));
            usuario.setNomeMorotista(cursor.getString(5));
            usuario.setRgMotorista(cursor.getString(6));
            usuario.setTelefoneMotorista(cursor.getString(7));
            usuario.setCaminhoImagemLogo(cursor.getString(8));
            usuario.setCaminhoAssinatura(cursor.getString(9));
        }

        return usuario;
    }

    public long inserir(Usuario usuario){
        ContentValues values = new ContentValues();
        values.put("email", usuario.getEmail());
        values.put("senha", usuario.getSenha());
        values.put("nomeEmpresa", usuario.getNomeEmpresa());
        values.put("cnpjEmpresa", usuario.getCnpjEmpresa());
        values.put("nomeMotorista", usuario.getNomeMorotista());
        values.put("rgMotorista", usuario.getRgMotorista());
        values.put("telefoneMotorista", usuario.getTelefoneMotorista());
        values.put("caminhoLogo", usuario.getCaminhoImagemLogo());
        values.put("caminhoAssinatura", usuario.getCaminhoAssinatura());

        return banco.insert("usuario", null, values);
    }

    public void atualizar(Usuario usuario){
        ContentValues values = new ContentValues();
        values.put("email", usuario.getEmail());
        values.put("senha", usuario.getSenha());
        values.put("nomeEmpresa", usuario.getNomeEmpresa());
        values.put("cnpjEmpresa", usuario.getCnpjEmpresa());
        values.put("nomeMotorista", usuario.getNomeMorotista());
        values.put("rgMotorista", usuario.getRgMotorista());
        values.put("telefoneMotorista", usuario.getTelefoneMotorista());
        values.put("caminhoLogo", usuario.getCaminhoImagemLogo());
        values.put("caminhoAssinatura", usuario.getCaminhoAssinatura());

        banco.update("usuario", values, "id = ?",
                new String[]{String.valueOf(usuario.getId())});

        Log.i("Log: ", "Id: " + String.valueOf(usuario.getId()));
    }
}
