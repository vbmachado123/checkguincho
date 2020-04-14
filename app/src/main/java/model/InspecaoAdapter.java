package model;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.balbino.checkguincho.R;

import java.util.List;

public class InspecaoAdapter extends BaseAdapter {

    private List<Inspecao> inspecao;
    private Activity activity;

    public InspecaoAdapter(Activity activity, List<Inspecao> inspecao) {
        this.activity = activity;
        this.inspecao = inspecao;
    }

    @Override
    public int getCount() {
        return inspecao.size();
    }

    @Override
    public Object getItem(int i) {

        return inspecao.get(i);
    }

    @Override
    public long getItemId(int i) {
        return inspecao.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
     View v = activity.getLayoutInflater().inflate(R.layout.lista_inspecao, parent, false);
     TextView nome = v.findViewById(R.id.tvNome);
     TextView telefone = v.findViewById(R.id.tvTelefone);
     Inspecao inspecaoExibe = inspecao.get(i);

     nome.setText(inspecaoExibe.getNomeProprietario());
     telefone.setText(inspecaoExibe.getTelefone());
     return v;
    }
}