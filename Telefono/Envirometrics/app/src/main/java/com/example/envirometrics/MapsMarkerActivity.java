package com.example.envirometrics;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.gson.JsonObject;
import com.google.maps.android.heatmaps.HeatmapTileProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.google.maps.android.heatmaps.WeightedLatLng;
import com.orhanobut.hawk.Hawk;

public class MapsMarkerActivity extends Activity implements OnMapReadyCallback {

    //DECLARACION DE VARIABLES GLOBALES
    Context context;
    private HeatmapTileProvider mProvider;
    TileOverlay mOverlay;
    GoogleMap map;
    Criteria criteria;
    private LogicaFake laLogica;
    private JSONObject jsonTodasLasMedidas;


    public MapsMarkerActivity(Context context_){
        this.context=context_;
        laLogica = new LogicaFake(context_);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;

        laLogica.getTodasLasMedidas( new PeticionarioREST.Callback () {
            @Override
            public void respuestaRecibida(int codigo, String cuerpo) {
                try {
                    if(codigo==200) {
                        JSONArray jsonObject = new JSONArray(cuerpo);
                        addHeatMap(jsonObject);
                    }

                }catch (JSONException err){
                    Log.d("Error", err.toString());
                }
            }
        });

        //Quito la opcion navegacion
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(false);


        //Mi posición
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
             googleMap.setMyLocationEnabled(true);
        }

        criteria = new Criteria();

        /*
        Location location = locationManager.getLastKnownLocation(Objects.requireNonNull(locationManager.getBestProvider(criteria, false)));


        if(location!=null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }*/

    }


    //MAPA DE COLOR
    private void addHeatMap(JSONArray jsonObject) {

        List<WeightedLatLng> list = null;

        //Obtener una lista con la latitud, longitud y valor de la medida
        try {
            list = readItems(jsonObject);
        } catch (JSONException e) {
            Toast.makeText(context, "Problem reading list of locations.", Toast.LENGTH_LONG).show();
        }

        // Creando un heat map tile provider, pasando una lista WeightedLatLng
        mProvider = new HeatmapTileProvider.Builder().weightedData(list).radius(50).build();
        // Añado a tile overlay to the map, usando heat map tile provider
        mOverlay = map.addTileOverlay(new TileOverlayOptions().tileProvider(mProvider));

    }

    private List<WeightedLatLng> readItems(JSONArray jsonObject) throws JSONException {

        ArrayList<WeightedLatLng> listValorMedida = new ArrayList<WeightedLatLng>();

        for (int i = 0; i < jsonObject.length(); i++) {
            JSONObject object = jsonObject.getJSONObject(i);
            double lat = object.getDouble("latitud");
            double lng = object.getDouble("longitud");
            double medida = object.getDouble("valorMedida");
            listValorMedida.add(new WeightedLatLng(new LatLng(lat, lng), medida));
        }

        return listValorMedida;
    }

}
