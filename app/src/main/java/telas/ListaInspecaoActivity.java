package telas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.balbino.checkguincho.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import dao.InspecaoDao;
import model.Inspecao;
import model.InspecaoAdapter;

public class ListaInspecaoActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private ListView listView;
    private InspecaoDao dao;
    private List<Inspecao> inspecoes;
    private List<Inspecao> inspecoesFiltradas = new ArrayList<>();

    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_inspecao);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Inspeções");
        setSupportActionBar(toolbar);

        validaCampo();

    }

    private void validaCampo() {

        fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(ListaInspecaoActivity.this, HomeActivity.class);
                startActivity(it);
            }
        });

        listView = (ListView) findViewById(R.id.lvInspecoes);
        dao = new InspecaoDao(this);
        inspecoes = dao.obterTodos();
        inspecoesFiltradas.addAll(inspecoes);

        InspecaoAdapter adaptador = new InspecaoAdapter(this, inspecoesFiltradas);

        listView.setAdapter(adaptador);
        registerForContextMenu(listView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        inspecoes = dao.obterTodos();
        inspecoesFiltradas.clear();
        inspecoesFiltradas.addAll(inspecoes);
        listView.invalidateViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.item_pesquisa).getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                procuraInspecao(s);
                return false;
            }
        });
        return true;
    }

    private void procuraInspecao(String texto) {

        inspecoesFiltradas.clear();

        for(Inspecao i : inspecoes){
            if(i.getNomeProprietario().toLowerCase().contains(texto.toLowerCase())){
                inspecoesFiltradas.add(i);
            }
            if(i.getTelefone().toLowerCase().contains(texto.toLowerCase())){
                inspecoesFiltradas.add(i);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.itemSair:
                return true;
            case R.id.itemConfiguracoes:
                return true;
            case R.id.item_pesquisa:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater i = getMenuInflater();
        i.inflate(R.menu.menu_contexto, menu);
    }

    /* PREENCHER DEPOIS */
    public void excluir(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo =
                ( AdapterView.AdapterContextMenuInfo ) item.getMenuInfo();

        final Inspecao inspecaoExcluir = inspecoesFiltradas.get(menuInfo.position);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage("Realmente deseja excluir a inspeção?")
                .setNegativeButton("Cancelar", null)
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        inspecoesFiltradas.remove(inspecaoExcluir);
                        inspecoes.remove(inspecaoExcluir);
                     // dao.exclui(inspecaoExcluir);
                        listView.invalidateViews();

                    }
                }).create();
        dialog.show();
    }

    public void visualizar(MenuItem item){

    }
}
