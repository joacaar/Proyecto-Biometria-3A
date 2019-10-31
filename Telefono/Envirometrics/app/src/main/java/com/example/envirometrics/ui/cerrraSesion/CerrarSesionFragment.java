package com.example.envirometrics.ui.cerrraSesion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.envirometrics.LoginActivity;
import com.example.envirometrics.R;
import com.example.envirometrics.ui.home.HomeViewModel;
import com.orhanobut.hawk.Hawk;

public class CerrarSesionFragment extends Fragment {

    private CerrarSesionViewModel cerrarSesionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Hawk.init(this.getActivity()).build();
        Hawk.deleteAll();

        View root = null;

        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
        this.getActivity().finish();

        return root;
    }

}