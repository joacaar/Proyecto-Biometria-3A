package com.example.envirometrics;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

public class Servicio extends Service {

    private NotificationManager notificationManager;
    static final String CANAL_ID = "mi_canal";
    static final int NOTIFICACION_ID = 1;
    private Notification.Builder notificacion;

    private ReceptorBLE receptor;


    @Override
    public void onCreate(){

        receptor = new ReceptorBLE(this);
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel( CANAL_ID, "Mis Notificaciones", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Descripcion del canal"); notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Service-------", "Servicio iniciado");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificacion = new
                    Notification.Builder(this, CANAL_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Título")
                    .setContentText("Texto de la notificación.");
        }else{
            notificacion = new
                    Notification.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Título")
                    .setContentText("Texto de la notificación.");
        }


        PendingIntent intencionPendiente = PendingIntent.getActivity( this , 0, new Intent( this , MainActivity.class ),PendingIntent.FLAG_CANCEL_CURRENT);
        notificacion.setContentIntent(intencionPendiente);

        //notificationManager.notify(NOTIFICACION_ID, notificacion.build()); Crea la notificacion
        startForeground(NOTIFICACION_ID, notificacion.build()); // Asocia la vida de la notificacion a la del servicio
        //Cuando este es destruido, la notificacion tambien.
        receptor.obtenerCO();
        Log.d("Comportamiento??????", String.valueOf(Calendar.getInstance().getTime()));

        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        Log.d("Service-------", "Servicio destruido");

        // The service is no longer used and is being destroyed
    }
}
