package telas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.balbino.checkguincho.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dao.FigurasDao;
import dao.InspecaoDao;
import dao.LocalizacaoDao;
import dao.TipoRegistroDao;
import model.Desenho;
import model.Dialog;
import model.FigurasInspecao;
import model.Inspecao;
import model.Localizacao;
import model.TipoRegistro;

public class AssinaturaRetiraActivity extends AppCompatActivity implements Dialog.RecuperaTextoListener {

    private Button proximo;
    private Desenho desenho;

    private String nomeDialog, rgDialog;
    private String caminho;

    private FigurasInspecao figuras;
    private FigurasDao dao;

    /* LOCALIZAÇÃO */
    private Localizacao l;
    private TipoRegistro tipoRegistro;
    private Location location;
    private LocationManager locationManager;
    private Address endereco;
    private double latitude = 0.0;
    private double longitude = 0.0;

    private String localizacao, dataFinal;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assinatura_retira);

        figuras = new FigurasDao(this).recupera();

        abrirDialog();

        proximo = (Button) findViewById(R.id.btSalvarAssinatura);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Assinatura Retira");
        setSupportActionBar(toolbar);

        final RelativeLayout parent = (RelativeLayout) findViewById(R.id.rlAssinaRetira);
        desenho = new Desenho(this, 0xFF000000, 3);
        parent.addView(desenho);

        proximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.setDrawingCacheEnabled(true);
                Bitmap b = parent.getDrawingCache();

                FileOutputStream out = null;
                File mydir = new File(Environment.getExternalStorageDirectory() + "/CheckGuincho");
                if(mydir.exists()) mydir.mkdir();

               try{
                   caminho = mydir + "/Imagens/" + "_" + nomeDialog + "_" + rgDialog + "_" + ".jpg";
                   Log.i("LOG: ", "Figura: " + figuras.getCaminhoFigura());
                   Log.i("LOG: ", "Figura: " + caminho);

                    out = new FileOutputStream(caminho);
                    b.compress(Bitmap.CompressFormat.JPEG, 60, out);
                    out.flush();
                    out.close();
                    Toast.makeText(AssinaturaRetiraActivity.this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }

                dao = new FigurasDao(AssinaturaRetiraActivity.this);

                figuras.setCaminhoAssinaturaRetira(caminho);
                dao.atualizar(figuras);
                //Salvar a localizacao, tipo registro e a figura
                salvarLocalizacao();
            }
        });
    }

    private void salvarLocalizacao() {
        l = new Localizacao();
        tipoRegistro = new TipoRegistro();

        Inspecao inspecao = new InspecaoDao(AssinaturaRetiraActivity.this).recupera();

        /* RECUPERANDO A DATA */
        SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Date dataAtual = calendar.getTime();
        dataFinal = dataFormatada.format(dataAtual);

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
        } else localizacao = "Localização indisponível...!";
        try {  endereco = buscaEndereco(latitude, longitude);
            localizacao = endereco.getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e){
            localizacao = "Localização indisponível...!";
        }

        LocalizacaoDao lDao = new LocalizacaoDao(this);
        TipoRegistroDao tDao = new TipoRegistroDao(this);
        tipoRegistro.setTipoRegistro("Retirada do veículo");
        long idTipo = tDao.inserir(tipoRegistro);

        l.setData(dataFinal);
        l.setLatitude(String.valueOf(latitude));
        l.setLongitude(String.valueOf(longitude));
        l.setIdTipoRegistro((int) idTipo);
        l.setEndereco(localizacao);
        l.setIdInspecao(inspecao.getId());
        long idLoc = lDao.insere(l);

        Intent it = new Intent(AssinaturaRetiraActivity.this, DestinoActivity.class);
        startActivity(it);
    }

    private void abrirDialog() {
        Dialog dialog = new Dialog();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "example dialog");
    }

    @Override
    public void recuperaTexto(String nome, String rg) {
        /* PARA SALVAR OS DADOS NO NOME DA IMAGEM */
        nomeDialog = nome;
        rgDialog = rg;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_limpar, menu);
        return true;
    }
}
