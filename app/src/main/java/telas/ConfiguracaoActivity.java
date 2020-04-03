package telas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.balbino.checkguincho.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        validaCampo();
        mascaraCampo();
        dao = new UsuarioDao(this);
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

        fabSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmaSaida();
            }
        });
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

                        acessaActivity(HomeActivity.class);

                    }
                }).create();
        dialog.show();
    }
    public void acessaActivity(Class c){
        Intent it = new Intent(ConfiguracaoActivity.this, c);
        startActivity(it);
    }
}
