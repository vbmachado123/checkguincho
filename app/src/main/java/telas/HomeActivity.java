package telas;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.balbino.checkguincho.R;

import util.Permissao;

public class HomeActivity extends AppCompatActivity {

    private Button atendimento, configuracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        atendimento = (Button) findViewById(R.id.btAtendimento);
        configuracao = (Button) findViewById(R.id.btConfiguracoes);

       Permissao permissao = new Permissao();
       permissao.Permissoes(this);

       configuracao.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               acessaActivity(ConfiguracaoActivity.class);
           }
       });
    }

    private void acessaActivity(Class c) {
        Intent it = new Intent(HomeActivity.this, ConfiguracaoActivity.class);
        startActivity(it);
    }
}
