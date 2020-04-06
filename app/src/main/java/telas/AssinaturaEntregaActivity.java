package telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.balbino.checkguincho.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import dao.FigurasDao;
import model.Desenho;
import model.Dialog;
import model.FigurasInspecao;

public class AssinaturaEntregaActivity extends AppCompatActivity implements Dialog.RecuperaTextoListener {

    private Button proximo;
    private Desenho desenho;

    private FigurasInspecao figuras;
    private FigurasDao dao;

    private String nomeDialog, rgDialog, caminho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assinatura_entrega);

        abrirDialog();

        figuras = new FigurasDao(this).recupera();

        proximo = (Button) findViewById(R.id.btSalvarAssinatura);

        final RelativeLayout parent = (RelativeLayout) findViewById(R.id.rlAssinaEntrega);
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

                try{
                    caminho = mydir + "/Imagens/" + "_" + nomeDialog + "_" + rgDialog + "_" + ".jpg";
                    out = new FileOutputStream(caminho);
                    b.compress(Bitmap.CompressFormat.JPEG, 60, out);
                    out.flush();
                    out.close();
                    Toast.makeText(AssinaturaEntregaActivity.this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }

                dao = new FigurasDao(AssinaturaEntregaActivity.this);

                figuras.setCaminhoAssinaturaEntrega(caminho);
                dao.atualizar(figuras);

                Intent it = new Intent(AssinaturaEntregaActivity.this, FinalizaActivity.class);
                startActivity(it);
            }
        });
    }

    private void abrirDialog() {
        Dialog dialog = new Dialog();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void recuperaTexto(String nome, String rg) {
        /* PARA SALVAR OS DADOS NO NOME DA IMAGEM */
        nomeDialog = nome;
        rgDialog = rg;
    }
}
