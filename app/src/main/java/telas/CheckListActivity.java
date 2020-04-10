package telas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.balbino.checkguincho.R;

import dao.ChecklistDao;
import dao.InspecaoDao;
import model.Checklist;
import model.Inspecao;

public class CheckListActivity extends AppCompatActivity {

    private CheckBox radio, estepe, macaco, triangulo, extintor, calotas, documentos, chaveRodas;
    private CheckBox tapetesBorracha, tapetesCarpete, rodaLiga, faroisAux, chaves;
    private CheckBox bateria, retrovisorEletrico, bagageiro, rack, carter, moto;

    private Button proximo;

    private Checklist checklist;
    private ChecklistDao cDao;
    private Inspecao inspecao;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("CheckList Inspeção");
        setSupportActionBar(toolbar);

        validaCampo();
    }

    private void validaCampo() {
        radio = (CheckBox) findViewById(R.id.cbRadio);
        estepe = (CheckBox) findViewById(R.id.cbEstepe);
        macaco = (CheckBox) findViewById(R.id.cbMacaco);
        chaveRodas = (CheckBox) findViewById(R.id.cbChaveRodas);
        triangulo = (CheckBox) findViewById(R.id.cbTriangulo);
        extintor = (CheckBox) findViewById(R.id.cbExtintor);
        calotas = (CheckBox) findViewById(R.id.cbCalotas);
        documentos = (CheckBox) findViewById(R.id.cbDocumentos);
        tapetesBorracha = (CheckBox) findViewById(R.id.cbTapeteBorracha);
        tapetesCarpete = (CheckBox) findViewById(R.id.cbTapeteCarpete);
        rodaLiga = (CheckBox) findViewById(R.id.cbRodaLiga);
        faroisAux = (CheckBox) findViewById(R.id.cbFaroisAux);
        chaves = (CheckBox) findViewById(R.id.cbChaves);
        bateria = (CheckBox) findViewById(R.id.cbBateria);
        retrovisorEletrico = (CheckBox) findViewById(R.id.cbRetrovisorEletrico);
        bagageiro = (CheckBox) findViewById(R.id.cbBagageiro);
        rack = (CheckBox) findViewById(R.id.cbRack);
        carter = (CheckBox) findViewById(R.id.cbCarter);
        moto = (CheckBox) findViewById(R.id.cbMoto);

        proximo = (Button) findViewById(R.id.btProximo);
        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaBanco();
            }
        });
    }

    private void salvaBanco() {
        checklist = new Checklist();
        String marcado = " X ";
        if(radio.isChecked()) checklist.setRadio(marcado);
        if(estepe.isChecked()) checklist.setEstepe(marcado);
        if(macaco.isChecked()) checklist.setMacaco(marcado);
        if(chaveRodas.isChecked()) checklist.setChaveRodas(marcado);
        if(triangulo.isChecked()) checklist.setTriangulo(marcado);
        if(extintor.isChecked()) checklist.setExtintor(marcado);
        if(calotas.isChecked()) checklist.setCalotas(marcado);
        if(documentos.isChecked()) checklist.setDocumentos(marcado);
        if(tapetesBorracha.isChecked()) checklist.setTapetesBorracha(marcado);
        if(tapetesCarpete.isChecked()) checklist.setTapetesCarpete(marcado);
        if(rodaLiga.isChecked()) checklist.setRodaLiga(marcado);
        if(faroisAux.isChecked()) checklist.setFaroisAux(marcado);
        if(chaves.isChecked()) checklist.setChaves(marcado);
        if(bateria.isChecked()) checklist.setBateria(marcado);
        if(retrovisorEletrico.isChecked()) checklist.setRetrovisorEletrico(marcado);
        if(bagageiro.isChecked()) checklist.setBagageiro(marcado);
        if(rack.isChecked()) checklist.setRack(marcado);
        if(carter.isChecked()) checklist.setProtetorCarter(marcado);
        if(moto.isChecked()) checklist.setMoto(marcado);
        inspecao = new InspecaoDao(this).recupera();
        checklist.setIdInspecao(inspecao.getId());

        ChecklistDao dao = new ChecklistDao(this);
        dao.inserir(checklist);
        Intent it = new Intent(CheckListActivity.this, FiguraActivity.class);
        startActivity(it);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_limpar, menu);
        return true;
    }
}
