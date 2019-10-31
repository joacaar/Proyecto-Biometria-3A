package com.example.envirometrics;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import com.orhanobut.hawk.Hawk;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    public static int REQUEST_BLUETOOTH = 1;

    public LogicaFake laLogicaFake;
    public ReceptorBLE receptorBle;
    private BluetoothAdapter bluetoothAdapter;

    private AppBarConfiguration mAppBarConfiguration;

    private Intent intencion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----------------------------------------------------
        //                  Beacon
        //----------------------------------------------------
        //Se piden los permisoso de localizacion o se comprueban que la app disponga de ellos
        pedirPermisoGPS();

        //Inicializamos el receptor bluetooth para comprobar si el bt esta activo
        receptorBle = new ReceptorBLE(this);
        laLogicaFake = new LogicaFake();

        // creamos la intencion que nos ejecutara el servicio y la notificacion en primer plano
        intencion = new Intent(MainActivity.this, Servicio.class);
        startService(intencion);

        //----------------------------------------------------
        //              NAVIGATION DRAWER
        //----------------------------------------------------
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_map, R.id.nav_perfil, R.id.nav_slideshow,
                R.id.nav_ajustes, R.id.nav_cerrar_sesion, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        /* NO FUNCIONA SI ESTA TODO EN EL MAIN, HAY QUE HACER QUE MIDA DE MANERA AUTOMATICA SIN DEPENDER DE UN BOTON
        //Cuando se pulsa el boton epieza a escanear llamando a la funcion obtenerCO()
        Button scanBoton = findViewById(R.id.scan);
        scanBoton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.e("--- DEBUG BT ---", "Boton escanear pulsado");
                receptorBle.obtenerCO();
                Log.e("--- DEBUG BT ---", "Despues de la llamada a obtenerCO()");
                //new EscanerSegundoPlano().execute(receptorBle);//Ejecutamos la tarea asincrona para buscar dispositivos
                Log.e("--- DEBUG BT ---", "A ver cuando se ejecuta esto");
            }
        });

        //Cuando se pulsa el boton para de escanear llamando a la funcion stopScan()
        Button stopBoton = findViewById(R.id.stop);
        stopBoton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("---BT---", "Boton de stop pulsado");
                receptorBle.stopScan();
            }
        });

        Button anunciarCo = findViewById(R.id.anunciarCO);
        anunciarCo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                anunciarCO();
            }
        });
*/

    }

    //----------------------------------------------------------------------------------------------
    // onStart ()
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onStart (){
        super.onStart();


        //Comprobamos el estado del bluetooth y pedimos al usuario que se active si este no lo esta
        //En el resultado comprobaremos la decision del usuario y activaremos la posibilidad de
        //ejecutar el servicio o no
        if(receptorBle.btActived() != null) {
            startActivityForResult(receptorBle.btActived(), REQUEST_BLUETOOTH);
        }

        // Comprobamos el bluetooth para activar, o no, los botones
        if(receptorBle.checkBtOn()){
            startService(intencion);
        }


    }

    public void pedirPermisoGPS(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    3);
        }
    }

    @Override
    public void onRequestPermissionsResult(int respuesta, String[] permissions, int[]grantResult){
        if(respuesta==3){
            if(grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED){

            }else{
                finish();
            }
        }
    }

    // REsultado de la peticion de activacion de bluetooth, si es activado activaremos los botones
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_BLUETOOTH) // Filtramos el resultado por el codigo de la actividad
        {
            //resultcode puede ser 0 si no se ha activado BT o -1 si este ha sido activado
            if(resultCode == -1){

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
