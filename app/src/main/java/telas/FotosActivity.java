package telas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.balbino.checkguincho.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FotosActivity extends AppCompatActivity {

    private TextView tvPainel, tvLateral, tvFrente;
    private ImageView ivPainel, ivLateral, ivFrente;
    private static final int iPainel = 1, iLateral = 2, iFrente = 3;
    private int verificaPainel, verificaLateral, verificaFrente;
    private Button proximo;
    private Toolbar toolbar;
    private Bitmap imagemPainel, imagemLateral, imagemFrente;
    private String currentPainelPath = null, currentLateralPath = null, currentFrentePath = null;
    private File file;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);

        validaCampo();
    }

    private void validaCampo() {
        tvPainel = (TextView) findViewById(R.id.tvPainel);
        tvLateral = (TextView) findViewById(R.id.tvLateral);
        tvFrente = (TextView) findViewById(R.id.tvFrente);

        ivPainel = (ImageView) findViewById(R.id.ivFtPainel);
        ivLateral = (ImageView) findViewById(R.id.ivFtLateral);
        ivFrente = (ImageView) findViewById(R.id.ivFtFrente);

        proximo = (Button) findViewById(R.id.btProximo);

        ivPainel.setImageResource(R.drawable.painel);
        ivLateral.setImageResource(R.drawable.lado);
        ivFrente.setImageResource(R.drawable.frente);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Fotos Veículo");
        setSupportActionBar(toolbar);

        tvPainel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto("o painel", currentPainelPath, iPainel);
            }
        });

        ivPainel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto("o painel", currentPainelPath, iPainel);
            }
        });

        tvLateral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto("a lateral", currentLateralPath, iLateral);
            }
        });

        ivLateral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto("a lateral", currentLateralPath, iLateral);
            }
        });

        tvFrente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto("a frente", currentFrentePath, iFrente);
            }
        });

        ivFrente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto("a frente", currentFrentePath, iFrente);
            }
        });

        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FotosActivity.this, AssinaturaRetiraActivity.class);
                startActivity(it);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_camera, menu);
        return true;
    }

    public void tirarFoto(String parteVeiculo, String path, int valor ){
        Toast.makeText(this, "Fotografe " + parteVeiculo + " para prosseguir", Toast.LENGTH_SHORT).show();
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(camera.resolveActivity(getPackageManager()) != null){
             file = null;

            try{
                file = getImageFile(parteVeiculo);
            } catch (IOException e){
                e.printStackTrace();
            }

            if(path != null) {
                 imageUri = FileProvider.getUriForFile(
                        this, "com.balbino.checkguincho.fileprovider", file);
                camera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
            startActivityForResult(camera, valor);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            try{
                switch ( requestCode ){
                    case iPainel:
                        currentPainelPath = String.valueOf(file);
                        imagemPainel = BitmapFactory.decodeFile(currentPainelPath);
                        ivPainel.setImageBitmap(imagemPainel);
                        break;
                    case iLateral:
                        currentLateralPath = String.valueOf(file);
                        imagemLateral = BitmapFactory.decodeFile(currentLateralPath);
                        ivLateral.setImageBitmap(imagemLateral);
                        break;
                    case iFrente:
                        currentFrentePath = String.valueOf(file);
                        imagemFrente = BitmapFactory.decodeFile(currentFrentePath);
                        ivFrente.setImageBitmap(imagemFrente);
                        break;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private File getImageFile(String parteVeiculo) throws IOException{
        file = new File(Environment.getExternalStorageDirectory() + "/CheckGuincho" + "/Imagens/" + parteVeiculo + System.currentTimeMillis()  +".jpg");
        if(file.exists()) file.mkdir();
       /* FileOutputStream stream = new FileOutputStream(file);*/
        return file;
    }
}