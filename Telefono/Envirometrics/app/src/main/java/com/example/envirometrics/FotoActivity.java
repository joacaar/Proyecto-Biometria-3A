package com.example.envirometrics;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;

public class FotoActivity extends Activity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView foto;
    private ImageView salir;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

        foto = findViewById(R.id.fotoTomada);
        salir = findViewById(R.id.arrowLeftFoto);



        Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(i, REQUEST_IMAGE_CAPTURE);

        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            Bitmap imageBitmap = (Bitmap)data.getExtras().get("data");
            foto.setImageBitmap(imageBitmap);

        }
    }

    @Override
    public void onRequestPermissionsResult(int respuesta, String[] permissions, int[]grantResult){
        super.onRequestPermissionsResult(respuesta, permissions, grantResult);
        Log.d("---PERMISOS---", "--- ID Permiso: " + respuesta);

        if(respuesta==5){
            if(grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("---PERMISOS---", "---Permiso concedido---");

            }
            if(grantResult.length > 0 && PackageManager.PERMISSION_DENIED == grantResult[0]){

            }
        }

    }
}
