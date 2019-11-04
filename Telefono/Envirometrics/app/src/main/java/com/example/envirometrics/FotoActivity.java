package com.example.envirometrics;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class FotoActivity extends Activity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView foto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

        foto = findViewById(R.id.fotoTomada);

        pedirPermisoCamara();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA ) == PackageManager.PERMISSION_GRANTED){
            Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivityForResult(i, REQUEST_IMAGE_CAPTURE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            foto.setImageBitmap(imageBitmap);

        }
    }

    //Funcion para comprobar y pedir los permisos de la camara
    public void pedirPermisoCamara(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new  String[]{Manifest.permission.CAMERA}, 3);
        }
    }

}
