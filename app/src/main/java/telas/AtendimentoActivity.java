package telas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.balbino.checkguincho.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao.LocalizacaoDao;
import dao.TipoRegistroDao;
import dao.UsuarioDao;
import model.Localizacao;
import model.TipoRegistro;
import model.Usuario;

public class AtendimentoActivity extends AppCompatActivity {

    private TextView nomeEmpresa, data, nomePrestador, localizacao;
    private Button iniciaAtendimento;
    private Usuario usuario;
    private Localizacao l;
    private TipoRegistro tipoRegistro;

    /* LOCALIZAÇÃO */
    private Location location;
    private LocationManager locationManager;
    private Address endereco;
    private double latitude = 0.0;
    private double longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atendimento);
        validaCampo();
    }

    private void validaCampo() {
        nomeEmpresa = (TextView) findViewById(R.id.tvNomeEmpresa);
        data = (TextView) findViewById(R.id.tvData);
        nomePrestador = (TextView) findViewById(R.id.tvNomePrestador);
        localizacao = (TextView) findViewById(R.id.tvLocalizacao);
        iniciaAtendimento = (Button) findViewById(R.id.btIniciaAtendimento);

        iniciaAtendimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaInformacoes();
            }
        });

        /* DADOS DO USUARIO */
        usuario = new UsuarioDao(this).recupera();
        nomeEmpresa.setText(usuario.getNomeEmpresa());
        nomePrestador.setText(usuario.getNomeMorotista());

        l = new Localizacao();
        tipoRegistro = new TipoRegistro();

        /* RECUPERANDO A DATA */
        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date dataAtual = calendar.getTime();
        data.setText(dataFormatada.format(dataAtual));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }   locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        location =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location != null) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        } else localizacao.setText("Localização indisponível...!");

        try {
            endereco = buscaEndereco(latitude, longitude);
            localizacao.setText(endereco.getAddressLine(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void salvaInformacoes() {
        LocalizacaoDao lDao = new LocalizacaoDao(this);
        TipoRegistroDao tDao = new TipoRegistroDao(this);
        tipoRegistro.setTipoRegistro("inicio do atendimento");
        //long idTipo = tDao.inserir(tipoRegistro);

        l.setData(data.getText().toString());
        l.setLatitude(String.valueOf(latitude));
        l.setLongitude(String.valueOf(longitude));
      //  l.setIdTipoRegistro((int) idTipo);
        l.setEndereco(localizacao.getText().toString());
       // long idLoc = lDao.insere(l);

        Intent it = new Intent(AtendimentoActivity.this, FormularioActivity.class);
      //  it.putExtra("idLocalizacao", idLoc);
        startActivity(it);
    }

    private Address buscaEndereco(double latitude, double longitude) throws IOException {
        Geocoder geocoder;
        Address address = null;
        List<Address> addresses;

        geocoder = new Geocoder(getApplicationContext());
        addresses = geocoder.getFromLocation(latitude, longitude, 1);
        if(addresses.size() > 0){
            address = addresses.get(0);
        }
            return address;
    }
}
