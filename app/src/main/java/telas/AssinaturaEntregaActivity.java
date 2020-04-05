package telas;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.balbino.checkguincho.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import model.Desenho;
import model.Dialog;

public class AssinaturaEntregaActivity extends AppCompatActivity implements Dialog.RecuperaTextoListener {

    private Button proximo;
    private Desenho desenho;

    private String nomeDialog, rgDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assinatura_entrega);

        abrirDialog();
        
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
                try{
                    String nome = "_" + nomeDialog + "_" + rgDialog + "_.jpg";
                    out = new FileOutputStream(Environment.getExternalStorageDirectory() + "/CheckGuincho/Imagens/" + nome);
                    b.compress(Bitmap.CompressFormat.JPEG, 1, out);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
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
