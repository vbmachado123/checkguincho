package telas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balbino.checkguincho.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

import dao.UsuarioDao;
import model.Usuario;
import util.Base64Custom;
import util.ConfiguracaoFirebase;

public class ConfiguracaoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText nomeEmpresa, cnpjEmpresa, nomeMotorista, rgMotorista, telefoneMotorista;
    private ImageView imagemLogo, imagemAssinatura;
    private TextView carregarLogo, assinatura;
    private FloatingActionButton fabSalvar;

    private Usuario usuario;
    private UsuarioDao dao;
    private String caminhoAssinatura;

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth autenticacao;

    private Uri filePath;
    private static final int foto = 1000;
    private Bitmap bitmap;

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

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
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
        carregarLogo = (TextView) findViewById(R.id.tvCarregarLogo);
        imagemLogo = (ImageView) findViewById(R.id.ivLogo);
        assinatura = (TextView) findViewById(R.id.tvAssinatura);
        imagemAssinatura = (ImageView) findViewById(R.id.ivAssinatura);
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

        assinatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterarAssinatura();
            }
        });

        imagemAssinatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterarAssinatura();
            }
        });

        carregarLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterarLogo();
            }
        });

        imagemLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alterarLogo();
            }
        });

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

    private void alterarLogo() {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja alterar a logo?")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* ALTERAR A LOGO */
                        Toast.makeText(ConfiguracaoActivity.this, "Escolha uma foto!", Toast.LENGTH_SHORT).show();

                        Intent it = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        if( it.resolveActivity(getPackageManager()) != null )
                            startActivityForResult(it, foto);
                    }
                }).create();
        dialog.show();
    }

    private void alterarAssinatura() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja alterar a assinatura?")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                })
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /* ALTERAR A ASSINATURA */
                        acessaActivity(AssinaturaPrestadorActivity.class);
                    }
                }).create();
        dialog.show();
    }

    private void criarUsuario() {
        usuario = new Usuario();
        usuario.setEmail(autenticacao.getCurrentUser().getEmail());
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

                        usuario.setEmail(autenticacao.getCurrentUser().getEmail());
                        usuario.setNomeEmpresa(nomeEmpresa.getText().toString());
                        usuario.setCnpjEmpresa(cnpjEmpresa.getText().toString());
                        usuario.setNomeMorotista(nomeMotorista.getText().toString());
                        usuario.setRgMotorista(rgMotorista.getText().toString());
                        usuario.setTelefoneMotorista(telefoneMotorista.getText().toString());

                        if(usuario1 != null) dao.atualizar(usuario);
                        else criarUsuario();

                        String identificador = Base64Custom.codificarBase64(usuario.getEmail());

                        reference.child("usuarios").child(identificador).setValue(usuario);

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
        switch (item.getItemId()){
            case R.id.itemConfiguracoes:
                acessaActivity(ConfiguracaoActivity.class);
                return true;
            case R.id.itemSair:
                deslogarUsuario();
                return true;
            case R.id.item_lista:
                acessaActivity(ListaInspecaoActivity.class);
                return true;
            case R.id.item_sincroniza:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void deslogarUsuario() {
        autenticacao.signOut();
        acessaActivity(LoginActivity.class);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == foto) {
            bitmap = null;

            try{
                filePath = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap( getContentResolver(), filePath );
                imagemLogo.setImageBitmap(bitmap);

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
