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
                acessaActivity(AtendimentoActivity.class);
                //acessaActivity(AssinaturaPrestadorActivity.class);
            }
        });

        configuracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acessaActivity(ConfiguracaoActivity.class);
            }
        });

    }

    private void acessaActivity(Class c) {
        Intent it = new Intent(HomeActivity.this, c);
        startActivity(it);
    }
}
