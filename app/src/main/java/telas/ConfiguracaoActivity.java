package telas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.balbino.checkguincho.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        validaCampo();
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

        final UsuarioDao dao = new UsuarioDao(this);
        usuario = dao.recupera();
        nomeEmpresa.setText(usuario.getNomeEmpresa());
        cnpjEmpresa.setText(usuario.getCnpjEmpresa());
        nomeMotorista.setText(usuario.getNomeMorotista());
        rgMotorista.setText(usuario.getRgMotorista());
        telefoneMotorista.setText(usuario.getTelefoneMotorista());

        fabSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setNomeEmpresa(nomeEmpresa.getText().toString());
                usuario.setCnpjEmpresa(cnpjEmpresa.getText().toString());
                usuario.setNomeMorotista(nomeMotorista.getText().toString());


                dao.atualizar(usuario);
            }
        });

    }
}
