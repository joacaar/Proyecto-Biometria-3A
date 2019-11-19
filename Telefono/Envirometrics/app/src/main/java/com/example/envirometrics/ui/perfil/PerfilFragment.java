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

    private EditText email;
    private TextView nombre;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_perfil, container, false);


        Hawk.init(getContext()).build();

        nombre = root.findViewById(R.id.textoPerfil);

        String emailUser = Hawk.get("email");
        nombre.setText(emailUser);


        return root;
    }
}