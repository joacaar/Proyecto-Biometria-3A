// ........................................................
// Autor: Joan Calabuig Artes
// Fecha inicio: 01/12/2019
// Última actualización: 01/12/2019
// mainTestAsociarSensorUsuario.js
// ........................................................
var request = require ('request')
var assert = require ('assert')
// ........................................................
// ........................................................
const IP_PUERTO="http://localhost:8080"
// ........................................................
// main ()
// ........................................................
describe( "Test 28 : Probamos /asociarSensorUSuario", function() {

  // ....................................................
  // PROBAMOS POST /asociarSensorUSuario
  // ....................................................


  it( "probar POST /asociarSensorUsuario", function( hecho ) {
    var datos1 = {
      idUSuario: 2, idSensor: 2341
    }
    request.post(
      { url : IP_PUERTO+"/asociarSensorUsuario",
      headers : { 'User-Agent' : 'jordi', 'Content-Type' : 'application/json' },
      body : JSON.stringify( datosMedida )
    },
    function( err, respuesta, carga ) {
      assert.equal( err, null, "¿ha habido un error?" )
      assert.equal( respuesta.statusCode, 200, "¿El código no es 200 (OK)" );
      hecho()
    } // callback
    ) // .post
  }) // it

  it( "probar POST /asociarSensorUsuario", function( hecho ) {
    var datos1 = {
      idUSuario: 1, idSensor: 2341
    }
    request.post(
      { url : IP_PUERTO+"/asociarSensorUsuario",
      headers : { 'User-Agent' : 'jordi', 'Content-Type' : 'application/json' },
      body : JSON.stringify( datosMedida )
    },
    function( err, respuesta, carga ) {
      assert.equal( err, null, "¿ha habido un error?" )
      assert.equal( respuesta.statusCode, 300, "¿El código no es 200 (OK)" );
      hecho()
    } // callback
    ) // .post
  }) // it

  it( "probar POST /asociarSensorUsuario", function( hecho ) {
    var datos1 = {
      idUSuario: 2, idSensor: 234156789
    }
    request.post(
      { url : IP_PUERTO+"/asociarSensorUsuario",
      headers : { 'User-Agent' : 'jordi', 'Content-Type' : 'application/json' },
      body : JSON.stringify( datosMedida )
    },
    function( err, respuesta, carga ) {
      assert.equal( err, null, "¿ha habido un error?" )
      assert.equal( respuesta.statusCode, 404, "¿El código no es 200 (OK)" );
      hecho()
    } // callback
    ) // .post
  }) // it

}) // describe
