package com.example.envirometrics.ui.perfil;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.envirometrics.LogicaFake;
import com.example.envirometrics.PeticionarioREST;
import com.example.envirometrics.R;
import com.orhanobut.hawk.Hawk;

import org.w3c.dom.Text;

public class PerfilFragment extends Fragment {

    private TextView nombre;
    private EditText emailEditText;
    private EditText password;
    private EditText newPassword;
    private TextView mensajeError;
    private Button btnCambiarDatos;
    public LogicaFake laLogica;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_perfil, container, false);
        laLogica = new LogicaFake(getContext());

        Hawk.init(getContext()).build();

        final String emailUser = Hawk.get("email");
        String telefonoUser = Hawk.get("telefono");

        nombre = root.findViewById(R.id.textoPerfil);
        emailEditText = root.findViewById(R.id.editTextEmail2);
        password = root.findViewById(R.id.editTextPass);
        newPassword = root.findViewById(R.id.editTextNewPass);
        mensajeError = root.findViewById(R.id.mensajeError);
        btnCambiarDatos = root.findViewById(R.id.btnCambiarDatos);


        if(Hawk.get("email")!=null) {
            nombre.setText(emailUser);
        }

        btnCambiarDatos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String pass = password.getText().toString();
                String passwordAntigua = Hawk.get("password");
                String newPass = newPassword.getText().toString();
                final String emailNuevo = emailEditText.getText().toString();

                if(pass.equals(passwordAntigua)){

                    //Iniciar sesión de la logicaFake
                    laLogica.cambiarEmail(emailUser,emailNuevo,
                            new PeticionarioREST.Callback () {
                                @Override
                                public void respuestaRecibida(int codigo, String cuerpo) {
                                    //telefono
                                    //newPass
                                    if(cuerpo.contains("true")){
                                        Hawk.put("email",emailNuevo);
                                    }else{
                                        mensajeError.setText("Este email ya existe");
                                    }
                                }
                            });
                }else{
                    mensajeError.setText("Contraseña incorrecta");
                }
            }
        });

        return root;
    }
}