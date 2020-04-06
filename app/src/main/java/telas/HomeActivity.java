package telas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.balbino.checkguincho.R;

import java.io.File;
import java.io.IOException;

import dao.UsuarioDao;
import model.Usuario;
import util.Permissao;

public class HomeActivity extends AppCompatActivity {

    private Button atendimento, configuracao;
    private ImageView imagemLogo;
    private String nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        validaCampo();

        Permissao permissao = new Permissao();
        permissao.Permissoes(this);
    }

    private void validaCampo() {

        atendimento = (Button) findViewById(R.id.btAtendimento);
        configuracao = (Button) findViewById(R.id.btConfiguracoes);
        imagemLogo = (ImageView) findViewById(R.id.ivLogo);

        Usuario usuario = new UsuarioDao(this).recupera();
        if(usuario == null) {
            Toast.makeText(this, "Complete o cadastro para prosseguir!", Toast.LENGTH_SHORT).show();
            acessaActivity(ConfiguracaoActivity.class);
        }

        atendimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assinaturaPrestador(AtendimentoActivity.class);
            }
        });

        configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               assinaturaPrestador(ConfiguracaoActivity.class);
            }
        });
    }

    public void assinaturaPrestador(Class c){
        boolean existe = true;
        File mydir = new File(Environment.getExternalStorageDirectory() + "/CheckGuincho");
        nome = mydir + "/Imagens/" + "AssinaturaPrestador" + ".jpg";
        File file = new File(nome);
        if( !(file.exists()) ) {
            existe = false;
            Toast.makeText(this, "Assine como prestador para prosseguir", Toast.LENGTH_SHORT).show();
            acessaActivity(AssinaturaPrestadorActivity.class);
        } else existe = true;

        if(existe) acessaActivity(c);

    }

    private void acessaActivity(Class c) {
        Intent it = new Intent(HomeActivity.this, c);
        startActivity(it);
    }
}
