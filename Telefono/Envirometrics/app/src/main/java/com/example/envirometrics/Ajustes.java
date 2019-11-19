package com.example.envirometrics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.envirometrics.ui.home.HomeFragment;

import java.util.List;
import java.util.Objects;

public class Ajustes extends AppCompatActivity {

    String TAG = "---Debug Ajustes ---";

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    RadioGroup rg;
    RadioButton rbSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        rg = findViewById(R.id.rg);

        preferences = getSharedPreferences("Ajustes", MODE_PRIVATE);
        editor = preferences.edit();

        if(!preferences.contains("tipoMedida")){
            Log.d(TAG, "tipoMedida no existe");
            rg.check(R.id.rb1);
            editor.putString("tipoMedida", "1");
            Log.d(TAG, preferences.getString("tipoMedida", "error: nulo"));
        }else{
            Log.d(TAG, "Tipo medida existe");
            Log.d(TAG, preferences.getString("tipoMedida", "error: nulo"));
                switch (preferences.getString("tipoMedida", "0")){
                    case "0":
                        Log.d(TAG, "Tipo medida 0 existe");
                        rbSelected = findViewById(R.id.rb2);
                        rbSelected.setChecked(true);
                        break;
                    case "1":
                        Log.d(TAG, "Tipo medida 1 existe");
                        rbSelected = findViewById(R.id.rb1);
                        rbSelected.setChecked(true);
                        break;
                    case "2":
                        Log.d(TAG, "Tipo medida 2 existe");
                        rbSelected = findViewById(R.id.rb2);
                        rbSelected.setChecked(true);
                        break;
                    case "3":
                        Log.d(TAG, "Tipo medida 3 existe");
                        rbSelected = findViewById(R.id.rb3);
                        rbSelected.setChecked(true);
                        break;
                    case "4":
                        Log.d(TAG, "Tipo medida 4 existe");
                        rbSelected = findViewById(R.id.rb4);
                        rbSelected.setChecked(true);
                        break;
                    case "5":
                        Log.d(TAG, "Tipo medida 5 existe");
                        rbSelected = findViewById(R.id.rb5);
                        rbSelected.setChecked(true);
                        break;
                    case "6":
                        Log.d(TAG, "Tipo medida 6 existe");
                        rbSelected = findViewById(R.id.rb6);
                        rbSelected.setChecked(true);
                        break;
                }
        }
    }

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
        }
        editor.commit();
        Log.d("Debug-Preferences", preferences.getString("tipoMedida","error"));
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
