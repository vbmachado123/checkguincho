package telas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.balbino.checkguincho.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class ExibePDFActivity extends AppCompatActivity {

    private PDFView view;
    private FloatingActionButton fabEnviar;
    private File file;
    private Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibe_p_d_f);

        //Recuperando o caminho do documento
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        fabEnviar = (FloatingActionButton) findViewById(R.id.fabEnviar);

        view = (PDFView) findViewById(R.id.pdfview);

            file = new File(extras.getString("documento"));

            view.fromFile(file)
                .password(null)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .load();

    fabEnviar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            enviarArquivo();
        }
    });
    }

    private void enviarArquivo() {
        Intent enviar = new Intent();
     /*   if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            *//* NECESSÁRIO SOLICITAÇÃO ESPECIAL *//*
            uri = FileProvider.getUriForFile(
                    this, "com.balbino.checkguincho.fileprovider", file);
            enviar.putExtra(Intent.EXTRA_STREAM, uri);
            enviar.setType("application/pdf");
            startActivity(Intent.createChooser(enviar, "Enviar documento via..."));
        }*/
            uri = Uri.fromFile(file);
            enviar.setAction(Intent.ACTION_SEND);
            enviar.putExtra(Intent.EXTRA_STREAM, uri);
            enviar.setType("application/pdf");
            startActivity(Intent.createChooser(enviar, "Enviar documento via..."));

    }
}
