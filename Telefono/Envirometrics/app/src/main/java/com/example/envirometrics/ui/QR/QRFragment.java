package com.example.envirometrics.ui.QR;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private TextView tituloQR;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_qr, container, false);
        laLogica = new LogicaFake(getContext());
        Hawk.init(getContext()).build();

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new  String[]{Manifest.permission.CAMERA}, 3);
        }

        if(!Hawk.contains("sensorAsociado")) {
            Intent intent = new Intent(getActivity(), QrScannerActivity.class);
            startActivityForResult(intent, QrScannerActivity.QR_REQUEST_CODE);
        }
        else{
            tituloQR = root.findViewById(R.id.tituloQR);
        }


        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



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
    private void enviarDatosAlServidor (int idTaxista, final int idSensor){
        //Hacer llamada a método de la lógica para registrar sensor

        laLogica.asociarSensorUsuario(idTaxista,idSensor,
                new PeticionarioREST.Callback () {
                    @Override
                    public void respuestaRecibida(int codigo, String cuerpo) {
                        if(codigo==200){
                            //Todo OK
                            Hawk.put("sensorAsociado",idSensor);
                            Hawk.put("idSensor",idSensor);
                        }

                        if (codigo == 300) {
                            //Ya está registrado el sensor
                            tituloQR.setText("Sensor ya registrado");
                        }

                    }
                });
    }

}
