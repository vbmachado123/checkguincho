package telas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

import dao.ChecklistDao;
import dao.FigurasDao;
import dao.InspecaoDao;
import model.Checklist;
import model.Desenho;
import model.FigurasInspecao;
import model.Inspecao;

public class FiguraActivity extends AppCompatActivity {

    private Button proximo;
    private Desenho desenho;
    private String nome;
    private Inspecao inspecao;
    private FigurasInspecao figuras;
    private FigurasDao dao;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_figura);

        inspecao = new InspecaoDao(this).recupera();
        figuras = new FigurasInspecao();
        dao = new FigurasDao(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Figuras Veículo");
        setSupportActionBar(toolbar);

        proximo = (Button) findViewById(R.id.btSalvarDesenho);

        final RelativeLayout parent = (RelativeLayout) findViewById(R.id.rlFigura);
        desenho = new Desenho(this, 0xFF802415, 6);
        parent.addView(desenho);

        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.setDrawingCacheEnabled(true);
                Bitmap b = parent.getDrawingCache();

                FileOutputStream out = null;
                File mydir = new File(Environment.getExternalStorageDirectory() + "/CheckGuincho");
                if(mydir.exists()) mydir.mkdir();

                try{
                    nome = mydir + "/Imagens/" + "FiguraVeiculo" + System.currentTimeMillis() + ".jpg";
                    out = new FileOutputStream(nome);
                    b.compress(Bitmap.CompressFormat.JPEG, 30, out);
                    out.flush();
                    out.close();
                    Toast.makeText(FiguraActivity.this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }

                figuras.setIdInspecao(inspecao.getId());
                figuras.setCaminhoFigura(nome);
                dao.inserir(figuras);

                Intent it = new Intent(FiguraActivity.this, FotosActivity.class);
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
        Toast.makeText(this, "Não é possivel sair agora", Toast.LENGTH_SHORT).show();
    }
}