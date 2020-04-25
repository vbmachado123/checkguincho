package telas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
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
                mydir.mkdirs();

                try{
                    file.createNewFile();
                    out = new FileOutputStream(file);
                    b.compress(Bitmap.CompressFormat.JPEG, 30, out);
                    out.flush();
                    out.close();
                    Toast.makeText(AssinaturaPrestadorActivity.this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) { //No such file or directory
                   /* Espaço interno insuficiente
                    * Erro genérico ao gerar pasta
                    * Salvar no armazenamento externo */
                    e.printStackTrace();
                    createFile(String.valueOf(mydir));
                } catch (Exception e){
                    e.printStackTrace();
                }

                UsuarioDao dao = new UsuarioDao(AssinaturaPrestadorActivity.this);
                Usuario usuario = new UsuarioDao(AssinaturaPrestadorActivity.this).recupera();
                if(usuario != null){
                    usuario.setCaminhoAssinatura(String.valueOf(file));
                    dao.atualizar(usuario);
                } else dao.inserir(usuario);

                Intent it = new Intent(AssinaturaPrestadorActivity.this, ConfiguracaoActivity.class);
                startActivity(it);
            }
        });
    }

    public static void createFile(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            } else {
                FileOutputStream writer = new FileOutputStream(path);
                writer.write(("").getBytes());
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createTemporaryFile(Context context) {

        String nomePasta = "/CheckGuincho/Imagens";
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();

        try {
            String fileName = "temporario";
            String coupons = "Get upto 50% off shoes @ xyx shop \n Get upto 80% off on shirts @ yuu shop";

            File file = File.createTempFile(fileName, null, new File(root, nomePasta + fileName));

            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(coupons.getBytes());
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
        }
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