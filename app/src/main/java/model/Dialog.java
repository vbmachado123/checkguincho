package model;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.view.LayoutInflaterCompat;

import com.balbino.checkguincho.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class Dialog extends AppCompatDialogFragment {
    private EditText nomeResponsavel, rgResponsavel;
    private RecuperaTextoListener listener;

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_assinatura, null);

        builder.setView(view)
                .setTitle("Dados Responsável")
                .setCancelable(false)
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nome = nomeResponsavel.getText().toString();
                String rg = rgResponsavel.getText().toString();
                listener.recuperaTexto(nome, rg);
            }
        });
        nomeResponsavel = view.findViewById(R.id.etNomeResponsavel);
        rgResponsavel = view.findViewById(R.id.etRgResponsavel);

        SimpleMaskFormatter smfRg = new SimpleMaskFormatter("NN.NNN.NNN");
        MaskTextWatcher mtwRg = new MaskTextWatcher(rgResponsavel, smfRg);
        rgResponsavel.addTextChangedListener(mtwRg);

        return  builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (RecuperaTextoListener) context;
        } catch (ClassCastException e){
            throw  new ClassCastException(context.toString() +
                   "Erro ao implementar interface" );
        }
    }

    public interface RecuperaTextoListener{
        void recuperaTexto(String nome, String rg);
    }
}
