package telas;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.balbino.checkguincho.R;

import model.Desenho;

public class AssinaturaPrestadorActivity extends AppCompatActivity {

    private Button proximo;
    private Desenho desenho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assinatura_prestador);

        proximo = (Button) findViewById(R.id.btSalvarAssinatura);

        final RelativeLayout parent = (RelativeLayout) findViewById(R.id.rlAssinaPrestador);
        desenho = new Desenho(this, 0xFF000000);
        parent.addView(desenho);

        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.setDrawingCacheEnabled(true);
                Bitmap b = parent.getDrawingCache();
            }
        });
    }
}
