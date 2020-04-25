package telas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.balbino.checkguincho.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

import static androidx.core.content.FileProvider.getUriForFile;

public class ExibePDFActivity extends AppCompatActivity {

    private PDFView view;
    private FloatingActionButton fabEnviar;
    private File file;
    private Uri uri;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibe_p_d_f);

        //Recuperando o caminho do documento
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Documento Inspeção");
        setSupportActionBar(toolbar);

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
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                enviarArquivoM();
            else
                enviarArquivo();

        }
    });
    }

    private void enviarArquivoM() {
        Intent enviar = new Intent();
            uri = getUriForFile(
                    this, "com.balbino.checkguincho.fileprovider", file);
            enviar.setAction(Intent.ACTION_SEND);
            enviar.putExtra(Intent.EXTRA_STREAM, uri);
            enviar.setType("application/pdf");
            startActivity(Intent.createChooser(enviar, "Enviar documento via..."));
    }

    private void enviarArquivo() {
            Intent enviar = new Intent();
            uri = Uri.fromFile(file);
            if(uri != null){
                enviar.setAction(Intent.ACTION_SEND);
                enviar.putExtra(Intent.EXTRA_STREAM, uri);
                enviar.setType("application/pdf");
                startActivity(Intent.createChooser(enviar, "Enviar documento via..."));
            } else Toast.makeText(this, "Não foi possível compartilhar o arquivo", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pdf, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_salvar:
                Toast.makeText(this, "Documento salvo em: " + file, Toast.LENGTH_SHORT).show();
                acessaActivity(HomeActivity.class);
                return true;
            case R.id.item_home:
                acessaActivity(HomeActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void acessaActivity(Class c) {
        Intent it = new Intent(ExibePDFActivity.this, c);
        startActivity(it);
    }
}
