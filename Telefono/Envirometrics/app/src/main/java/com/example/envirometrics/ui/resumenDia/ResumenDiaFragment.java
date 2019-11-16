package com.example.envirometrics.ui.resumenDia;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.envirometrics.IntroActivity;
import com.example.envirometrics.R;

public class ResumenDiaFragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_resumen_diario, container, false);

        Intent i = new Intent(getContext(), IntroActivity.class);
        startActivity(i);

        return root;

    }
}
