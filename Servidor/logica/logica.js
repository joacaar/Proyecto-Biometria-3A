// .....................................................................
// Autor: Emilio Esteve Peiró
// Fecha inicio: 24/10/2019
// Última actualización: 24/10/2019
// Logica.js
// .....................................................................
const sjcl = require('sjcl')
const sqlite3 = require("sqlite3")
/*const SimpleCrypto = require("simple-crypto-js").default;*/
// .....................................................................
// .....................................................................


module.exports = class Logica {


  // .................................................................
  // menorBD: Texto
  // -->
  // constructor () -->
  // .................................................................
  constructor(nombreBD, cb) {
    this.laConexion = new sqlite3.Database(
      nombreBD,
      (err) => {
        if (!err) {
          this.laConexion.run("PRAGMA foreign_keys = ON")
        }
        cb(err)
      })
  } // ()


  // .................................................................
  // menorTabla:Texto
  // -->
  // borrarFilasDe() -->
  // .................................................................
  borrarFilasDe(tabla) {
    return new Promise((resolver, rechazar) => {
      this.laConexion.run(
        "delete from " + tabla + ";",
        (err) => (err ? rechazar(err) : resolver())
      )
    })
  } // ()


  // .................................................................
  // borrarFilasDeTodasLasTablas() -->
  // .................................................................
  async borrarFilasDeTodasLasTablas() {
    await this.borrarFilasDe("Medidas")
    await this.borrarFilasDe("Sensores")
    await this.borrarFilasDe("Usuarios")
    await this.borrarFilasDe("UsuarioSensor")
    await this.borrarFilasDe("TipoSensores")
  } // ()

  // .................................................................
  // datos:{email:Texto, password:Texto}
  // -->
  // cambiarPassword() -->
  // .................................................................
  cambiarPassword(datos) {
    var textoSQL =
      'UPDATE Usuarios SET password = $password WHERE email = $email'

    var valoresParaSQL = {
      $password: sjcl.encrypt(datos.email, datos.password),
      $email: datos.email
    }
    return new Promise((resolver, rechazar) => {
      this.laConexion.run(textoSQL, valoresParaSQL, function(err) {
        (err ? rechazar(err) : resolver())
      })
    })
  } // ()

  // .................................................................
  // datos:{email:Texto, telefono:Texto}
  // -->
  // cambiarTelefono() -->
  // .................................................................
  cambiarTelefono(datos) {
    var textoSQL =
      'UPDATE Usuarios SET telefono = $telefono WHERE email = $email'

    var valoresParaSQL = {
      $telefono: datos.telefono,
      $email: datos.email
    }
    return new Promise((resolver, rechazar) => {
      this.laConexion.run(textoSQL, valoresParaSQL, function(err) {
        (err ? rechazar(err) : resolver())
      })
    })
  } // ()

  // .................................................................
  // datos:{email:Texto, emailNuevo:Texto}
  // -->
  // cambiarEmail() -->
  // .................................................................
  async cambiarEmail(datos) {

    var textoSQL =
      'UPDATE Usuarios SET email = $emailNuevo WHERE email = $email';

    var valoresParaSQL = {
      $email: datos.email,
      $emailNuevo: datos.emailNuevo
    }

    return new Promise((resolver, rechazar) => {
      this.laConexion.run(textoSQL, valoresParaSQL, function(err) {
        (err ? rechazar(err) : resolver())
      })
    })
  } // ()

  // .................................................................
  // idUsuario:N
  // -->
  // borrarRelacionUsuarioSensorPorIdUsuario() -->
  // .................................................................
  borrarRelacionUsuarioSensorPorIdUsuario(idUsuario) {

    var textoSQL =
      'DELETE from UsuarioSensor where idUsuario=$idUsuario';

    var valoresParaSQL = {
      $idUsuario: idUsuario
    }

    return new Promise((resolver, rechazar) => {
      this.laConexion.run(textoSQL, valoresParaSQL, function(err) {
        (err ? rechazar(err) : resolver())
      })
    })
  } // ()

  // .................................................................
  // idUsuario:N
  // -->
  // borrarMedidasDeUnUsuarioPorIdUsuario() -->
  // .................................................................

  borrarMedidasDeUnUsuarioPorIdUsuario(idUsuario){

    var textoSQL =
      'DELETE from Medidas where idUsuario=$idUsuario';

    var valoresParaSQL = {
      $idUsuario: idUsuario
    }

    return new Promise((resolver, rechazar) => {
      this.laConexion.run(textoSQL, valoresParaSQL, function(err) {
        (err ? rechazar(err) : resolver())
      })
    })

  }

  // .................................................................
  // idUsuario:N
  // -->
  // borrarUsuarioPorIdUsuario() -->
  // .................................................................
  async borrarUsuarioPorIdUsuario(idUsuario) {

    await this.borrarRelacionUsuarioSensorPorIdUsuario(idUsuario);
    await this.borrarMedidasDeUnUsuarioPorIdUsuario(idUsuario);

    var textoSQL =
      'DELETE from Usuarios where idUsuario=$idUsuario';

    var valoresParaSQL = {
      $idUsuario : idUsuario
    }

    return new Promise((resolver, rechazar) => {
      this.laConexion.run(textoSQL, valoresParaSQL, function(err) {
        (err ? rechazar(err) : resolver())
      })
    })
  } // ()

  // .................................................................
  // idSensor:N
  // -->
  // borrarRelacionUsuarioSensorPorIdSensor() -->
  // .................................................................
  borrarRelacionUsuarioSensorPorIdSensor(idSensor) {

    var textoSQL =
      'DELETE from UsuarioSensor where idSensor=$idSensor';

    var valoresParaSQL = {
      $idSensor: idSensor
    }

    return new Promise((resolver, rechazar) => {
      this.laConexion.run(textoSQL, valoresParaSQL, function(err) {
        (err ? rechazar(err) : resolver())
      })
    })
  } // ()

  // .................................................................
  // idSensor:N
  // -->
  // borrarSensorPorIdSensor() -->
  // .................................................................
  async borrarSensorPorIdSensor(idSensor) {

    await this.borrarRelacionUsuarioSensorPorIdSensor(idSensor);

    var textoSQL =
      'DELETE from Sensores where idSensor=$idSensor';

    var valoresParaSQL = {
      $idSensor : idSensor
    }

    return new Promise((resolver, rechazar) => {
      this.laConexion.run(textoSQL, valoresParaSQL, function(err) {
        (err ? rechazar(err) : resolver())
      })
    })
  } // ()


  // .................................................................
  // datos:{valorMedida:R, tiempo:N: latitud:R, longitud:R, idMedida:N, idUsuario:N, idTipoMedida:N}
  // -->
  // insertarMedida() -->
  // .................................................................
  async insertarMedida(datos) {
    var textoSQL =
      'insert into Medidas values( $valorMedida, $tiempo, $latitud, $longitud, $idMedida, $idUsuario, $idTipoMedida );'
    var res = await this.getUltimoIdMedida();
    var valoresParaSQL = {
      $valorMedida: datos.valorMedida,
      $tiempo: datos.tiempo,
      $latitud: datos.latitud,
      $longitud: datos.longitud,
      $idUsuario: datos.idUsuario,
      $idTipoMedida: datos.idTipoMedida,
      $idMedida: res + 1
    }
    return new Promise((resolver, rechazar) => {
      this.laConexion.run(textoSQL, valoresParaSQL, function(err) {
        (err ? rechazar(err) : resolver())
      })
    })
  } // ()

  // .................................................................
  // --> idUsuario: N
  // buscarMedidasPorIdUsuario()
  // --> [{valorMedida:R, tiempo:N: latitud:R, longitud:R, idMedida:N, idUsuario:N, idTipoMedida:N}]
  // .................................................................
  buscarMedidasPorIdUsuario(idUsuario) {
    var textoSQL = "select * from Medidas where idUsuario=$idUsuario";
    var valoresParaSQL = {
      $idUsuario: idUsuario
    }
    return new Promise((resolver, rechazar) => {
      this.laConexion.all(textoSQL, valoresParaSQL,
        (err, res) => {
          (err ? rechazar(err) : resolver(res))
        })
    })
  }

  // .................................................................
  // getUsuarios()
  // --> [{email:Texto, password:Texto, telefono:Texto, idUsuario:N}]
  // .................................................................
  getUsuarios() {
    var textoSQL = "select * from Usuarios";
    var valoresParaSQL = {}
    return new Promise((resolver, rechazar) => {
      this.laConexion.all(textoSQL, valoresParaSQL,
        (err, res) => {
          (err ? rechazar(err) : resolver(res))
        })
    })
  }

  // .................................................................
  // getUltimoIDUsuario()
  // --> N
  // .................................................................
  getUltimoIDUsuario() {
    var textoSQL = "select * from Usuarios";
    var valoresParaSQL = {}
    return new Promise((resolver, rechazar) => {
      this.laConexion.all(textoSQL, valoresParaSQL,
        (err, res) => {
          if (err) {
            rechazar(err)
          }
          if (res.length == 0) {
            resolver(0)
          } else {
            resolver(res[res.length - 1].idUsuario)
          }
        })
    })
  }

  // .................................................................
  // --> idUsuario: N
  // getUltimaMedidaDeUnUsuario()
  // --> {valorMedida:R, tiempo:N: latitud:R, longitud:R, idMedida:N, idUsuario:N, idTipoMedida:N}
  // .................................................................
  async getUltimaMedidaDeUnUsuario(idUsuario) {

    var res = await this.buscarMedidasPorIdUsuario(idUsuario);

    return new Promise((resolver, rechazar) => {
      if (res.length > 0) {
        resolver(res[res.length - 1])
      } else {
        rechazar(null)
      }
    })

  }

  // .................................................................
  // getUltimoIdMedida()
  // --> N
  // .................................................................
  async getUltimoIdMedida() {

    var textoSQL = "select * from Medidas";
    var valoresParaSQL = {}
    return new Promise((resolver, rechazar) => {
      this.laConexion.all(textoSQL, valoresParaSQL,
        (err, res) => {
          if (err) {
            rechazar(err)
          }
          if (res.length == 0) {
            resolver(0)
          } else {
            resolver(res[res.length - 1].idMedida)
          }
        })
    })

  }

  // .................................................................
  // --> email: Texto
  // buscarUsuarioPorEmail()
  // --> {email:Texto, telefono:Texto, password:Texto, idUsuario:Texto}
  // .................................................................
  buscarUsuarioPorEmail(email) {
    var textoSQL = "select * from Usuarios where email=$email";
    var valoresParaSQL = {
      $email: email
    }
    return new Promise((resolver, rechazar) => {
      this.laConexion.all(textoSQL, valoresParaSQL,
        (err, res) => {
          (err ? rechazar(err) : resolver(res[0]))
        })
    })
  }

  //-------------------------------------------------------------------
  // email:Texto -->
  // elUsuarioExiste()
  // --> V/F
  //-------------------------------------------------------------------
  async elUsuarioExiste(email) {

    var res = await this.buscarUsuarioPorEmail(email);

    return new Promise((resolver, rechazar) => {
      try {
        if (res.email != email) {
          resolver(false)
        }

        resolver(true)
      } catch (error) {
        resolver(false)
      }
    })
  }

  // .................................................................
  // -->{email:Texto, telefono:Texto, password:Texto, idUsuario:Texto}
  // darAltaUsuario()
  // .................................................................
  async darAltaUsuario(datos) {
    var textoSQL =
      'insert into Usuarios values ( $email, $password, $idUsuario, $telefono );'

    var ultimaIdUsuario = await this.getUltimoIDUsuario();

    var elUsuarioExiste = await this.elUsuarioExiste(datos.email)

    // encriptamos la contraseña con el email.
    var laPasswordEncriptada = sjcl.encrypt(datos.email, datos.password)

    var valoresParaSQL = {
      $idUsuario: ultimaIdUsuario + 1,
      $email: datos.email,
      $password: laPasswordEncriptada,
      $telefono: datos.telefono
    }

    return new Promise((resolver, rechazar) => {
      if (!elUsuarioExiste) {
        this.laConexion.run(textoSQL, valoresParaSQL, function(err) {
          if (err) {
            rechazar(err)
          }
          resolver(true)
        })
      } else {
        resolver(false)
      }
    })
  }

  // .................................................................
  // -->{idUsuario:N, idSensor:N}
  // darSensorAUsuario()
  // .................................................................
  darSensorAUsuario(datos) {
    var textoSQL =
      'insert into UsuarioSensor values ( $idUsuario, $idSensor );'
    var valoresParaSQL = {
      $idUsuario: datos.idUsuario,
      $idSensor: datos.idSensor
    }
    return new Promise((resolver, rechazar) => {
      this.laConexion.run(textoSQL, valoresParaSQL, function(err) {
        (err ? rechazar(err) : resolver())
      })
    })
  }

  // .................................................................
  // -->{idTipoMedida:N, idSensor:N}
  // insertarSensor()
  // .................................................................
  insertarSensor(datos) {
    var textoSQL =
      'insert into Sensores values ( $idTipoMedida, $idSensor );'
    var valoresParaSQL = {
      $idTipoMedida: datos.idTipoMedida,
      $idSensor: datos.idSensor
    }
    return new Promise((resolver, rechazar) => {
      this.laConexion.run(textoSQL, valoresParaSQL, function(err) {
        (err ? rechazar(err) : resolver())
      })
    })
  }

  // .................................................................
  // -->{idTipoMedida:N, descripcion:Texto}
  // insertarTipoSensor()
  // .................................................................
  insertarTipoSensor(datos) {
    var textoSQL =
      'insert into TipoSensores values ( $idTipoMedida, $descripcion );'
    var valoresParaSQL = {
      $idTipoMedida: datos.idTipoMedida,
      $descripcion: datos.descripcion
    }
    return new Promise((resolver, rechazar) => {
      this.laConexion.run(textoSQL, valoresParaSQL, function(err) {
        (err ? rechazar(err) : resolver())
      })
    })
  }

  // .................................................................
  // --> idSensor:N
  // getUsuarioQueTieneElSensor()
  // --> {idSensor:N, idUsuario:N}
  // .................................................................
  buscarIDUsuarioQueTieneElSensor(idSensor) {
    var textoSQL = "select * from UsuarioSensor where idSensor=$idSensor";
    var valoresParaSQL = {
      $idSensor: idSensor
    }
    return new Promise((resolver, rechazar) => {
      this.laConexion.all(textoSQL, valoresParaSQL,
        (err, res) => {
          (err ? rechazar(err) : resolver(res[0]))
        })
    })
  }

  // --------------------------------------------------------
  // --> idSensor:N
  // buscarSensor()
  // {idSensor: N, idTipoMedida: N}
  // --------------------------------------------------------
  buscarSensor(idSensor) {
    var textoSQL = "select * from Sensores where idSensor=$idSensor";
    var valoresParaSQL = {
      $idSensor: idSensor
    }
    return new Promise((resolver, rechazar) => {
      this.laConexion.all(textoSQL, valoresParaSQL,
        (err, res) => {
          if(err){
            rechazar(err)
          } if(res == undefined){
            resolver(undefined)
          }
          resolver(res[0])
        })
    })
  }

  // .................................................................
  // buscarRelacionesUsuarioSensor()
  // --> [{idSensor:N, idUsuario:N}]
  // .................................................................
  buscarRelacionesUsuarioSensor() {
    var textoSQL = "select * from UsuarioSensor";
    var valoresParaSQL = {}
    return new Promise((resolver, rechazar) => {
      this.laConexion.all(textoSQL, valoresParaSQL,
        (err, res) => {
          (err ? rechazar(err) : resolver(res))
        })
    })
  }

  // --------------------------------------------------------
  // --> nombre:Texto
  // buscarUsuarioAdmin()
  // {nombre:Texto, password:Texto}
  // --------------------------------------------------------
  buscarUsuarioAdmin(nombre) {
    var textoSQL = "select * from UsuariosAdmin where nombre=$nombre";
    var valoresParaSQL = {
      $nombre: nombre
    }
    return new Promise((resolver, rechazar) => {
      this.laConexion.all(textoSQL, valoresParaSQL,
        (err, res) => {
          (err ? rechazar(err) : resolver(res[0]))
        })
    })
  }

  // --------------------------------------------------------
  // {email:Texto, password:Texto}
  // iniciarSesion()
  // --> V/F
  // --------------------------------------------------------
  async iniciarSesion(datos) {

    var res = await this.buscarUsuarioPorEmail(datos.email);

    return new Promise((resolver, rechazar) => {

      try {
        if (sjcl.decrypt(datos.email, res.password) == datos.password) {
          resolver(true)
        } else {
          resolver(false)
        }
      } catch (error) {
        resolver(false)
      }

    })

  }

  // --------------------------------------------------------
  // {nombre:Texto, password:Texto}
  // iniciarSesion()
  // --> V/F
  // --------------------------------------------------------
  async iniciarSesionAdmin(datos) {

    var res = await this.buscarUsuarioAdmin(datos.nombre);

    return new Promise((resolver, rechazar) => {

      try {
        if (res.password == datos.password) {
          resolver(true)
        } else {
          resolver(false)
        }
      } catch (error) {
        resolver(false)
      }

    })

  }

  // .................................................................
  // getTodasLasMedidas()
  // --> [{{valorMedida:R, tiempo:N: latitud:R, longitud:R, idMedida:N, idUsuario:N, idTipoMedida:N}}]
  // .................................................................
  getTodasLasMedidas() {
    var textoSQL = "select * from Medidas";
    var valoresParaSQL = {}
    return new Promise((resolver, rechazar) => {
      this.laConexion.all(textoSQL, valoresParaSQL,
        (err, res) => {
          (err ? rechazar(err) : resolver(res))
        })
    })
  }

  // .................................................................
  // idTipoDeMedida:N -->
  // buscarUnTipoDeMedidas()
  // --> [{{valorMedida:R, tiempo:N: latitud:R, longitud:R, idMedida:N, idUsuario:N, idTipoMedida:N}}]
  // .................................................................
  buscarUnTipoDeMedidas(idTipoMedida) {
    var textoSQL = "select * from Medidas where idTipoMedida = $idTipoMedida";
    var valoresParaSQL = { $idTipoMedida:idTipoMedida }
    return new Promise((resolver, rechazar) => {
      this.laConexion.all(textoSQL, valoresParaSQL,
        (err, res) => {
          (err ? rechazar(err) : resolver(res))
        })
    })
  }

  // .................................................................
  // email:Texto -->
  // getTodasLasMedidasDeUnUsuarioPorEmail()
  // --> [{{valorMedida:R, tiempo:N: latitud:R, longitud:R, idMedida:N, idUsuario:N, idTipoMedida:N}}]
  // .................................................................
  async getTodasLasMedidasDeUnUsuarioPorEmail(email) {

    var usuario = await this.buscarUsuarioPorEmail(email)

    var textoSQL = "select * from Medidas where idUsuario=$idUsuario";

    var valoresParaSQL = {
      $idUsuario: usuario.idUsuario
    }
    return new Promise((resolver, rechazar) => {
      this.laConexion.all(textoSQL, valoresParaSQL,
        (err, res) => {
          (err ? rechazar(err) : resolver(res))
        })
    })
  }

  // .................................................................
  // cerrar() -->
  // .................................................................
  cerrar() {
    return new Promise((resolver, rechazar) => {
      this.laConexion.close((err) => {
        (err ? rechazar(err) : resolver())
      })
    })
  } // ()

} // class
// .....................................................................
// .....................................................................
