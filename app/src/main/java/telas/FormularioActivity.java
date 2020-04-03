package telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.balbino.checkguincho.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import dao.CorDao;
import dao.InspecaoDao;
import dao.LocalizacaoDao;
import dao.MarcaDao;
import dao.ModeloDao;
import dao.UsuarioDao;
import model.Checklist;
import model.Cor;
import model.Inspecao;
import model.Localizacao;
import model.Marca;
import model.Modelo;
import model.Usuario;

public class FormularioActivity extends AppCompatActivity {

    private EditText nomeMotorista, telefoneMotorista, marca, modelo, ano, cor, placa;
    private CheckBox recusaInspecao;

    private int intInspecao;

    /* MODELOS */
    private Marca mMarca;
    private Modelo mModelo;
    private Cor cCor;
    private Localizacao localizacao;
    private Usuario usuario;
    private Inspecao inspecao;

    /* DAOs */
    private CorDao corDao = new CorDao(this);
    private InspecaoDao inspecaoDao = new InspecaoDao(this);
    private MarcaDao marcaDao = new MarcaDao(this);
    private ModeloDao modeloDao = new ModeloDao(this);
    private LocalizacaoDao localizacaoDao = new LocalizacaoDao(this);
    private UsuarioDao usuarioDao = new UsuarioDao(this);

    private Button proximo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        validaCampo();
        mascaraCampo();
    }

    private void mascaraCampo() {
        SimpleMaskFormatter smftel = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher mtwTel = new MaskTextWatcher(telefoneMotorista, smftel);
        telefoneMotorista.addTextChangedListener(mtwTel);

        SimpleMaskFormatter smfAno = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher mtwAno = new MaskTextWatcher(ano, smfAno);
        ano.addTextChangedListener(mtwAno);
    }

    private void validaCampo() {
        nomeMotorista = (EditText) findViewById(R.id.etNomeMotorista);
        telefoneMotorista = (EditText) findViewById(R.id.etTelefoneMorista);
        marca = (EditText) findViewById(R.id.etMarca);
        modelo = (EditText) findViewById(R.id.etModelo);
        ano = (EditText) findViewById(R.id.etAno);
        cor = (EditText) findViewById(R.id.etCor);
        placa = (EditText) findViewById(R.id.etPlaca);
        recusaInspecao = (CheckBox) findViewById(R.id.cbRecusaInspecao);

        proximo = (Button) findViewById(R.id.btProximo);
        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaNoBanco();
            }
        });
    }

    private void salvaNoBanco() {
            mMarca = new Marca();
            mModelo = new Modelo();
            cCor = new Cor();
            inspecao = new Inspecao();
            usuario = new Usuario();
            localizacao = new Localizacao();

            usuario = usuarioDao.recupera();

            mMarca.setMarca(marca.getText().toString());
            long idMarca = marcaDao.inserir(mMarca);
            mModelo.setModelo(modelo.getText().toString());
            mModelo.setIdMarca((int) idMarca);
            cCor.setCor(cor.getText().toString());

            long idModelo = modeloDao.inserir(mModelo);
            long idCor = corDao.inserir(cCor);

            localizacao = localizacaoDao.recupera();

            int anoConvertido = Integer.getInteger(ano.getText().toString());

            inspecao.setNomeProprietario(nomeMotorista.getText().toString());
            inspecao.setTelefone(telefoneMotorista.getText().toString());
            inspecao.setAno(anoConvertido);
            inspecao.setPlaca(placa.getText().toString());
            inspecao.setIdCor((int) idCor);
            inspecao.setIdMarca((int) idMarca);
            inspecao.setIdModelo((int) idModelo);
            inspecao.setIdUsuario(usuario.getId());

        if(recusaInspecao.isChecked()) intInspecao = 1;
        else intInspecao = 0;

        inspecao.setInspecao(intInspecao);
        inspecaoDao.insere(inspecao);
        if(intInspecao == 1) acessaActivity(AssinaturaRecusaActivity.class);
        else if(intInspecao == 0) acessaActivity(CheckListActivity.class);
    }

    public void acessaActivity(Class c){
        Intent it = new Intent(FormularioActivity.this, c);
        startActivity(it);
    }
}
