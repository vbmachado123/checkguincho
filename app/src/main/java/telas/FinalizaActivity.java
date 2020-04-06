package telas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.balbino.checkguincho.R;

import java.util.Locale;

import model.MoneyTextWatcher;

public class FinalizaActivity extends AppCompatActivity {

    private EditText valorInspecao, observacao;
    private ImageView ivLogo;
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

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Finalizar Atendimento");
        setSupportActionBar(toolbar);

        Locale mLocale = new Locale("pt", "BR");

        valorInspecao.addTextChangedListener(new MoneyTextWatcher(valorInspecao, mLocale));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        return true;
    }
}
