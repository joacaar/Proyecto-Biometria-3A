
//var request = require ('request')
const IP_PUERTO="http://localhost:8080"

// --------------------------------------------------------------------------
//module.exports =
//---------------------------------------------------------------------------
class Proxy {

  // ------------------------------------------------------------------------
  // constructor()
  // -------------------------------------------------------------------------
  constructor(){
    console.log("Soy un proxy")
  }

  //---------------------------------------------------------------------------
  // datos:{usuario:Texto, password:Texto} -> iniciarSesion() -> V/F
  //----------------------------------------------------------------------------
  iniciarSesion( datos, callback ){

    var datosUsuario = {
      nombre: datos.nombre, password: datos.password
    }

      fetch(IP_PUERTO+"/iniciarSesionAdmin", {
      method: 'POST', // or 'PUT'
      body: JSON.stringify(datosUsuario), // data can be `string` or {object}!
      headers:{
         'User-Agent' : 'jordi', 'Content-Type' : 'application/json'
      }
      }).then( (res) => {
        return res.json()
      }).catch( (error) => {
        return error
      }).then( (data) => {
        callback(data)
      })

    }

  //----------------------------------------------------------------------------
  // idUsuario:N --> getUltimaMedidaDeUnUsuario()
  //  --> JSON{ valorMedida: R, latitud:R, longitud:R, tiempo:N, idMedida:N,
  // idTipoMedida:N, idUsuario:N }
  //----------------------------------------------------------------------------
  getUltimaMedidaDeUnUsuario( idUsuario, callback ){

    var myInit = { method: 'GET',
                   headers: {
                     'User-Agent' : 'jordi', 'Content-Type' : 'application/json'
                   },
                   mode: 'cors',
                   cache: 'default' };


    fetch(IP_PUERTO+"/ultimaMedida/" + idUsuario, myInit)
    .then((res)=>{
      return res.json();
    })
    .then((data)=>{
      callback(data);
    })

  }

  //----------------------------------------------------------------------------
  // getUsuarios() -->
  //[JSON{email:Texto, password:Texto, telefono:Texto, idUsuario:N}]
  //----------------------------------------------------------------------------
  getUsuarios( callback ){

    var myInit = { method: 'GET',
                   headers: {
                     'User-Agent' : 'jordi', 'Content-Type' : 'application/json'
                   },
                   mode: 'cors',
                   cache: 'default' };


    fetch(IP_PUERTO+"/usuarios", myInit)
    .then((res)=>{
      return res.json();
    })
    .then((data)=>{
      callback(data);
    })

  }

  //----------------------------------------------------------------------------
  // getTodasLasMedidas() -->
  // [JSON{ valorMedida: R, latitud:R, longitud:R, tiempo:N, idMedida:N,
  // idTipoMedida:N, idUsuario:N }]
  //----------------------------------------------------------------------------
  getTodasLasMedidas( callback ){

    var myInit = { method: 'GET',
                   headers: {
                     'User-Agent' : 'jordi', 'Content-Type' : 'application/json'
                   },
                   mode: 'cors',
                   cache: 'default' };


    fetch(IP_PUERTO+"/getTodasLasMedidas", myInit)
    .then((res)=>{
      return res.json();
    })
    .then((data)=>{
      callback(data);
    })

  }

  //----------------------------------------------------------------------------
  // email:Texto -->
  // getTodasLasMedidasDeUnUsuarioPorEmail() -->
  // [JSON{ valorMedida: R, latitud:R, longitud:R, tiempo:N, idMedida:N,
  // idTipoMedida:N, idUsuario:N }]
  //----------------------------------------------------------------------------
  getTodasLasMedidasDeUnUsuarioPorEmail( email, callback ){

    var myInit = { method: 'GET',
                   headers: {
                     'User-Agent' : 'jordi', 'Content-Type' : 'application/json'
                   },
                   mode: 'cors',
                   cache: 'default' };


    fetch(IP_PUERTO+"/getTodasLasMedidasDeUnUsuarioPorEmail/" + email, myInit)
    .then((res)=>{
      return res.json();
    })
    .then((data)=>{
      callback(data);
    })

  }

  //----------------------------------------------------------------------------
  // datos:{email:Texto, password:Texto, telefono:Texto} -->
  // darAltaUsuario() -->
  // {respuesta:v/f, idUsuario:N}
  //----------------------------------------------------------------------------
  darAltaTaxista( datos, callback ){

    var datosUsuario = {
      email: datos.email, password: datos.password,
      telefono: datos.telefono
    }

      fetch(IP_PUERTO+"/darAltaUsuario", {
      method: 'POST', // or 'PUT'
      body: JSON.stringify(datosUsuario), // data can be `string` or {object}!
      headers:{
         'User-Agent' : 'jordi', 'Content-Type' : 'application/json'
      }
      }).then( (res) => {
        return res.json()
      }).catch( (error) => {
        return error
      }).then( (data) => {
        callback(data)
      })

    }

    //----------------------------------------------------------------------------
    // idTipoMedida:N -->
    // darAltaSensor() -->
    // {respuesta:v/f, idUsuario:N}
    //----------------------------------------------------------------------------
    darAltaSensor( idTipoMedida, callback ){

      var datosSensor = {
        idTipoMedida: idTipoMedida
      }

        fetch(IP_PUERTO+"/insertarSensor", {
        method: 'POST', // or 'PUT'
        body: JSON.stringify(datosSensor), // data can be `string` or {object}!
        headers:{
           'User-Agent' : 'jordi', 'Content-Type' : 'application/json'
        }
        }).then( (res) => {
          return res.json()
        }).catch( (error) => {
          return error
        }).then( (data) => {
          callback(data)
        })

      }

      //----------------------------------------------------------------------------
      // datos:{email:Texto, password:Texto, telefono:Texto} -->
      // darAltaUsuario() -->
      // {respuesta:v/f, idUsuario:N}
      //----------------------------------------------------------------------------
      darSensorAUsuario( datos, callback ){

        var datosSensorUsuario = {
          idSensor: datos.idSensor, idUsuario: datos.idUsuario
        }

          fetch(IP_PUERTO+"/darSensorAUsuario", {
          method: 'POST', // or 'PUT'
          body: JSON.stringify(datosSensorUsuario), // data can be `string` or {object}!
          headers:{
             'User-Agent' : 'jordi', 'Content-Type' : 'application/json'
          }
          }).then( (res) => {
            return res.json()
          }).catch( (error) => {
            return error
          }).then( (data) => {
            callback(data)
          })

        }

}
