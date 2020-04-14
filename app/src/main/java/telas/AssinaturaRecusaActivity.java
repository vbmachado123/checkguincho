package telas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
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

import dao.InspecaoDao;
import model.Desenho;
import model.Inspecao;

public class AssinaturaRecusaActivity extends AppCompatActivity {

    private Button proximo;
    private Desenho desenho;
    private String nome;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assinatura_recusa);

        proximo = (Button) findViewById(R.id.btSalvarAssinatura);

        Inspecao i = new InspecaoDao(this).recupera();
        Log.i("Telefone: ", i.getTelefone());

        exibeDialog();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Assinatura Recusa");
        setSupportActionBar(toolbar);

        final RelativeLayout parent = (RelativeLayout) findViewById(R.id.rlAssinaRecusa);
        desenho = new Desenho(this, 0xFF000000, 3);
        parent.addView(desenho);

        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.setDrawingCacheEnabled(true);
                Bitmap b = parent.getDrawingCache();

                FileOutputStream out = null;
                File mydir = new File(Environment.getExternalStorageDirectory() + "/CheckGuincho");
                if(mydir.exists()) mydir.mkdir();
                nome = mydir + "/Imagens/" + "_AssinaturaRecusa_" + System.currentTimeMillis() + ".jpg";

                try{
                    out = new FileOutputStream(nome);
                    b.compress(Bitmap.CompressFormat.JPEG, 30, out);
                    out.flush();
                    out.close();
                    Toast.makeText(AssinaturaRecusaActivity.this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }

                InspecaoDao dao = new InspecaoDao(AssinaturaRecusaActivity.this);
                Inspecao i = dao.recupera();
                i.setCaminhoAssinaturaRecusa(nome);

                dao.atualizar(i);

                Intent it = new Intent(AssinaturaRecusaActivity.this, FinalizaActivity.class);
                startActivity(it);
            }
        });

    }

    private void exibeDialog() {
    String mensagem = "Eu, proprietário do veículo rebocado, " +
        "considero desnecessária a inspeção dos quadros acima, estou ciente de todas as avarias e materiais existentes ou faltantes em meu veículo, " +
        "já que acompanharei o trajeto do reboque. Portanto ASSINO dispensando as verificações:";

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Recusa")
                .setMessage(mensagem)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent it = new Intent(AssinaturaRecusaActivity.this, FormularioActivity.class);
                        startActivity(it);
                    }
                }).setPositiveButton("OK", null)
                .setCancelable(false)
                .create();
        dialog.show();
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
