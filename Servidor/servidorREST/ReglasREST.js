// .....................................................................
// Autor: Emilio Esteve Peiró
// Fecha inicio: 24/10/2019
// Última actualización: 24/10/2019
// ReglasREST.js
// .....................................................................

const path = require('path')

module.exports.cargar = function( servidorExpress, laLogica ) {

// .......................................................
// GET /prueba
// .......................................................
servidorExpress.get('/prueba', function( peticion, respuesta ){
  console.log( " * GET /prueba " )
  respuesta.send( "¡Funciona!" )
}) // get /prueba

// .......................................................
// GET /medidaPorIdMedida/<idMedida>
// .......................................................
servidorExpress.get('/medidaPorIdMedida/:idMedida',
  async function( peticion, respuesta ){
    console.log( " * GET /medidasPorIdMedida " )
    // averiguo la fecha
    var idMedida = peticion.params.idMedida
    // llamo a la función adecuada de la lógica
    var res = await laLogica.buscarMedidasPorIdMedida( idMedida )
    // si no hay resultados...
    if( res ) {
      // 404: not found
      respuesta.status(404).send( "no encontré medidas con esa id " + idMedida )
      return
    }
    // todo ok
    respuesta.send( JSON.stringify( res ) )
  }) // get /medidaPorIdMedida/<idMedida>

  // .......................................................
  // GET /medidasPorIdUsuario/<idMedida>
  // .......................................................
  servidorExpress.get('/medidasPorIdUsuario/:idUsuario',
    async function( peticion, respuesta ){
      console.log( " * GET /medidasPorIdUsuario " )
      // averiguo la fecha
      var idUsuario = peticion.params.idUsuario
      // llamo a la función adecuada de la lógica
      var res = await laLogica.buscarMedidasPorIdUsuario( idUsuario )
      // si no hay resultados...
      if( res.length == 0 ) {
        // 404: not found
        respuesta.status(404).send( "no encontré medidas con esa id " + idUsuario )
        return
      }
      // todo ok
      respuesta.send( JSON.stringify( res ) )
    }) // get /medidasPorIdUsuario/<idUsuario>

    // .......................................................
    // GET /medidasPorIdUsuario/<idMedida>
    // .......................................................
    servidorExpress.get('/ultimaMedida/:idUsuario',
      async function( peticion, respuesta ){
        console.log( " * GET /ultimaMedida " )
        // averiguo la fecha
        var idUsuario = peticion.params.idUsuario
        // llamo a la función adecuada de la lógica
        var res = await laLogica.getUltimaMedidaDeUnUsuario( idUsuario )
        // si no hay resultados...
        if( res.length == 0 ) {
          // 404: not found
          respuesta.status(404).send( "no encontré medidas con esa id " + idUsuario )
          return
        }
        // todo ok
        respuesta.send( JSON.stringify( res ) )
      }) // get /medida/<idMedida>

    // .......................................................
    // GET /medidasPorIdUsuario/<idMedida>
    // .......................................................
    servidorExpress.get('/buscarSensor/:idSensor',
      async function( peticion, respuesta ){
        console.log( " * GET /buscarSensor " )
        // averiguo la fecha
        var idSensor = peticion.params.idSensor
        // llamo a la función adecuada de la lógica
        var res = await laLogica.buscarSensor( idSensor )
        // si no hay resultados...
        if( res.length == 0 ) {
          // 404: not found
          respuesta.status(404).send( "no encontré sensor con esa id " + idSensor )
          return
        }
        // todo ok
        respuesta.send( JSON.stringify( res ) )
      }) // get /medida/<idMedida>

      // .......................................................
      // GET /usuarios
      // .......................................................
      servidorExpress.get('/usuarios',
        async function( peticion, respuesta ){
          console.log( " * GET /usuarios " )
          // averiguo la fecha
          var res = await laLogica.getUsuarios()
          // si no hay resultados...
          if( res.length == 0 ) {
            // 404: not found
            respuesta.status(404).send( "No encontré usuarios" )
            return
          }
          // todo ok
          respuesta.send( JSON.stringify( res ) )
        }) // get /usuarios

        // .......................................................
        // GET /getTodasLasMedidas
        // .......................................................
        servidorExpress.get('/getTodasLasMedidas',
          async function( peticion, respuesta ){
            console.log( " * GET /getTodasLasMedidas " )

            var res = await laLogica.getTodasLasMedidas()
            // si no hay resultados...
            if( res.length == 0 ) {
              // 404: not found
              respuesta.status(404).send( "No encontré medidas" )
              return
            }
            // todo ok
            respuesta.send( JSON.stringify( res ) )
          }) // get /getTodasLasMedidas

          // .......................................................
          // GET /getTodasLasMedidasDeUnUsuarioPorEmail/<email>
          // .......................................................
          servidorExpress.get('/getTodasLasMedidasDeUnUsuarioPorEmail/:email',
            async function( peticion, respuesta ){
              console.log( " * GET /getTodasLasMedidasDeUnUsuarioPorEmail " )

              var email = peticion.params.email

              var res = await laLogica.getTodasLasMedidasDeUnUsuarioPorEmail(email)
              // si no hay resultados...
              if( res.length == 0 ) {
                // 404: not found
                respuesta.status(404).send( "No encontré medidas" )
                return
              }
              // todo ok
              respuesta.send( JSON.stringify( res ) )
            }) // get /getTodasLasMedidas


    //-----------------------------------------------------------------------------
    // POST /insertarMedida
    // peticion.body --> JSON
    // al llamarlo deberemos insertar un JSON en el body para que lo pueda procesar.
    //-----------------------------------------------------------------------------
    servidorExpress.post('/insertarMedida',
      async function( peticion, respuesta ){
        console.log( " * POST /insertarMedida " )
        var datos = JSON.parse( peticion.body )
        // supuesto procesamiento
	      console.log(peticion.body);

        // llamamos al método de la lógica que se encarga de insertar medida
        await laLogica.insertarMedida(datos);

        // enviarmos una respuesta que demuestra que todo ha salido correctamente
        respuesta.send({laRespuesta: "OK"});
	      console.log("Peticion POST insertarMedida recibido");
    }) // post / insertarMedida

    //-----------------------------------------------------------------------------
    // POST /insertarMedida
    // peticion.body --> JSON
    // al llamarlo deberemos insertar un JSON en el body para que lo pueda procesar.
    //-----------------------------------------------------------------------------
    servidorExpress.post('/cambiarPassword',
      async function( peticion, respuesta ){
        console.log( " * POST /insertarMedida " )
        var datos = JSON.parse( peticion.body )
        // supuesto procesamiento
	      console.log(peticion.body);

        await laLogica.cambiarPassword(datos)
        // enviarmos una respuesta que demuestra que todo ha salido correctamente
        respuesta.send({respuesta: "OK"});
	      console.log("Peticion POST cambiarPassword recibido");
    }) // post / insertarMedida

    //-----------------------------------------------------------------------------
    // POST /darAltaUsuario
    // peticion.body --> JSON
    // al llamarlo deberemos insertar un JSON en el body para que lo pueda procesar.
    //-----------------------------------------------------------------------------
    servidorExpress.post('/darAltaUsuario',
      async function( peticion, respuesta ){
        console.log( " * POST /darAltaUsuario " )
        var datos = JSON.parse( peticion.body )
        // supuesto procesamiento
        console.log(peticion.body);

        // llamamos al método de la lógica que se encarga de registrar usuario
        var seHaRegistradoElUsuario = await laLogica.darAltaUsuario(datos);

        var res = await laLogica.buscarUsuarioPorEmail(datos.email)

        console.log(seHaRegistradoElUsuario)

        if(seHaRegistradoElUsuario){
          respuesta.send({respuesta:seHaRegistradoElUsuario, idUsuario:res.idUsuario})
        }

        respuesta.send({respuesta:seHaRegistradoElUsuario})

        console.log("Peticion POST darAltaUsuario recibido");
    }) // post / darAltaUsuario

    //-----------------------------------------------------------------------------
    // POST /insertarSensor
    // peticion.body --> JSON
    // al llamarlo deberemos insertar un JSON en el body para que lo pueda procesar.
    //-----------------------------------------------------------------------------
    servidorExpress.post('/insertarSensor',
      async function( peticion, respuesta ){
        console.log( " * POST /insertarSensor " )
        var datos = JSON.parse( peticion.body )
        // supuesto procesamiento
        console.log(peticion.body);

        // llamamos al método de la lógica que se encarga de insertar medida
        await laLogica.insertarSensor(datos);

        // enviarmos una respuesta que demuestra que todo ha salido correctamente
        respuesta.send("OK");
        console.log("Peticion POST insertarSensor recibido");
    }) // post / insertarSensor

    //-----------------------------------------------------------------------------
    // POST /insertarSensor
    // peticion.body --> JSON
    // al llamarlo deberemos insertar un JSON en el body para que lo pueda procesar.
    //-----------------------------------------------------------------------------
    servidorExpress.post('/insertarTipoSensor',
      async function( peticion, respuesta ){
        console.log( " * POST /insertarTipoSensor " )
        var datos = JSON.parse( peticion.body )
        // supuesto procesamiento
        console.log(peticion.body);

        // llamamos al método de la lógica que se encarga de insertar medida
        await laLogica.insertarTipoSensor(datos);

        // enviarmos una respuesta que demuestra que todo ha salido correctamente
        respuesta.send( {laRespuesta: "OK"} );
        console.log("Peticion POST insertarSensor recibido");
    }) // post / insertarTipoSensor

    //-----------------------------------------------------------------------------
    // POST /iniciarSesion
    // peticion.body --> JSON
    // al llamarlo deberemos insertar un JSON en el body para que lo pueda procesar.
    //-----------------------------------------------------------------------------
    servidorExpress.post('/iniciarSesion',
      async function( peticion, respuesta ){
        console.log( " * POST /iniciarSesion " )
        var datos = JSON.parse( peticion.body )
        // supuesto procesamiento
        console.log(peticion.body);

        // llamamos al método de la lógica que se encarga de insertar medida
        var res = await laLogica.iniciarSesion(datos);
        var res2 = await laLogica.buscarUsuarioPorEmail(datos.email)
        console.log(res)

        if( res ){
          respuesta.send({respuesta:true, idUsuario:res2.idUsuario});
        }

        respuesta.send({respuesta:false});


        console.log("Peticion POST insertarSensor recibido");
    }) // post / iniciarSesion

    //-----------------------------------------------------------------------------
    // POST /iniciarSesion
    // peticion.body --> JSON
    // al llamarlo deberemos insertar un JSON en el body para que lo pueda procesar.
    //-----------------------------------------------------------------------------
    servidorExpress.post('/iniciarSesionAdmin',
      async function( peticion, respuesta ){
        console.log( " * POST /iniciarSesion " )
        var datos = JSON.parse( peticion.body )
        // supuesto procesamiento
        console.log(peticion.body);

        // llamamos al método de la lógica que se encarga de insertar medida
        var res = await laLogica.iniciarSesionAdmin(datos);
        console.log(res)

        if( res ){
          respuesta.send(true);
        }

        respuesta.send(false);


        console.log("Peticion POST insertarSensor recibido");
    }) // post / iniciarSesion

    //-----------------------------------------------------------------------------
    // POST /insertarMedida
    // peticion.body --> JSON
    // al llamarlo deberemos insertar un JSON en el body para que lo pueda procesar.
    //-----------------------------------------------------------------------------
    servidorExpress.post('/borrarFilasDe/:tabla',
      async function( peticion, respuesta ){

        var tabla = peticion.params.tabla;

        await laLogica.borrarFilasDe(tabla);

        respuesta.send("OK")

    }) // post / borrarFilasDe/<tabla>

  //-----------------------------------------------------------------------------
  // GET /ux/<pagina>
  //-----------------------------------------------------------------------------
  servidorExpress.get('/ux/:pagina', function( peticion, respuesta ){
      console.log( " servint html normal: " + peticion.params.pagina )

      var elPath = path.join(__dirname, '..', 'ux');
      respuesta.sendFile( elPath + "/" + peticion.params.pagina);
  });

  //-----------------------------------------------------------------------------
  // GET /ux/html/<pagina>
  //-----------------------------------------------------------------------------
  servidorExpress.get('/ux/html/:pagina', function( peticion, respuesta ){
      console.log( " servint html normal: " + peticion.params.pagina )

      var elPath = path.join(__dirname, '..', 'ux', 'html');
      respuesta.sendFile( elPath + "/" + peticion.params.pagina);
  });

  //-----------------------------------------------------------------------------
  // GET /ux/css/<estilos>
  //-----------------------------------------------------------------------------
  servidorExpress.get('/ux/css/:estilos', function( peticion, respuesta ){
      console.log( " servint css: " + peticion.params.estilos )

      var elPath = path.join(__dirname, '..', 'ux', 'css');
      respuesta.sendFile( elPath + "/" + peticion.params.estilos);
  });

  //-----------------------------------------------------------------------------
  // GET /ux/images/<imagen>
  //-----------------------------------------------------------------------------
  servidorExpress.get('/ux/images/:imagen', function( peticion, respuesta ){
      console.log( " servint imagenes: " + peticion.params.imagen )

      var elPath = path.join(__dirname, '..', 'ux', 'images');
      respuesta.sendFile( elPath + "/" + peticion.params.imagen);
  });

} // cargar()

// .....................................................................
// .....................................................................
