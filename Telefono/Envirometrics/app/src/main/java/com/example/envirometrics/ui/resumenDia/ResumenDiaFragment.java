package com.example.envirometrics.ui.resumenDia;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.envirometrics.IntroActivity;
import com.example.envirometrics.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.formatter.SimpleColumnChartValueFormatter;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

public class ResumenDiaFragment extends Fragment {

    LineChartView chart;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_resumen_diario, container, false);

        chart = root.findViewById(R.id.chart);

        generateData();
        chart.startDataAnimation(500);

        return root;

    }


    private void generateData() {

        chart.setInteractive(true);

        List<PointValue> values = new ArrayList<PointValue>();
        values.add(new PointValue(0, 2));
        values.add(new PointValue(1, 3));
        values.add(new PointValue(2, 4));
        values.add(new PointValue(3, 3));
        values.add(new PointValue(4, 4));


        //In most cased you can call data model methods in builder-pattern-like manner.
        Line line = new Line(values).setColor(Color.rgb(0,180,154)).setCubic(true).setHasLabels(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        AxisValue axisValueX;
        List<AxisValue> valores = new ArrayList<AxisValue>();

        String[] horas = {"8:00", "12:00", "18:00", "20:00", "22:00"};
        String[] contaminacion = new String[horas.length];

        for (int i = 0; i < horas.length; i++){
            contaminacion[i]=horas[i];
            axisValueX = new AxisValue(i).setLabel(contaminacion[i]);// se le asigna a cada posicion el label que se desea
            // "i" es el valor del indice y dias es el string que mostrara el label
            valores.add(axisValueX);
        }

        Axis axisX = new Axis().setValues(valores);
        //Axis axisY = Axis.generateAxisFromRange(0, 200, 10);// para añadir un rango al eje Y

        // Añadimos titulo a los indices
        //axisX.setName("Horas");
        //axisY.setName("Contaminación %");

        // asignamos cada eje a su posicion en la grafica
        data.setAxisXBottom(axisX);
        //data.setAxisYLeft(axisY);

        chart.setZoomEnabled(false);

        //Le pasamos toda la informacion a la vista de la grafica
        chart.setLineChartData(data);


    }

}
