package telas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.balbino.checkguincho.R;

public class FotosActivity extends AppCompatActivity {

    private TextView tvPainel, tvLateral, tvFrente;
    private ImageView ivPainel, ivLateral, ivFrente;
    private int iPainel = 0, iLateral = 0, iFrente = 0;
    private Button proximo;

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

    }
}
