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


    private String urlServidor = "http://172.20.10.5:8080/";

    public LogicaFake(Context context){
            Hawk.init(context).build();
        }


    // -------------------------------------------------------------------------------
    //                        medicion: Medida --> anunciarCO()
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

    // ----------------------------------------------------------------------------------------
    //                  usuario: Usuario --> darAltaUsuario() --> elCallback()
    // ----------------------------------------------------------------------------------------
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


    // ------------------------------------------------------------------------------------------
    //        email: String, password:String --> iniciarSesion() --> elCallback()
    // ------------------------------------------------------------------------------------------
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
    //                      getTodasLasMedidas() --> elCallback
    // -------------------------------------------------------------------------------
    public void getTodasLasMedidas(PeticionarioREST.Callback elCallback) {

        PeticionarioREST elPeticionario = new PeticionarioREST();

        Map<String, String> params = new HashMap<String, String>();

        JSONObject eljson = new JSONObject(params);

        elPeticionario.hacerPeticionREST("GET", this.urlServidor + "getTodasLasMedidas", eljson.toString(), elCallback,
                "application/json; charset=utf-8"
        );
    }

    // ----------------------------------------------------------------------------------------------
    //            email: String, emailNuevo: String --> getTodasLasMedidas() --> elCallback
    // ----------------------------------------------------------------------------------------------
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

    // -------------------------------------------------------------------------------------------------------
    //            idUsuario: String --> obtenerDistanciaRecorridaEnUnDia() --> elCallback
    // -------------------------------------------------------------------------------------------------------
    public void obtenerDistanciaRecorridaEnUnDia(int idUsuario, PeticionarioREST.Callback elCallback) {

        PeticionarioREST elPeticionario = new PeticionarioREST();

        Map<String, String> params = new HashMap<String, String>();
        params.put("idUsuario", String.valueOf(idUsuario));

        JSONObject eljson = new JSONObject(params);

        elPeticionario.hacerPeticionREST("GET", this.urlServidor + "distanciaRecorridaEnUnDia", eljson.toString(), elCallback,
                "application/json; charset=utf-8"
        );
    }

    // -------------------------------------------------------------------------------------------------------
    //            idUsuario: String --> obtenerDistanciaRecorridaEnUnDia() --> elCallback
    // -------------------------------------------------------------------------------------------------------
    public void darSensorAUsuario(int idUsuario, int idSensor, PeticionarioREST.Callback elCallback) {

        PeticionarioREST elPeticionario = new PeticionarioREST();

        Map<String, String> params = new HashMap<String, String>();
        params.put("idUsuario", String.valueOf(idUsuario));
        params.put("idSensor", String.valueOf(idSensor));


        JSONObject eljson = new JSONObject(params);

        elPeticionario.hacerPeticionREST("POST", this.urlServidor + "asociarSensorUsuario", eljson.toString(), elCallback,
                "application/json; charset=utf-8"
        );
    }


} // class

