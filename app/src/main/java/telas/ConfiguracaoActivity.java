package telas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.GenericLifecycleObserver;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.util.Log;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.balbino.checkguincho.R;
import com.bumptech.glide.Glide;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private FirebaseUser user;

    private Uri filePath;
    private static final int foto = 1000;
    private Bitmap bitmap;
    private String identificadorUsuario;

    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Configurações");
        setSupportActionBar(toolbar);

        usuario = new Usuario();
        dao = new UsuarioDao(context);
        validaCampo();

        /* CONFIGURAÇÕES INICIAIS */
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        identificadorUsuario = autenticacao.getCurrentUser().getEmail();

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

        user = ConfiguracaoFirebase.getUsuarioAtual();

            try{
                Uri url = user.getPhotoUrl();
                if(url != null){
                    Glide.with(this)
                            .load(url)
                            .into(imagemLogo);
                } else{
                    imagemLogo.setImageResource(R.drawable.logo);
                }

            } catch (Exception e){
                e.printStackTrace();
            }

        if(usuario != null){
            nomeEmpresa.setText(usuario.getNomeEmpresa());
            cnpjEmpresa.setText(usuario.getCnpjEmpresa());
            nomeMotorista.setText(usuario.getNomeMorotista());
            rgMotorista.setText(usuario.getRgMotorista());
            telefoneMotorista.setText(usuario.getTelefoneMotorista());
        }

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

        AlertDialog dialog = new AlertDialog.Builder(this, R.style.DialogStyle)
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
                        if( it.resolveActivity(getPackageManager()) != null ){
                            it.setType("image/*");
                     /*       it.setAction(Intent.ACTION_GET_CONTENT);*/
                            startActivityForResult(it,foto);
                        }
                    }
                }).create();
        dialog.show();
    }

    private void alterarAssinatura() {
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.DialogStyle)
                .setTitle("Atenção")
                .setMessage("Realmente deseja alterar a assinatura?")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
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
        Usuario usuario = new Usuario();
        UsuarioDao dao = new UsuarioDao(context);

        usuario.setNomeEmpresa(nomeEmpresa.getText().toString());
        usuario.setCnpjEmpresa(cnpjEmpresa.getText().toString());
        usuario.setNomeMorotista(nomeMotorista.getText().toString());
        usuario.setRgMotorista(rgMotorista.getText().toString());
        usuario.setTelefoneMotorista(telefoneMotorista.getText().toString());

        dao.inserir(usuario);
    }

    private void confirmaSaida() {

        AlertDialog dialog = new AlertDialog.Builder(this, R.style.DialogStyle)
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
                        UsuarioDao dao = new UsuarioDao(ConfiguracaoActivity.this);
                        Usuario usuario1 = dao.recupera();

                        if(usuario1 != null){
                            if(usuario1.getEmail().equals("")){
                                identificadorUsuario = String.valueOf(ConfiguracaoFirebase.getUsuarioAtual().getEmail());
                                usuario1.setEmail(identificadorUsuario);
                            }
                            usuario1.setNomeEmpresa(nomeEmpresa.getText().toString());
                            usuario1.setCnpjEmpresa(cnpjEmpresa.getText().toString());
                            usuario1.setNomeMorotista(nomeMotorista.getText().toString());
                            usuario1.setRgMotorista(rgMotorista.getText().toString());
                            usuario1.setTelefoneMotorista(telefoneMotorista.getText().toString());
                            dao.atualizar(usuario1);
                        } else criarUsuario();

                        String identificador = Base64Custom.codificarBase64(usuario.getEmail());

                        reference.child("usuarios").child(identificador).setValue(usuario);

                        if( filePath != null) subirImagem(); /* LOGO FOI ALTERADA */
                        else acessaActivity(HomeActivity.class);
                    }
                }).create();
        dialog.show();
    }

    private void subirImagem() {

        if(filePath != null){
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Salvando imagem...");
            progressDialog.show();

            final StorageReference ref = storageReference
                    .child(usuario.getNomeEmpresa()).child("logo");

            ref.putFile(filePath)
                    .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                             progressDialog.dismiss();
                             Toast.makeText(ConfiguracaoActivity.this, "Imagem salva com sucesso", Toast.LENGTH_SHORT).show();
                             acessaActivity(HomeActivity.class);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ConfiguracaoActivity.this, "Não foi possível salvar imagem, tente novamente!", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage(" Salvando " + (int)progress + "%");
                        }
                    })
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                       Uri url = taskSnapshot.getDownloadUrl();
                            if(ConfiguracaoFirebase.atualizarFotoUsuario(url)){
                                Log.i("Foto", "Sucesso ao atualizar imagem");
                            }
                        }
                    });
        }
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