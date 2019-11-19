package com.example.envirometrics.ui.perfil;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.envirometrics.R;
import com.orhanobut.hawk.Hawk;

import org.w3c.dom.Text;

public class PerfilFragment extends Fragment {

    private TextView nombre;
    private EditText telefono;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_perfil, container, false);


        Hawk.init(getContext()).build();

        String emailUser = Hawk.get("email");
        String telefonoUser = Hawk.get("telefono");

        nombre = root.findViewById(R.id.textoPerfil);
        telefono = root.findViewById(R.id.editTextTelefono2);

        nombre.setText(emailUser);

        telefono.setHint("686377222");

        if(Hawk.get("telefono")!=null) {

            telefono.setHint(telefonoUser);
        }

        return root;
    }

    public void onClickCambiarDatos(View view){


    }
}