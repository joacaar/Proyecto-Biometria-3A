package com.example.envirometrics;

import android.Manifest;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.navigation.NavigationView;
import com.orhanobut.hawk.Hawk;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static int REQUEST_BLUETOOTH = 1;

    public LogicaFake laLogicaFake;
    public ReceptorBLE receptorBle;
    private BluetoothAdapter bluetoothAdapter;
    private String value;

    private boolean activarServicio;

    private AppBarConfiguration mAppBarConfiguration;

    private Intent intencion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Pedimos los permisos al inicio para poder activar el servicio
        pedirPermisoGPS();

        Hawk.init(this).build();

        //----------------------------------------------------
        //                  Beacon
        //----------------------------------------------------
        activarServicio = false;

        //Inicializamos el receptor bluetooth para comprobar si el bt esta activo
        if(receptorBle == null){
            receptorBle = new ReceptorBLE(this);

        }
        laLogicaFake = new LogicaFake(this);

        // creamos la intencion que nos ejecutara el servicio y la notificacion en primer plano
        //intencion = new Intent(MainActivity.this, Servicio.class);
        //startService(intencion);



    }

    //----------------------------------------------------------------------------------------------
    // onStart ()
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onStart (){
        super.onStart();
        if(receptorBle.checkBtOn()){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED){
                //startService(intencion);
            }
        }

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
                R.id.nav_map, R.id.nav_perfil, R.id.nav_ajustes, R.id.nav_cerrar_sesion)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }
//Funcion para comprobar y pedir los permisos de GPS y en caso de tenerlos, pedir los del BT
    public void pedirPermisoGPS(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED //&&
                /*ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED*/) {
            ActivityCompat.requestPermissions(this, new  String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 3);
        }else{
            if(receptorBle == null){
                receptorBle = new ReceptorBLE(this);
            }
            if(receptorBle.btActived() != null) {
                startActivityForResult(receptorBle.btActived(), REQUEST_BLUETOOTH);
            }
        }
    }

    //Respuesta de la peticion de permisos del GPS
    @Override
    public void onRequestPermissionsResult(int respuesta, String[] permissions, int[]grantResult){
        super.onRequestPermissionsResult(respuesta, permissions, grantResult);
        Log.d("---PERMISOS---", "--- ID Permiso: " + respuesta);

        if(respuesta==3){
            if(grantResult.length > 0 && grantResult[0] == PackageManager.PERMISSION_GRANTED){
                Log.d("---PERMISOS---", "---Permiso concedido---");
                activarServicio = true;
                if(receptorBle.btActived() != null) {
                    startActivityForResult(receptorBle.btActived(), REQUEST_BLUETOOTH);
                }
            }
            if(grantResult.length > 0 && PackageManager.PERMISSION_DENIED == grantResult[0]){
                avisarPermisos();
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
                if(receptorBle.checkBtOn()){
                    if(activarServicio){
                        startService(intencion);
                    }
                }
                return;
            }
            avisarPermisos();
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

        //Obtener datos del usuario registrado
        String emailUsuario = Hawk.get("email");

        TextView nombreUsuarioNav = findViewById(R.id.nombreUsuarioNav);

        nombreUsuarioNav.setText(emailUsuario);

        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // Funcion que mostrara un dialogo de aviso para recordar que sin el GPS o el Bluetooth
    // la aplicacion no dinpondra de todas sus funcionalidades
    public void avisarPermisos(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);

        // set title
        alertDialogBuilder.setTitle("Aviso!");

        // set dialog message
        alertDialogBuilder
                .setMessage("La aplicación no funcionara correctamente sin los permisos." + "\n" +
                        "Desea aceptar los permisos?")
                .setCancelable(false)
                .setPositiveButton("SI",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, close
                        // current activity
                        pedirPermisoGPS();

                    }
                })
                .setNegativeButton("NO",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    //Iniciar actividad de la cámara
    public void onClickFab (View view){
        Intent i = new Intent(this, FotoActivity.class);
        startActivity(i);
    }
}
