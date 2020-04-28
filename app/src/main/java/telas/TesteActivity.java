package telas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.balbino.checkguincho.R;

import dao.UsuarioDao;
import model.Usuario;

/* ACTIVITY CRIADA PARA REALIZAR TESTES DE INTERAÇÕES COM O BANCO E AFINS
* NAS CONDIÇÕES IDEAIS OU NÃO
* 27/04/20 TESTES NA TABELA DE USUÁRIO -> ERRO AO INSERIR E RECUPERAR - [RESOLVIDO]*/
public class TesteActivity extends AppCompatActivity {

    private Button btTesteInsere, btTesteRecupera;
    private Usuario usuario;
    private UsuarioDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste);

        usuario = new Usuario();
        dao = new UsuarioDao(this);
        usuario.setEmail("Teste email");
        usuario.setNomeEmpresa("Teste nome empresa");
        usuario.setTelefoneMotorista("Teste telefone motorista");
        usuario.setSenha("Teste senha");
        usuario.setCnpjEmpresa("Teste CNPJ");
        usuario.setCaminhoAssinatura("Teste caminho assinatura");
        usuario.setCaminhoImagemLogo("Teste caminho logo");
        usuario.setNomeMorotista("Teste nome motorista");
        usuario.setRgMotorista("Teste rg motorista");

        btTesteInsere = (Button) findViewById(R.id.btTesteInsere);
        btTesteRecupera = (Button) findViewById(R.id.btTesteRecupera);

        btTesteInsere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long id = dao.inserir(usuario);
                Toast.makeText(TesteActivity.this, String.valueOf(id), Toast.LENGTH_SHORT).show();

            }
        });

        btTesteRecupera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Usuario u = new UsuarioDao(TesteActivity.this).getById(1);
                Usuario u = new UsuarioDao(TesteActivity.this).recupera();

                Log.i("Usuario: ", u.getEmail());
                Log.i("Usuario: ", u.getNomeEmpresa());
                Log.i("Usuario: ", u.getTelefoneMotorista());
                Log.i("Usuario: ", u.getCnpjEmpresa());
                Log.i("Usuario: ", u.getCaminhoAssinatura());
                Log.i("Usuario: ", u.getCaminhoImagemLogo());
                Log.i("Usuario: ", u.getNomeMorotista());
                Log.i("Usuario: ", u.getRgMotorista());
            }
        });


    }
}
