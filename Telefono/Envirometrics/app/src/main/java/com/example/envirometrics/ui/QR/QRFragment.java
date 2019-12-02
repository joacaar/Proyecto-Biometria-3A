package com.example.envirometrics.ui.QR;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.envirometrics.LogicaFake;
import com.example.envirometrics.MainActivity;
import com.example.envirometrics.PeticionarioREST;
import com.example.envirometrics.R;
import com.orhanobut.hawk.Hawk;

import org.json.JSONException;
import org.json.JSONObject;

import me.ydcool.lib.qrmodule.activity.QrScannerActivity;

import static android.app.Activity.RESULT_OK;

public class QRFragment extends Fragment {

    private LogicaFake laLogica;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_qr, container, false);
        laLogica = new LogicaFake(getContext());

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(getActivity(), QrScannerActivity.class);
        startActivityForResult(intent, QrScannerActivity.QR_REQUEST_CODE);
        Hawk.init(getContext()).build();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QrScannerActivity.QR_REQUEST_CODE) {
            Log.d("QR escaneado", resultCode == RESULT_OK ? data.getExtras().getString(QrScannerActivity.QR_RESULT_STR) : "Nada escaneado!");

            TextView informacionSensor = getActivity().findViewById(R.id.informacionSensor);
            String  idSensor = "";
            String datosQR;
            int idTaxista;

            if(resultCode == RESULT_OK){

            //Obtengo los datos del QR
            datosQR = data.getExtras().getString(QrScannerActivity.QR_RESULT_STR);

                JSONObject mainObject = null;
                try {
                    mainObject = new JSONObject(datosQR);
                    idSensor = mainObject.getString("idSensor");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                idTaxista = Hawk.get("id");
                informacionSensor.setText("Id del sensor: " + idSensor);

                enviarDatosAlServidor(idTaxista, Integer.parseInt(idSensor));

            }else {
                informacionSensor.setText("Nada escaneado");
                Intent i = new Intent(getActivity(), MainActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        }
    }

    // ---------------------------------------------------------------------------------------------------
    //                  String: idTaxista, String: idSensor --> enviarDatosAlServidor()
    // ---------------------------------------------------------------------------------------------------
    private void enviarDatosAlServidor (int idTaxista, int idSensor){
        //Hacer llamada a método de la lógica para registrar sensor

        laLogica.darSensorAUsuario(idTaxista,idSensor,
                new PeticionarioREST.Callback () {
                    @Override
                    public void respuestaRecibida(int codigo, String cuerpo) {

                        if(codigo==200){

                        }

                    }
                });

    }

}
