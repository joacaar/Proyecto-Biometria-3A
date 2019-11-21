package com.example.envirometrics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.example.envirometrics.ui.home.HomeFragment;
import com.orhanobut.hawk.Hawk;

import java.util.List;
import java.util.Objects;

public class Ajustes extends AppCompatActivity {

    String TAG = "---Debug Ajustes ---";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    RadioGroup rg;
    RadioButton rbSelected;

    Switch btnServ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        rg = findViewById(R.id.rg);
        btnServ = findViewById(R.id.btnServicio);

        Hawk.init(this).build();
        //Para comprobar si es taxista y habilitar o no el servicio
        if(Hawk.get("esTaxista", false)){
            btnServ.setEnabled(true);
        }

        //Declaramos unas preferencias para los ajustes en modo privado y su editor
        preferences = getSharedPreferences("Ajustes", MODE_PRIVATE);
        editor = preferences.edit();

        //Comprobamos que el permiso del servicio existe en las preferencias
        if(preferences.contains("permisoServicio")){
            if(preferences.getBoolean("permisoServicio", false))
            btnServ.setChecked(true);
            else
                btnServ.setChecked(false);
        }else{
            btnServ.setChecked(false);
        }

        //Comprobamos que el campo tipoMedida existe, en caso negativo mostraremos por defecto la primera opcion elegida
        if(!preferences.contains("tipoMedida")){
            Log.d(TAG, "tipoMedida no existe");
            rg.check(R.id.rb1);
            editor.putString("tipoMedida", "1");
            Log.d(TAG, preferences.getString("tipoMedida", "error: nulo"));
        }else{//En caso afirmativo se obtiene la opcion elegida para mostrar en las opciones la actual
            Log.d(TAG, "Tipo medida existe");
            Log.d(TAG, preferences.getString("tipoMedida", "error: nulo"));
                switch (preferences.getString("tipoMedida", "0")){
                    case "0":
                        rbSelected = findViewById(R.id.rb2);
                        rbSelected.setChecked(true);
                        break;
                    case "1":
                        rbSelected = findViewById(R.id.rb1);
                        rbSelected.setChecked(true);
                        break;
                    case "2":
                        rbSelected = findViewById(R.id.rb2);
                        rbSelected.setChecked(true);
                        break;
                    case "3":
                        rbSelected = findViewById(R.id.rb3);
                        rbSelected.setChecked(true);
                        break;
                    case "4":
                        rbSelected = findViewById(R.id.rb4);
                        rbSelected.setChecked(true);
                        break;
                    case "5":
                        rbSelected = findViewById(R.id.rb5);
                        rbSelected.setChecked(true);
                        break;
                    case "6":
                        rbSelected = findViewById(R.id.rb6);
                        rbSelected.setChecked(true);
                        break;
                }
        }
    }

    //Funcion para obtener la opcion del tipo de medida que elige ver el usuario
    public void obtenerOpcionElegida(View v){

        boolean marcado = ((RadioButton) v).isChecked();

        switch (v.getId()) {
            case R.id.rb1:
                if (marcado) {
                    editor.putString("tipoMedida", "1");
                }
                break;

            case R.id.rb2:
                if (marcado) {
                    editor.putString("tipoMedida", "2");
                }
                break;

            case R.id.rb3:
                if (marcado) {
                    editor.putString("tipoMedida", "3");
                }
                break;
            case R.id.rb4:
                if (marcado) {
                    editor.putString("tipoMedida", "4");
                }
                break;
            case R.id.rb5:
                if (marcado) {
                    editor.putString("tipoMedida", "5");
                }
                break;
            case R.id.rb6:
                if (marcado) {
                    editor.putString("tipoMedida", "6");
                }
                break;
        }
        editor.commit();
        Log.d("Debug-Preferences", preferences.getString("tipoMedida","error"));
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    //Funcion para permitir o no el funcionamiento del servicio en segundo plano
    public void permitirServicio(View v){
        if(btnServ.isChecked()){
            editor.putBoolean("permisoServicio", true);
        }else{
            editor.putBoolean("permisoServicio", false);
        }
        editor.commit();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
