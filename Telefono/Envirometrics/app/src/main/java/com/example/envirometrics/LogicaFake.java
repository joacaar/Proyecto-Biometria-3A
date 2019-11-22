package com.example.envirometrics;

import android.content.Context;
import android.util.Log;

import com.orhanobut.hawk.Hawk;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogicaFake {

    private final String TAG = "---LogicaDebug---";

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    interface RespuestaAPreguntarAlgo {
        public void respuesta( String respuesta );

    } // interface

    // -------------------------------------------------------------------------------
    //                  Declaración de variables
    // -------------------------------------------------------------------------------
    private String urlServidor = "http://192.168.137.132:8080/";

    // -------------------------------------------------------------------------------
    //                          Constructor()
    // -------------------------------------------------------------------------------
    public LogicaFake(Context context){
            Hawk.init(context).build();
        }


    // -------------------------------------------------------------------------------
    //                          anunciarCO()
    // -------------------------------------------------------------------------------
    public void anunciarCO( Medida medicion) {

        PeticionarioREST elPeticionario = new PeticionarioREST();

        Map<String, String> params = new HashMap<String, String>();
        params.put("idUsuario", String.valueOf(Hawk.get("id")));
        params.put("idTipoMedida", String.valueOf(1));
        params.put("valorMedida", String.valueOf(medicion.getMedidaCO()));
        params.put("tiempo", String.valueOf(medicion.getTiempo()));
        params.put("latitud", String.valueOf(medicion.getLatitud()));
        params.put("longitud", String.valueOf(medicion.getLongitud()));

        JSONObject eljson = new JSONObject(params);

        elPeticionario.hacerPeticionREST("POST", this.urlServidor + "insertarMedida", eljson.toString(),
                new PeticionarioREST.Callback() {
                    @Override
                    public void respuestaRecibida(int codigo, String cuerpo) {
                        Log.d("RESPUESTA RECIBIDA", "Logica.anunciarCO() respuestaRecibida: codigo = "
                                + codigo + " cuerpo=" + cuerpo);
                    }
                },
                "application/json; charset=utf-8"
        );
    }

    // -------------------------------------------------------------------------------
    //                            darAltaUsuario()
    // -------------------------------------------------------------------------------
    public void darAltaUsuario(Usuario usuario, PeticionarioREST.Callback elCallback) {

        PeticionarioREST elPeticionario = new PeticionarioREST();

        Map<String, String> params = new HashMap<String, String>();
        params.put("email", usuario.getEmail());
        params.put("telefono", usuario.getTelefono());
        params.put("password", usuario.getPassword());

        JSONObject eljson = new JSONObject(params);

        elPeticionario.hacerPeticionREST("POST", this.urlServidor + "darAltaUsuario", eljson.toString(),elCallback,
                "application/json; charset=utf-8");
    }


    // -------------------------------------------------------------------------------
    //                              iniciarSesion()
    // -------------------------------------------------------------------------------
    public void iniciarSesion(String email, String password, PeticionarioREST.Callback elCallback) {

        PeticionarioREST elPeticionario = new PeticionarioREST();


        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);


        JSONObject eljson = new JSONObject(params);

        elPeticionario.hacerPeticionREST("POST", this.urlServidor + "iniciarSesion", eljson.toString(), elCallback,
                "application/json; charset=utf-8"
        );
    }

    // -------------------------------------------------------------------------------
    //                              getTodasLasMedidas()
    // -------------------------------------------------------------------------------
    public void getTodasLasMedidas(PeticionarioREST.Callback elCallback) {

        PeticionarioREST elPeticionario = new PeticionarioREST();

        Map<String, String> params = new HashMap<String, String>();

        JSONObject eljson = new JSONObject(params);

        elPeticionario.hacerPeticionREST("GET", this.urlServidor + "getTodasLasMedidas", eljson.toString(), elCallback,
                "application/json; charset=utf-8"
        );
    }

    // -------------------------------------------------------------------------------
    //                              getTodasLasMedidas()
    // -------------------------------------------------------------------------------
    public void cambiarEmail(String email,String emailNuevo,PeticionarioREST.Callback elCallback) {

        PeticionarioREST elPeticionario = new PeticionarioREST();

        Map<String, String> params = new HashMap<String, String>();
        params.put("email",email);
        params.put("emailNuevo",emailNuevo);

        JSONObject eljson = new JSONObject(params);

        elPeticionario.hacerPeticionREST("POST", this.urlServidor + "cambiarEmail", eljson.toString(), elCallback,
                "application/json; charset=utf-8"
        );
    }


} // class

