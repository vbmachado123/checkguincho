package telas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.balbino.checkguincho.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;

import dao.ChecklistDao;
import dao.InspecaoDao;
import dao.PdfDao;
import model.Checklist;
import model.Inspecao;
import model.MoneyTextWatcher;
import model.Pdf;
import util.ConfiguracaoFirebase;
import util.GeraPDF;

public class FinalizaActivity extends AppCompatActivity {

    private EditText valorInspecao, observacao;
    private ImageView imagemLogo;
    private Button btFinaliza;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finaliza);
        validaCampo();
    }

    private void validaCampo() {
        valorInspecao = (EditText) findViewById(R.id.etValor);
        observacao = (EditText) findViewById(R.id.etObsercavao);
        btFinaliza = (Button) findViewById(R.id.btFinaliza);
        imagemLogo = (ImageView) findViewById(R.id.ivLogo);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Finalizar Atendimento");
        setSupportActionBar(toolbar);

        //Recuperar dados do usuario
        FirebaseUser user = ConfiguracaoFirebase.getUsuarioAtual();
        Uri url = user.getPhotoUrl();
        if(url != null){
            Glide.with(this)
                    .load(url)
                    .into(imagemLogo);
        } else{
            imagemLogo.setImageResource(R.drawable.logo);
        }

        Locale mLocale = new Locale("pt", "BR");

        valorInspecao.addTextChangedListener(new MoneyTextWatcher(valorInspecao, mLocale));

        btFinaliza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Document documento;
                String root = Environment.getExternalStorageDirectory().getAbsolutePath();
                String nomePasta = "/CheckGuincho";
                Inspecao i = new InspecaoDao(FinalizaActivity.this).recupera();
                String nomeArquivo = i.getNomeProprietario() + ".pdf";
                File mydir = new File(root, nomePasta);
                File arquivo = new File(mydir, nomeArquivo);
                GeraPDF pdf = new GeraPDF();

                String obs = "";
                if(observacao.getText().toString() != null) obs = observacao.getText().toString();

                try {
                    documento = pdf.GerarPDF(String.valueOf(arquivo), obs, FinalizaActivity.this);
                } catch (FileNotFoundException e){

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(arquivo.canRead()){
                    String caminhoDocumento = root + nomePasta + "/" + nomeArquivo;

                    Pdf pdfModel = new Pdf();
                    pdfModel.setCaminhoDocumento(caminhoDocumento);
                    pdfModel.setIdInspecao(i.getId());
                    PdfDao dao = new PdfDao(FinalizaActivity.this);
                    dao.inserir(pdfModel);

                    Intent it = new Intent(FinalizaActivity.this, ExibePDFActivity.class);
                    it.putExtra("documento", pdfModel.getCaminhoDocumento());

                    startActivity(it);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }
}
