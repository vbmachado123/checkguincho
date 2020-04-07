package telas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.balbino.checkguincho.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

import dao.UsuarioDao;
import model.Usuario;

public class ConfiguracaoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText nomeEmpresa, cnpjEmpresa, nomeMotorista, rgMotorista, telefoneMotorista;
    private ImageView imagemLogo, imagemAssinatura;
    private TextView carregarLogo, assinatura;
    private FloatingActionButton fabSalvar;

    private Usuario usuario;
    private UsuarioDao dao;
    private String caminhoAssinatura;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Configurações");
        setSupportActionBar(toolbar);

        validaCampo();
        mascaraCampo();
    }

    private void mascaraCampo() {
        SimpleMaskFormatter smftel = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher mtwTel = new MaskTextWatcher(telefoneMotorista, smftel);
        telefoneMotorista.addTextChangedListener(mtwTel);

        SimpleMaskFormatter smfCnpj = new SimpleMaskFormatter("NN.NNN.NNN/NNNN-NN");
        MaskTextWatcher mtwCnpj = new MaskTextWatcher(cnpjEmpresa, smfCnpj);
        cnpjEmpresa.addTextChangedListener(mtwCnpj);

        SimpleMaskFormatter smfRg = new SimpleMaskFormatter("NN.NNN.NNN");
        MaskTextWatcher mtwRg = new MaskTextWatcher(rgMotorista, smfRg);
        rgMotorista.addTextChangedListener(mtwRg);
    }

    private void validaCampo() {
        usuario = new Usuario();
        dao = new UsuarioDao(this);

        nomeEmpresa = (EditText) findViewById(R.id.etNomeEmpresa);
        cnpjEmpresa = (EditText) findViewById(R.id.etCnpjEmpresa);
        nomeMotorista = (EditText) findViewById(R.id.etNomeMotorista);
        rgMotorista = (EditText) findViewById(R.id.etRgMotorista);
        telefoneMotorista = (EditText) findViewById(R.id.etTelefoneMorista);
        imagemLogo = (ImageView) findViewById(R.id.ivLogo);
        imagemAssinatura = (ImageView) findViewById(R.id.ivAssinatura);
        carregarLogo = (TextView) findViewById(R.id.tvCarregarLogo);
        assinatura = (TextView) findViewById(R.id.tvAssinatura);
        fabSalvar = (FloatingActionButton) findViewById(R.id.fabSalvar);

        usuario = dao.recupera();

        if(usuario != null){
            nomeEmpresa.setText(usuario.getNomeEmpresa());
            cnpjEmpresa.setText(usuario.getCnpjEmpresa());
            nomeMotorista.setText(usuario.getNomeMorotista());
            rgMotorista.setText(usuario.getRgMotorista());
            telefoneMotorista.setText(usuario.getTelefoneMotorista());
        }
        
        /* TEMPORÁRIO */
        imagemLogo.setImageResource(R.drawable.logo);

        fabSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmaSaida();
            }
        });
        File mydir = new File(Environment.getExternalStorageDirectory() + "/CheckGuincho");
        caminhoAssinatura = mydir + "/Imagens/" + "AssinaturaPrestador" + ".jpg";
        File fileAssinatura = new File(caminhoAssinatura);
        if( fileAssinatura.exists() ){
            Bitmap assinatura = BitmapFactory.decodeFile(String.valueOf(fileAssinatura));
            imagemAssinatura.setImageBitmap(assinatura);
        }
    }

    private void criarUsuario() {
        usuario.setNomeEmpresa(nomeEmpresa.getText().toString());
        usuario.setCnpjEmpresa(cnpjEmpresa.getText().toString());
        usuario.setNomeMorotista(nomeMotorista.getText().toString());
        usuario.setRgMotorista(rgMotorista.getText().toString());
        usuario.setTelefoneMotorista(telefoneMotorista.getText().toString());
        dao.inserir(usuario);
    }

    private void confirmaSaida() {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja atualizar as informações?")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        acessaActivity(HomeActivity.class);

                    }
                })
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Usuario usuario1 = dao.recupera();

                        usuario.setNomeEmpresa(nomeEmpresa.getText().toString());
                        usuario.setCnpjEmpresa(cnpjEmpresa.getText().toString());
                        usuario.setNomeMorotista(nomeMotorista.getText().toString());
                        usuario.setRgMotorista(rgMotorista.getText().toString());
                        usuario.setTelefoneMotorista(telefoneMotorista.getText().toString());

                        if(usuario1 != null) dao.atualizar(usuario);
                        else dao.inserir(usuario);

                        usuario.salvar();

                        acessaActivity(HomeActivity.class);
                    }
                }).create();
        dialog.show();
    }
    public void acessaActivity(Class c){
        Intent it = new Intent(ConfiguracaoActivity.this, c);
        startActivity(it);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_configuracoes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
