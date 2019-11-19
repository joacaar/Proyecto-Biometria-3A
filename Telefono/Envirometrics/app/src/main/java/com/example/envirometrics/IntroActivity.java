package com.example.envirometrics;

import android.Manifest;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;

import androidx.viewpager.widget.ViewPager;

import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;


public class IntroActivity extends com.heinrichreimersoftware.materialintro.app.IntroActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()

        addSlide(new SimpleSlide.Builder()
                .title("¿Qué es Envirometrics?")
                .description("Envirometrics te permite viajar en taxi por un trayecto con menos contaminación.")
                .image(R.drawable.taxi_env)
                .background(R.color.colorPrimaryDark)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("Mapa")
                .description("Un mapa con toda la información necesaria sobre la contaminación de tu ciudad.")
                .image(R.drawable.mapa_intro)
                .background(R.color.colorPrimaryDark)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("Análisis de contaminación")
                .description("Con simplemente hacer una foto a tu entorno envirometrics te dirá cuanta contaminación hay.")
                .image(R.drawable.foto_cont)
                .background(R.color.colorPrimaryDark)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title("Resumen del día")
                .description("¿Quieres saber cuanta contaminación has respirado en un día? Envirometrics te muestra un resumen de tu día con todo tipo de detalles.")
                .image(R.drawable.grafico)
                .background(R.color.colorPrimaryDark)
                .scrollable(false)
                .build());

        setButtonBackVisible(false);
    }

}