package telas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balbino.checkguincho.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import dao.UsuarioDao;
import model.Usuario;
import util.ConfiguracaoFirebase;
import util.Preferencias;

public class CadastroActivity extends AppCompatActivity {

    private ImageView imagemLogo;
    private TextView adicionaLogo, voltarLogin;
    private EditText nomeEmpresa, telefone, email, senha;
    private Button botaoCadastrar;
    private Uri filePath;
    private Usuario usuario;
    private UsuarioDao dao;
    private long id;

    private static final int foto = 1000;
    private Bitmap bitmap;

    /* FIREBASE */
    private FirebaseAuth autenticacao;
    private StorageReference reference, imgRef;
    private FirebaseUser usuarioFirebase;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        validaInformacoes();
        mascaraCampo();
    }

    private void mascaraCampo() {
        SimpleMaskFormatter smftel = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher mtwTel = new MaskTextWatcher(telefone, smftel);
        telefone.addTextChangedListener(mtwTel);
    }

    private void validaInformacoes() {
        imagemLogo = (ImageView) findViewById(R.id.ivLogo);
        adicionaLogo = (TextView) findViewById(R.id.tvLogo);
        voltarLogin = (TextView) findViewById(R.id.tvCadastrado);
        nomeEmpresa = (EditText) findViewById(R.id.etNomeEmpresa);
        telefone = (EditText) findViewById(R.id.etTelefone);
        email = (EditText) findViewById(R.id.etEmail);
        senha = (EditText) findViewById(R.id.etSenha);
        botaoCadastrar = (Button) findViewById(R.id.btCadastro);
        voltarLogin = (TextView) findViewById(R.id.tvCadastrado);

        imagemLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escolheFoto();
            }
        });
        adicionaLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                escolheFoto();
            }
        });

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean campo = validaCampoVazio();
                if( campo ) {
                    usuario = new Usuario();
                    dao = new UsuarioDao(CadastroActivity.this);

                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());
                    usuario.setNomeEmpresa(nomeEmpresa.getText().toString());
                    usuario.setTelefoneMotorista(telefone.getText().toString());

                    id = dao.inserir(usuario);
                    Log.i("ID", String.valueOf(id));
                    cadastrarUsuario();
                }
            }
        });

        voltarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acessaActivity(LoginActivity.class);
            }
        });
    }

    private void cadastrarUsuario() {
            /* FAZENDO A VERIFICAÇÃO SE A LOGO FOI ESCOLHIDA */

            autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            autenticacao.createUserWithEmailAndPassword(
                    usuario.getEmail(),
                    usuario.getSenha()
            ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        usuarioFirebase = task.getResult().getUser();
                        String identificador = usuario.salvar();

                        Preferencias preferencias = new Preferencias(CadastroActivity.this);
                        preferencias.salvarDados(identificador, usuario.getNomeEmpresa());

                    } else {
                        /*Tratando os erros ao logar o usuário*/
                        String erroExcecao = "";
                        try {
                            throw task.getException();
                        } catch (FirebaseAuthWeakPasswordException e) {
                            erroExcecao = "Digite uma senha mais forte, contendo mais caracteres e com letras e números";
                        } catch (FirebaseAuthInvalidCredentialsException e) {
                            erroExcecao = "O e-mail digitado é inválido, digite um novo e-mail!";
                        } catch (FirebaseNetworkException e){
                            erroExcecao = "Sem acesso a internet";
                        } catch (FirebaseAuthUserCollisionException e) {
                            erroExcecao = "Este e-mail já está sendo usado!";
                        } catch (Exception e) {
                            erroExcecao = "Erro ao efetuar o cadastro!";
                            e.printStackTrace();
                        }
                        Toast.makeText(CadastroActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                    }
                }
            });

         if( !(filePath == null) ) {
             usuario.setCaminhoImagemLogo(filePath.toString());
             subirImagem();
         }
       acessaActivity(HomeActivity.class);
    }

    private void subirImagem() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        reference = ConfiguracaoFirebase.getFirebaseStorage();

        imgRef = reference
                .child(usuario.getNomeEmpresa())
                .child("logo.png");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
        byte[] dadosImagem = baos.toByteArray();

        UploadTask uploadTask = imgRef.putBytes(dadosImagem);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CadastroActivity.this, "Erro ao fazer upload da imagem",
                        Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Uri url = taskSnapshot.getDownloadUrl();
                if(ConfiguracaoFirebase.atualizarFotoUsuario(url)){
                    Log.i("Foto", "Sucesso ao atualizar imagem");
                }

                Toast.makeText(CadastroActivity.this, "Sucesso ao fazer upload da imagem",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validaCampoVazio(){
        boolean campo = true;
        if(nomeEmpresa.getText().length() <= 0){
            nomeEmpresa.setError("Campo vazio!");
            campo = false;
        } else if(telefone.getText().length() <= 0){
            telefone.setError("Campo vazio!");
            campo = false;
        } else if(email.getText().length() <= 0){
            email.setError("Campo vazio!");
            campo = false;
        } else if(senha.getText().length() <= 0){
            senha.setError("Campo vazio!");
            campo = false;
        }
            return campo;
    }

    private void acessaActivity(Class c) {
        Intent it = new Intent(CadastroActivity.this, c);
        startActivity(it);
    }

    private void escolheFoto() {
        Toast.makeText(this, "Escolha uma foto!", Toast.LENGTH_SHORT).show();

        Intent it = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if( it.resolveActivity(getPackageManager()) != null ){
                it.setType("image/*");
                startActivityForResult(it, foto);
            }
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
