
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
  // datos:{valorMedida: R} -> insertarMedida() ->
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
  // Texto --> getMedicionesPorFecha() --> JSON{ medidaCO: R, hora: Texto, fecha: Texto }
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
  // getMedicionesPorFecha() -->
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


}
