package com.example.envirometrics.ui.resumenDia;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.envirometrics.LogicaFake;
import com.example.envirometrics.PeticionarioREST;
import com.example.envirometrics.R;
import com.google.gson.JsonArray;
import com.orhanobut.hawk.Hawk;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;

import lecho.lib.hellocharts.view.LineChartView;

public class ResumenDiaFragment extends Fragment {

    private LineChartView chart;
    private TextView distancia;
    private LogicaFake laLogica;
    private int idUsuario;
    private TextView textoMediaContaminacion;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.d("SOY ONCREATEVIEW","zzzzzz");
        View root = inflater.inflate(R.layout.fragment_resumen_diario, container, false);

        Hawk.init(getContext()).build();

        idUsuario = Hawk.get("id");


        laLogica = new LogicaFake(getContext());
        chart = root.findViewById(R.id.chart);
        distancia = root.findViewById(R.id.textViewDistancia);
        textoMediaContaminacion = root.findViewById(R.id.textoMediaContaminacion);

        obtenerDistanciaRecorrida();
        empezarHacerDibujoContaminacionDiaria();
        obtenerCalidadDelAireRespiradoDuranteElDia();

        return root;

    }

    // -----------------------------------------------------------------------------
    //  JSONArray -> f()
    // -----------------------------------------------------------------------------
    private void hacerDibujoContaminacionDiaria(JSONArray jsonArrayMedidas) throws JSONException {

        Log.d("SOY hacerDibujoCont","zzzzzz");

        chart.setInteractive(true);

        List<PointValue> values = new ArrayList<PointValue>();

        values.add(new PointValue(0, 2));
        values.add(new PointValue(1, 3));
        values.add(new PointValue(2, 4));
        values.add(new PointValue(3, 3));
        values.add(new PointValue(4, 4));

        //
        //
        //
        /*
        for(int i=0; i < 5; i++) {

            JSONObject object = jsonArrayMedidas.getJSONObject(i);

            double medida = object.getDouble("valorMedida");

            values.add(new PointValue(i, i++));
            Log.d("VALOR GRAFICA", String.valueOf(medida));

            //values.add(new PointValue(1, 56));
            //values.add(new PointValue(2, 4));
            //values.add(new PointValue(3, 3));
            //values.add(new PointValue(4, 4));
        }*/


        //
        // In most cased you can call data model methods in builder-pattern-like manner.
        //
        Line line = new Line(values).setColor(Color.rgb(0,180,154)).setCubic(true).setHasLabels(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        AxisValue axisValueX;
        List<AxisValue> valores = new ArrayList<AxisValue>();

        String[] horas = {"8:00", "12:00", "18:00", "20:00", "22:00"};
        String[] contaminacion = new String[horas.length];

        //
        //
        //
        for (int i = 0; i < horas.length; i++){
            contaminacion[i]=horas[i];
            axisValueX = new AxisValue(i).setLabel(contaminacion[i]);// se le asigna a cada posicion el label que se desea
            // "i" es el valor del indice y dias es el string que mostrara el label
            valores.add(axisValueX);
        }

        Axis axisX = new Axis().setValues(valores);
        //Axis axisY = Axis.generateAxisFromRange(0, 200, 10);// para añadir un rango al eje Y

        //
        // Añadimos titulo a los indices
        //
        //axisX.setName("Horas");
        //axisY.setName("Contaminación %");

        // asignamos cada eje a su posicion en la grafica
        data.setAxisXBottom(axisX);
        //data.setAxisYLeft(axisY);

        chart.setZoomEnabled(false);

        //Le pasamos toda la informacion a la vista de la grafica
        chart.setLineChartData(data);

    }

    // -----------------------------------------------------------------------------
    //
    // -----------------------------------------------------------------------------
    private void empezarHacerDibujoContaminacionDiaria(){
        //
        // busco las medidas para el dibujo
        //
        laLogica.buscarMedidasDelUltimoDiaDeUnUsuario(idUsuario,
                new PeticionarioREST.Callback () {
                    @Override
                    public void respuestaRecibida(int codigo, String cuerpo) {
                        try {
                            JSONArray jsonArrayMedidas = new JSONArray(cuerpo);
                            Log.d("------Medidas------", jsonArrayMedidas.toString());

                            //
                            // ya tengo los datos, llamo a hacer el dibujo
                            //
                            hacerDibujoContaminacionDiaria(jsonArrayMedidas);


                        }catch (JSONException err){
                            Log.d("Error", err.toString());
                        }
                    }
                });
    }

    private void obtenerDistanciaRecorrida (){


        laLogica.distanciaRecorridaEnUnDia(idUsuario,
                new PeticionarioREST.Callback () {
                    @Override
                    public void respuestaRecibida(int codigo, String cuerpo) {
                        try {
                            //Limito la distancia a dos decimales (está en Km)
                            DecimalFormat df = new DecimalFormat("#.0");
                            JSONObject jsonObject = new JSONObject(cuerpo);
                            distancia.setText(df.format(jsonObject.get("respuesta")) + " Km");

                        }catch (JSONException err){
                            Log.d("Error", err.toString());
                        }
                    }
                });
    }

    private void obtenerCalidadDelAireRespiradoDuranteElDia () {
        laLogica.calidadDelAireRespiradoEnElUltimoDia(idUsuario,
                new PeticionarioREST.Callback () {
                    @Override
                    public void respuestaRecibida(int codigo, String cuerpo) {
                        if(codigo == 200){
                            try {
                                JSONObject jsonObject = new JSONObject(cuerpo);
                                textoMediaContaminacion.setText(jsonObject.get("respuesta").toString() + " ppb");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

}
