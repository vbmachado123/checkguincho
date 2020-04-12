package telas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.balbino.checkguincho.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import dao.UsuarioDao;
import model.Desenho;
import model.Usuario;

public class AssinaturaPrestadorActivity extends AppCompatActivity {

    private Button proximo;
    private Desenho desenho;

    private String nome;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assinatura_prestador);

        proximo = (Button) findViewById(R.id.btSalvarAssinatura);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Assinatura Prestador");
        setSupportActionBar(toolbar);

        final RelativeLayout parent = (RelativeLayout) findViewById(R.id.rlAssinaPrestador);
        desenho = new Desenho(this, 0xFF000000, 3);
        parent.addView(desenho);

        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.setDrawingCacheEnabled(true);
                Bitmap b = parent.getDrawingCache();

                FileOutputStream out = null;
                String nomePasta = "/CheckGuincho/Imagens";
                String root = Environment.getExternalStorageDirectory().getAbsolutePath();
                nome = "AssinaturaPrestador" + ".jpg";

                File mydir = new File(root, nomePasta);
                File file = new File(mydir, nome);

                if(mydir.exists()) mydir.mkdirs();

                try{
                    mydir.mkdirs();
                    mydir.createNewFile();
                    out = new FileOutputStream(file);
                    b.compress(Bitmap.CompressFormat.JPEG, 60, out);
                    out.flush();
                    out.close();
                    Toast.makeText(AssinaturaPrestadorActivity.this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }

                Usuario usuario = new UsuarioDao(AssinaturaPrestadorActivity.this).recupera();
                usuario.setCaminhoAssinatura(mydir + "/" + nome);
                UsuarioDao dao = new UsuarioDao(AssinaturaPrestadorActivity.this);
                dao.atualizar(usuario);

                Intent it = new Intent(AssinaturaPrestadorActivity.this, ConfiguracaoActivity.class);
                startActivity(it);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_limpar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemConfiguracoes:
                acessaActivity();
                return true;
            case R.id.itemSair:
                acessaActivity();
                return true;
            case R.id.item_delete:
                desenho.clear();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void acessaActivity() {
        Toast.makeText(this, "Não é possível sair agora", Toast.LENGTH_SHORT).show();
    }
}