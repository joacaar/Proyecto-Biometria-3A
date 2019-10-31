package com.example.envirometrics;

import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogicaFake {


        // -------------------------------------------------------------------------------
        // -------------------------------------------------------------------------------
        interface RespuestaAPreguntarAlgo {
            public void respuesta( String respuesta );
        } // interface

        // -------------------------------------------------------------------------------
        // -------------------------------------------------------------------------------
        private String urlServidor = "http://192.168.1.139:8080/";

    // -------------------------------------------------------------------------------
    // -------------------------------------------------------------------------------
    public void anunciarCO( Medicion medicion) {

        PeticionarioREST elPeticionario = new PeticionarioREST();

        Map<String, String> params = new HashMap<String, String>();
        params.put("medidaCO", String.valueOf(medicion.getMedidaCO()));
        params.put("hora", medicion.getHora());
        params.put("fecha", medicion.getFecha());
        params.put("latitud", String.valueOf(medicion.getLatitud()));
        params.put("longitud", String.valueOf(medicion.getLongitud()));

        JSONObject eljson = new JSONObject(params);

        elPeticionario.hacerPeticionREST("POST", this.urlServidor + "insertarMedicion", eljson.toString(),
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






















        /*

        // -------------------------------------------------------------------------------
        // -------------------------------------------------------------------------------
        public void enviarMedicionDeAlgo( String datosJSON ) {

            PeticionarioREST elPeticionario = new PeticionarioREST();

            elPeticionario.hacerPeticionREST("POST",  this.urlServidor + "/posts/", datosJSON,
                    new PeticionarioREST.Callback () {
                        @Override
                        public void respuestaRecibida( int codigo, String cuerpo ) {
                            Log.d( "RESPUESTA RECIBIDA", "Logica.enviarMedicionDeAlgo() respuestaRecibida: codigo = "
                                    + codigo + " cuerpo=" + cuerpo);
                        }
                    },
                    "application/json; charset=utf-8"
            );

        /*
        Ejempo con curl
        curl -d '{"key1":"pepito", "key2":"value2"}' -H "Content-Type: application/json; charset=utf-8" -X POST http://jsonplaceholder.typicode.com/posts/
         */

        // -------------------------------------------------------------------------------
        // -------------------------------------------------------------------------------
        public void preguntarAlgo( final RespuestaAPreguntarAlgo cb ) {

            PeticionarioREST elPeticionario = new PeticionarioREST();

            elPeticionario.hacerPeticionREST("GET",  this.urlServidor + "/posts/", null,
                    new PeticionarioREST.Callback () {
                        @Override
                        public void respuestaRecibida( int codigo, String cuerpo ) {
                            Log.d( "RESPUESTA RECIBIDA", "Logica.preguntarAlgo() respuestaRecibida: codigo = "
                                    + codigo + " cuerpo=" + cuerpo);
                            cb.respuesta( cuerpo );
                        } // ()
                    } // new
            );

        } // ()

} // class

