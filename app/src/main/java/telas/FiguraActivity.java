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

import model.Desenho;

public class FiguraActivity extends AppCompatActivity {

    private Button proximo;
    private Desenho desenho;
    private String nome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_figura);

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
                    b.compress(Bitmap.CompressFormat.JPEG, 60, out);
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

                Intent it = new Intent(FiguraActivity.this, FotosActivity.class);
                startActivity(it);
            }
        });
    }
}