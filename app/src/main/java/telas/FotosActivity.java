package telas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.balbino.checkguincho.R;

public class FotosActivity extends AppCompatActivity {

    private TextView tvPainel, tvLateral, tvFrente;
    private ImageView ivPainel, ivLateral, ivFrente;
    private int iPainel = 0, iLateral = 0, iFrente = 0;
    private Button proximo;
    private Toolbar toolbar;

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
}