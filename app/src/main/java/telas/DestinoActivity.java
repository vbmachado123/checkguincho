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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.balbino.checkguincho.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao.InspecaoDao;
import dao.LocalizacaoDao;
import dao.TipoRegistroDao;
import model.Inspecao;
import model.Localizacao;
import model.TipoRegistro;

public class DestinoActivity extends AppCompatActivity {

    private ImageView ivLogo;
    private TextView tvdata, tvLocalizacao;
    private Button chegadaDestino;

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
        setContentView(R.layout.activity_destino);

        validaCampo();
    }

    private void validaCampo() {
        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        tvdata = (TextView) findViewById(R.id.tvData);
        tvLocalizacao = (TextView) findViewById(R.id.tvLocalizacao);
        chegadaDestino = (Button) findViewById(R.id.btChegadaDestino);

        l = new Localizacao();
        tipoRegistro = new TipoRegistro();

        /* RECUPERANDO A DATA */
        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date dataAtual = calendar.getTime();
        tvdata.setText(dataFormatada.format(dataAtual));

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
        } else tvLocalizacao.setText("Localização indisponível...!");
        try {  endereco = buscaEndereco(latitude, longitude);
            tvLocalizacao.setText(endereco.getAddressLine(0));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            tvLocalizacao.setText("Localização indisponível...!");
        }

        chegadaDestino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvaInformacoes();
            }
        });
    }

    private void salvaInformacoes() {
        LocalizacaoDao lDao = new LocalizacaoDao(this);
        TipoRegistroDao tDao = new TipoRegistroDao(this);
        tipoRegistro.setTipoRegistro("Chegada ao destino");
        long idTipo = tDao.inserir(tipoRegistro);

        Inspecao inspecao = new InspecaoDao(DestinoActivity.this).recupera();

        l.setData(tvdata.getText().toString());
        l.setLatitude(String.valueOf(latitude));
        l.setLongitude(String.valueOf(longitude));
        l.setIdTipoRegistro((int) idTipo);
        l.setEndereco(tvLocalizacao.getText().toString());
        l.setIdInspecao(inspecao.getId());
        long idLoc = lDao.insere(l);

        Intent it = new Intent(DestinoActivity.this, AssinaturaEntregaActivity.class);
        // Log.i("LOG: ", "Localizacao: " + idLoc);
        Log.i("LOG: ", "Localizacao: " + l.getData());
        Log.i("LOG: ", "Localizacao: " + l.getLatitude());
        Log.i("LOG: ", "Localizacao: " + l.getLongitude());
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
