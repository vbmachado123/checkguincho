package telas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

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
    private CorDao corDao;
    private InspecaoDao inspecaoDao;
    private MarcaDao marcaDao;
    private ModeloDao modeloDao;
    private LocalizacaoDao localizacaoDao;
    private UsuarioDao usuarioDao;

    private Button proximo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        corDao = new CorDao(this);
        inspecaoDao = new InspecaoDao(this);
        marcaDao = new MarcaDao(this);
        modeloDao = new ModeloDao(this);
        localizacaoDao = new LocalizacaoDao(this);
        usuarioDao = new UsuarioDao(this);

        validaCampo();
        mascaraCampo();
    }

    private void mascaraCampo() {
        SimpleMaskFormatter smfTelefone = new SimpleMaskFormatter("(NN) NNNNN-NNNN");
        MaskTextWatcher mtwTelefone = new MaskTextWatcher(telefoneMotorista, smfTelefone);
        telefoneMotorista.addTextChangedListener(mtwTelefone);

        SimpleMaskFormatter smfAno = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher mtwAno = new MaskTextWatcher(ano, smfAno);
        ano.addTextChangedListener(mtwAno);
    }

    private void validaCampo() {
        mMarca = new Marca();
        mModelo = new Modelo();
        cCor = new Cor();
        inspecao = new Inspecao();
        usuario = new Usuario();
        localizacao = new Localizacao();

        nomeMotorista = (EditText) findViewById(R.id.etNomeProprietario);
        telefoneMotorista = (EditText) findViewById(R.id.etTelefone);
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
                boolean campo = validaCampoVazio();

                if(campo) salvaNoBanco();
            }
        });
    }

    private boolean validaCampoVazio() {
        boolean campo = true;

        if(nomeMotorista.getText().length() == 0){
            nomeMotorista.setError("Campo vazio!");
            campo = false;
        } else if(telefoneMotorista.getText().length() == 0){
            telefoneMotorista.setError("Campo vazio!");
            campo = false;
        } else if(marca.getText().length() == 0){
            marca.setError("Campo vazio!");
            campo = false;
        } else if(modelo.getText().length() == 0){
            modelo.setError("Campo vazio!");
            campo = false;
        } else if(ano.getText().length() == 0){
            ano.setError("Campo vazio!");
            campo = false;
        } else if(cor.getText().length() == 0){
            cor.setError("Campo vazio!");
            campo = false;
        } else if(placa.getText().length() == 0){
            placa.setError("Campo vazio!");
            campo = false;
        }
        return campo;
    }

    private void salvaNoBanco() {
            usuario = usuarioDao.recupera();

            mMarca.setMarca(marca.getText().toString());
            long idMarca = marcaDao.inserir(mMarca);
            mModelo.setModelo(modelo.getText().toString());
            mModelo.setIdMarca((int) idMarca);
            cCor.setCor(cor.getText().toString());

            long idModelo = modeloDao.inserir(mModelo);
            long idCor = corDao.inserir(cCor);

            localizacao = localizacaoDao.recupera();

            int anoConvertido = Integer.parseInt(ano.getText().toString());

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
        long idInspecao = inspecaoDao.insere(inspecao);

        /* SALVANDO LOCALIZAÇÃO */
        localizacao = localizacaoDao.recupera();
        localizacao.setIdInspecao((int) idInspecao);
        localizacaoDao.atualizar(localizacao);

        if(intInspecao == 1) acessaActivity(AssinaturaRecusaActivity.class);
        else if(intInspecao == 0) acessaActivity(CheckListActivity.class);
    }

    public void acessaActivity(Class c){
        Intent it = new Intent(FormularioActivity.this, c);
        startActivity(it);
    }
}
