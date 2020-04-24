package telas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.balbino.checkguincho.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import model.Usuario;
import util.Base64Custom;
import util.ConfiguracaoFirebase;
import util.Permissao;
import util.Preferencias;

public class LoginActivity extends AppCompatActivity {

    private EditText email, senha;
    private Button login;
    private TextView cadastrar;

    private FirebaseAuth autenticacao;
    private Usuario usuario;
    private String identificadorUsuarioLogado;
    private ValueEventListener valueEventListenerUsuario;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        verificaUsuarioLogado();

        Permissao permissao = new Permissao();
        permissao.Permissoes(this);

        validaCampo();
    }

    private void verificaUsuarioLogado() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(autenticacao.getCurrentUser() != null){
            acessaActivity(HomeActivity.class);
        }
    }

    private void validaCampo() {

        email = (EditText) findViewById(R.id.etEmail);
        senha = (EditText) findViewById(R.id.etSenha);
        login = (Button) findViewById(R.id.btLogin);
        cadastrar = (TextView) findViewById(R.id.tvCadastrar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean campo = validaCampoVazio();
                
                   if(campo){
                       usuario = new Usuario();
                       usuario.setEmail(email.getText().toString());
                       usuario.setSenha(senha.getText().toString());
                       validarLogin();
                   }
            }
        });
        
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acessaActivity(CadastroActivity.class);
            }
        });
    }

    private void validarLogin() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    identificadorUsuarioLogado = Base64Custom.codificarBase64(usuario.getEmail());

                     reference = ConfiguracaoFirebase.getFirebaseDatabase()
                            .child("usuarios")
                            .child(identificadorUsuarioLogado);

                    valueEventListenerUsuario = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Usuario usuarioRecuperado = dataSnapshot.getValue(Usuario.class);

                            Preferencias preferencias = new Preferencias(LoginActivity.this);
                            preferencias.salvarDados(identificadorUsuarioLogado, usuarioRecuperado.getNomeEmpresa());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    };

                    reference.addListenerForSingleValueEvent(valueEventListenerUsuario);
                    acessaActivity(HomeActivity.class);
                    Toast.makeText(LoginActivity.this, "Sucesso ao fazer login!", Toast.LENGTH_LONG).show();
                }else {
                    /*Tratando os erros ao logar o usuário*/
                    String erroExcecao = "";

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthInvalidUserException e) {
                        erroExcecao = "E-mail inválido!";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "Senha incorreta!";
                    } catch (FirebaseNetworkException e){
                        erroExcecao = "Sem acesso a internet";
                    } catch (Exception e) {
                        erroExcecao = "Erro ao efetuar o login!";
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean validaCampoVazio() {
        boolean campo = true;
        if(email.getText().length() == 0){
            email.setError("Campo vazio!");
            campo = false;
        } else if(senha.getText().length() == 0) {
            senha.setError("Campo vazio!");
            campo = false;
        }
        return campo;
    }

    private void acessaActivity(Class c) {
        Intent it = new Intent(LoginActivity.this, c);
        startActivity(it);
        finish();
    }
}
