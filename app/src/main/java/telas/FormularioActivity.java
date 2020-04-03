package telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.balbino.checkguincho.R;

public class FormularioActivity extends AppCompatActivity {

    private Button proximo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        validaCampo();
    }

    private void validaCampo() {
        proximo = (Button) findViewById(R.id.btProximo);
        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(FormularioActivity.this, CheckListActivity.class);
                startActivity(it);
            }
        });
    }
}
