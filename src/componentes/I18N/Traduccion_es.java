package componentes.I18N;

import java.util.ListResourceBundle;

public final class Traduccion_es extends ListResourceBundle{

	private static final Object[][] clave_traduccion = {
		
		//Mediador
		
		//Mediador.comandoGuardarServer
        {"mcg1", "Datos guardados correctamente.\n"},  
        {"mcg2", "Guardando datos..."},
        {"mcg3", "Imposible guardar los datos.\n"},                    
        {"mcg4", "Error creando fichero"},
        {"mcg5", "Ya hay un servidor guardado con este nombre.\n"},                    
        {"mcg6", "¿Desea sobreescribir los cambios?"},
        {"mcg7", "Nombre duplicado"},
        
        //Mediador.comandoCifrarRCON
        {"mccr1", "No se puede cifrar una RCON en blanco.\n"},  
        {"mccr2", "Modifique la RCON de su servidor."},                              
        {"mccr3", "RCON en blanco"},
        
        {"mccr4", "La siguiente RCON cifrada:\n\n"},  
        {"mccr5", "ha sido copiada al portapapeles.\n\n"},                              
        {"mccr6", "Distribúyala entre los miembros de su clan y/o\n"},
        {"mccr7", "péguela mediante CONTROL+V  directamente en el menu de conexión\n"},  
        {"mccr8", "para acceder inmediatamente a su servidor."},                              
        {"mccr9", "RCON cifrada"},
        
        //Mediador.comandoConectar
        {"mcc1", "Imposible conectar [CEAL]CON. Host Desconocido.\n"},  
        {"mcc2", "Asegurese de que los datos son correctos y vuelva a intentarlo.\n"},                              
        {"mcc3", "Error creando la conexión"},  
        
        {"mcc4", "Imposible conectar [CEAL]CON.\n"},                      
        {"mcc5", "Asegúrese de que la ip, el puerto y contraseña introducidos están abiertos,\n"},                    
        {"mcc6", "corresponden con un servidor activo y vuelva a intentarlo.\n"},
        {"mcc7", "Error creando la conexión"},
        
        {"mcc8", "Imposible conectar [CEAL]CON.\n"},                              
        {"mcc9", "Asegúrese de que la contraseña cifrada introducida \n"},                       
        {"mcc10", "es correcta. Si se ha asegurado, probablemente\n"},                      
        {"mcc11", "la contraseña haya cambiado. Pregunte al administrador."},                    
        {"mcc12", "Contraseña incorrecta"},
        
        //Mediador.comandoAcercade
        {"mcad1", "http://www.cealweb.net"},  
        {"mcad2", "El explorador de internet no ha podido ser abierto.\n"},                              
        {"mcad3", "Para visitar nuestra pagina dirijase a http://www.cealweb.net"},
        {"mcad4", "Error abriendo el explorador"},
        
        //Mediador.comandoCerrar
        {"mcce1", "Esto cerrará [CEAL]CON.\n"},  
        {"mcce2", "¿Desea continuar?\n"},                              
        {"mcce3", "Cerrando..."},
        
        //Mediador.cargarDatosLogyLocale
        {"mcdlyl1", "Imposible abrir los archivos de configuración de [CEAL]CON\n"},  
        {"mcdlyl2", "Posiblemente la carpeta bin haya sido modificada, dañada o no exista. Restáurela.\n"},                              
        {"mcdlyl3", "Ahora se cargarán los valores por defecto."},        
        {"mcdlyl4", "Error abriendo el archivo"},       
        
        {"mcdlyl5", "Imposible manipular los archivos.\n"},                    
        {"mcdlyl6", "Posiblemente la carpeta bin haya sido modificada o dañada. Restáurela.\n"},
        {"mcdlyl7", "Ahora se cargarán los valores por defecto."},  
        {"mcdlyl8", "Error manipulando archivos"},  
        
        //Mediador.validacionCamposDialogoConexion
        {"mvcdc1", "Rellene los campos vacíos.\n"},  
        {"mvcdc2", "Rellene los campos"},                              
        {"mvcdc3", "Tiene que elegir que un juego al que conectarse.\n"},                       
        {"mvcdc4", "Seleccione un juego"},                      
        {"mvcdc5", "El puerto debe ser un número.\n"},                    
        {"mvcdc6", "Puerto imposible"},
        {"mvcdc7", "El puerto no puede exceder de 65535\n"},                    
        {"mvcdc8", "Puerto imposible"},
        
        //Mediador.chequear_tablaactualizada_jugadorseleccionado
        {"mctaj1", "La tabla debe estar actualizada para seleccionar a un jugador.\n"},
        {"mctaj2", "Haga un refresco."},
        {"mctaj3", "Haga un refresco"},
        {"mctaj4", "Debe seleccionar a un jugador.\n"},                      
        {"mctaj5", "Seleccione jugador"},
        
        //Mediador.noRespondeServidor
        {"mnrs1", "El servidor no ha respondido. \n"},
        {"mnrs2", "Esto puede ser debido a que algún paquete con información se \n"},
        {"mnrs3", "haya perdido, cosa que sucede al cambiar un mapa, por ejemplo.\n"},
        {"mnrs4", "Otras razones son que la conexión esté fallando \n"},
        {"mnrs5", "o a que la acción realizada no haya finalizado correctamente.\n"},                    
        {"mnrs6", "Asegúrese que la conexión con el servidor sigue activa y\n"},
        {"mnrs7", "vuelva a intentarlo pasados unos segundos."},  
        {"mnrs8", "No responde el servidor"},
        
        //Mediador.desincronizacionSecuencia
        {"mds1", "Error en [CEAL]CON.\n"},
        {"mds2", "Se ha producido una desincronización entre el servidor y [CEAL]CON.\n"},
        {"mds3", "Quizás la aplicación no responda o muestre información errónea.\n"},
        {"mds4", "Si este mensaje aparece repetidas veces, reiniciela y vuelva a intentarlo.\n"},                      
        {"mds5", "Error de sincronización"},
        
        //Mediador.comandoBorrarServer
        {"mcbs1", "Configuración de server borrada correctamente"},
        {"mcbs2", "Archivo borrado"},
        {"mcbs3", "No ha podido borrarse la configuración seleccionada."},
        {"mcbs4", "Archivo NO borrado"},
        
        {"mcbs5", "Elija un servidor para que sus datos puedan ser borrados.\n"},  
        {"mcbs6", "Elija servidor"},  
        
        //Mediador.comandoCargarServer
        {"mccs1", "Imposible abrir el archivo con los datos del servidor.\n"},  
        {"mccs2", "Posiblemente la carpeta bin haya sido modificada, dañada o no exista. Restáurela.\n"},                              
        {"mccs3", "No se cargará ningún valor."},        
        {"mccs4", "Error abriendo el archivo"},       
        
        {"mccs5", "Imposible manipular los archivos.\n"},                    
        {"mccs6", "Posiblemente la carpeta bin haya sido modificada o dañada. Restáurela.\n"},
        {"mccs7", "Los datos que ve probablemente sean incorrectos, revíselos."},  
        {"mccs8", "Error manipulando archivos"},  
        
        {"mccs9", "El juego del server almacenado no era correcto.\n"},                       
        {"mccs10", "Probablemente se haya corrompido o haya sido modificado\n"},                      
        {"mccs11", "a mano en los ficheros de configuración.\n"},                    
        {"mccs12", "Seleccionelo correctamente y guarde la configuración."},
        {"mccs13", "Juego imposible"}, 
        
        {"mccs14", "Elija un servidor para que sus datos puedan ser cargados.\n"},  
        {"mccs15", "Elija servidor"},  
        
        //Mediador.comandoGuardarConfiguracion
        {"mcgyc1", "Datos guardados correctamente.\n"},  
        {"mcgyc2", "Los cambios tendrán efecto al reiniciar la aplicación"},                              
        {"mcgyc3", "Cambio en la configuración"},        
        
        {"mcgyc4", "Imposible abrir los archivos de configuración de [CEAL]CON\n"},  
        {"mcgyc5", "Posiblemente la carpeta bin haya sido modificada, dañada o no exista. Restáurela.\n"},                              
        {"mcgyc6", "Ahora se cargarán los valores por defecto."},        
        {"mcgyc7", "Error abriendo el archivo"},       
        
        {"mcgyc8", "Imposible manipular los archivos.\n"},                    
        {"mcgyc9", "Posiblemente la carpeta bin haya sido modificada o dañada. Restáurela.\n"},
        {"mcgyc10", "Ahora se cargarán los valores por defecto."},  
        {"mcgyc11", "Error manipulando archivos"},
        
        //Mediador.comandoMenuAyuda
        {"mcma1", "La ayuda no ha podido ser abierta.\n"},  
        {"mcma2", "Error abriendo navegador del sistema"},  
        
        //MediadorBFBC2
        
        //MediadorBFBC2.comandoBFBC2Move
        {"mbfbc2cm1", "Jugador "},
        {"mbfbc2cm2", " movido correctamente\n al equipo: "},
        {"mbfbc2cm3", " patrulla: "},
        {"mbfbc2cm4", "Jugador movido"},
        
        {"mbfbc2cm5", "El servidor no ha devuelto lo que se esperaba.\n"},
        {"mbfbc2cm6", "Refresque, compruebe, e inténtelo de nuevo."},
        {"mbfbc2cm7", "Error en el comando"},
        
        {"mbfbc2cm8", "El equipo "},
        {"mbfbc2cm9", " no es válido."},
        {"mbfbc2cm10", "InvalidTeamId"},
        
        {"mbfbc2cm11", "La patrulla "},
        {"mbfbc2cm12", " no es válida."},
        {"mbfbc2cm13", "InvalidSquadId"},
        
        {"mbfbc2cm14", "El jugador "},
        {"mbfbc2cm15", " no está muerto.\n Seleccione Forzar muerte e inténtelo de nuevo."},
        {"mbfbc2cm16", "PlayerNotDead"},
        
        {"mbfbc2cm17", "No se ha podido asignar el jugador "},
        {"mbfbc2cm18", "\n al equipo "},
        {"mbfbc2cm19", "SetTeamFailed"},
        
        {"mbfbc2cm20", "No se ha podido asignar el jugador "},
        {"mbfbc2cm21", "\n a la patrulla "},
        {"mbfbc2cm22", "SetSquadFailed"},
        
        {"mbfbc2cm23", "Tiene que seleccionar un equipo Y\n"},
        {"mbfbc2cm24", "una patrulla para mover al jugador."},
        {"mbfbc2cm25", "Faltan datos"},
        
        //MediadorBFBC2.comandoBFBC2Kick
        {"mbfbc2ck1", "Jugador expulsado correctamente.\n"},
        {"mbfbc2ck2", "Jugador expulsado"}, 
        
        {"mbfbc2ck3", "La plaza está vacía y no puede expulsarse a nadie.\n"},
        {"mbfbc2ck4", "Posiblemente el jugador la haya abandonado\n"},
        {"mbfbc2ck5", "después de hacer el refresco.\n"},
        {"mbfbc2ck6", "Refresque e inténtelo de nuevo."},                      
        {"mbfbc2ck7", "No se ha expulsado al jugador"},
        
        {"mbfbc2ck8", "El servidor no ha devuelto lo que se esperaba.\n"},
        {"mbfbc2ck9", "Refresque, compruebe, e inténtelo de nuevo."},
        {"mbfbc2ck10", "Error en el comando"},
        
        {"mbfbc2ck11", "El mensaje a radiar no debe de exceder de los "},
        {"mbfbc2ck12", " caracteres.\n"}, 
        {"mbfbc2ck13", "Razon de la expulsión"},        
        
        {"mbfbc2ck14", "Debe introducir una razón para explusar a un jugador.\n"},
        {"mbfbc2ck15", "Razón de la expulsión"}, 
        
        //MediadorBFBC2.comandoBFBC2BaneoTemporal
        {"mbfbc2bt1", "Jugador baneado correctamente durante "},
        {"mbfbc2bt2", " minutos."},
        {"mbfbc2bt3", "Jugador baneado"},
        
        {"mbfbc2bt4", "La lista de baneo está llena y el jugador no puede banearse.\n"},
        {"mbfbc2bt5", "Pida al administrador que vacíe la lista\n"},
        {"mbfbc2bt6", "e inténtelo de nuevo.\n"},
        {"mbfbc2bt7", "Full Ban List"},
        
        {"mbfbc2bt8", "El servidor no ha devuelto lo que se esperaba.\n"},
        {"mbfbc2bt9", "Refresque, compruebe, e inténtelo de nuevo."},
        {"mbfbc2bt10", "Error en el comando"},
        
        {"mbfbc2bt11", "El mensaje a radiar no debe de exceder de los "},
        {"mbfbc2bt12", " caracteres.\n"},
        {"mbfbc2bt13", "Razón del baneo"},
        
        {"mbfbc2bt14", "Hay que escribir una razón para la expulsión Y\n"},
        {"mbfbc2bt15", "seleccionar un tiempo de baneo."},
        {"mbfbc2bt16", "Faltan datos"},
        
        //MediadorBFBC2.comandoBFBC2ChatyChatPrivado
        {"mbfbc2ccp1", "El mensaje a radiar no debe de exceder de los "},
        {"mbfbc2ccp2", " caracteres.\n"},
        {"mbfbc2ccp3", "Hablar en el chat"},
        
        {"mbfbc2ccp4", "Debe introducir un texto para mostrar en el chat.\n"},
        {"mbfbc2ccp5", "Hablar en el chat"},
        
        //MediadorBFBC2.comandoBFBC2YellyYellPrivado
        {"mbfbc2cyyp1", "El mensaje a radiar no debe de exceder de los "},
        {"mbfbc2cyyp2", " caracteres.\n"},
        {"mbfbc2cyyp3", "Gritando"},
        
        {"mbfbc2cyyp4", "Hay que escribir una frase para gritar Y\n"},
        {"mbfbc2cyyp5", "seleccionar el tiempo a gritarla."},
        {"mbfbc2cyyp6", "Faltan datos"},    
        
        //MediadorBFBC2.comandoBFBC2NextMap
        {"mbfbc2cnm1", "El server está cambiando al siguiente mapa."},  
        {"mbfbc2cnm2", "Siguiente mapa"},
        
        //MediadorBFBC2.comandoBFBC2RestartMap
        {"mbfbc2crm1", "El server está reiniciando mapa."},  
        {"mbfbc2crm2", "Reiniciando mapa"},
        
        //MediadorCODWW
        
        //MediadorCODWW.comandoCODWWChat
        {"mcodwwcc1", "Debe introducir un texto para mostrar en el chat.\n"},
        {"mcodwwcc2", "Hablar en el chat"},
        
        //MediadorCODWW.comandoCODWWChatPrivado
        {"mcodwwccp1", "Debe introducir un texto para mostrar en el chat.\n"},
        {"mcodwwccp2", "Hablar en el chat"},
        {"mcodwwccp3", " ^1 MENSAJE PRIVADO: ^7"},
        
        //MediadorCODWW.comandoCODWWNextMap
        {"mcodwwcnm1", "El server está cambiando al siguiente mapa."},  
        {"mcodwwcnm2", "Siguiente mapa"},
        
        //MediadorCODWW.comandoCODWWFastRestart
        {"mcodwwcrm1", "El server está reiniciando mapa."},  
        {"mcodwwcrm2", "Reiniciando mapa"},
        
        //MediadorCODWW.kickear_BanearTemporalmente_Jugador
        {"mcodwwkbtj1", "^7: Expulsando a "},
        {"mcodwwkbtj2", " Razon: "},
        
        {"mcodwwkbtj3", "Jugador expulsado correctamente.\n"},
        {"mcodwwkbtj4", "Jugador expulsado"},
        
        {"mcodwwkbtj5", "La plaza está vacía y no puede expulsarse a nadie.\n"},
        {"mcodwwkbtj6", "Posiblemente el jugador la haya abandonado\n"},
        {"mcodwwkbtj7", "después de hacer el refresco.\n"},
        {"mcodwwkbtj8", "Refresque e inténtelo de nuevo."},
        {"mcodwwkbtj9", "No se ha expulsado al jugador"},   
        
        {"mcodwwkbtj10", "La plaza seleccionada no existe.\n"},
        {"mcodwwkbtj11", "Probablemente haya un fallo en la generación de la tabla.\n"},
        {"mcodwwkbtj12", "Refresque e inténtelo de nuevo."},
        {"mcodwwkbtj13", "No se ha expulsado al jugador"},
        
        {"mcodwwkbtj14", "El servidor no ha devuelto lo que se esperaba.\n"},
        {"mcodwwkbtj15", "Es posible que el jugador haya sido expulsado,\n"},
        {"mcodwwkbtj16", "pero no puede garantizarse.\n"},
        {"mcodwwkbtj17", "Refresque, compruebe, e inténtelo de nuevo."},
        {"mcodwwkbtj18", "Error en el comando"},  
        
        {"mcodwwkbtj19", "Debe introducir una razón para expulsar a un jugador.\n"},
        {"mcodwwkbtj20", "Razón Explusión"},
        
        //JFrameCEALCON
        
        {"jf1", "[CEAL]CON 0.3"},
        {"jf2", "Archivo"},
        {"jf3", "Acerca de"},
        {"jf4", "Conectar [CEAL]CON"},
        {"jf5", "Configuración"},
        {"jf6", "Cifrar RCON"},        
        {"jf8", "Acerca de [CEAL]CON"},
        
        //PanelBFBC2
        
        //PanelBFBC2.construirPanelNorte
        {"pbfbc2cpn1", "Cambiar jugador"},
        {"pbfbc2cpn2", "TeamId"},
        {"pbfbc2cpn3", "SquadId"},
        {"pbfbc2cpn4", "Forzar muerte"},
        {"pbfbc2cpn5", "Jugadores:"},
        {"pbfbc2cpn6", "Atacantes(1):"},
        {"pbfbc2cpn7", "Defensores(2):"},
        {"pbfbc2cpn8", "Reiniciar Mapa"},
        {"pbfbc2cpn9", "Siguiente Mapa"},
        
        //PanelBFBC2.construirRefrescoyKick
        {"pbfbc2crk1", "REFRESCO"},
        {"pbfbc2crk2", "Kickear"},
        {"pbfbc2crk3", "Banear Temporalmente"},
        {"pbfbc2crk4", "Minutos"},
        {"pbfbc2crk5", "Acciones"},
        {"pbfbc2crk6", "Click aqui para obtener la informacion de los jugadores"},
        
        //PanelBFBC2.construirChat
        {"pbfbc2cc1", "Decir"},
        {"pbfbc2cc2", "Decir privado"},
        {"pbfbc2cc3", "¡GRITAR!"},
        {"pbfbc2cc4", "¡GRITAR PRIVADO!"},
        {"pbfbc2cc5", "Segundos"},
        {"pbfbc2cc6", "Chat"},
        
        //PanelCODWW
        
        //PanelCODWW.construirPanelNorte
        {"pcodwwcpn1", "Fast_restart"},
        {"pcodwwcpn2", "Siguiente Mapa"},
        
        //PanelCODWW.construirRefrescoyKick
        {"pcodwwcrk1", "REFRESCO"},
        {"pcodwwcrk2", "Kickear"},
        {"pcodwwcrk3", "Banear Temporalmente"},
        {"pcodwwcrk4", "Acciones"},
        
        //PanelCODWW.construirChat
        {"pcodwwcc1", "Decir en privado"},
        {"pcodwwcc2", "Decir"},
        {"pcodwwcc3", "Chat"},
        
        //HiloMensajeBFBC2
        
        {"hmbfbc21", "[CEAL]CON 0.3 vigilando..."},
        {"hmbfbc22", "Consola de acceso remoto programada por el clan [CEAL] http://www.cealweb.net"},
        
        //HiloMensajeCODWW
        
        {"hmcodww1", "^1[C^3EA^1L]^7CON ^10.3 vigilando..."},
        {"hmcodww2", "^1Consola de acceso remoto programada por el clan [C^3EA^1L] http://www.cealweb.net"},        
                
        //DialogoConexion
        
        {"dc1", "Conexión"},
        {"dc2", "Nick"},
        {"dc3", "IP"},
        {"dc4", "Puerto"},
        {"dc5", "RCON cifrada"},
        {"dc6", "Elija un juego"},
        {"dc7", "Conectar"},
        {"dc8", "Guardar Datos Server"},
        {"dc9", "Nombre Servidor"},
        {"dc10", "Cargar Datos Server"},
        {"dc11", "Borrar Datos Server"},
        {"dc12", "Configuración de servers guardadas:"},
        {"dc13", "El nombre introducido aqui se verá en el server en los distintos comandos que ejecutes"},
        {"dc14", "Con este nombre se guardarán lo datos del servidor, si pulsas en Guardar Datos Server"},
        {"dc15", "Introduce la RCON cifrada que te ha dado el administrador de tu clan"},       
        
        //DialogoConfiguracion
        
        {"dcf1", "Configuración"},
        {"dcf2", "Nivel de log"},
        {"dcf3", "Idioma"},
        {"dcf4", "Guardar"},   
        {"dcf5", "Cancelar"},   
        
        //DialogoCifrarRCON
        
        {"dcrcon1", "Cifrar RCON"},
        {"dcrcon2", "RCON"},
        {"dcrcon3", "Cifrar RCON"},
        
        //DialogoAcercade
        
        {"da1", "Acerca de"},
        {"da2", "[CEAL]CON 0.3\n"},
        {"da3", "Monitorizador Avanzado De Rcon Externo."},
        {"da4", "Implementación concreta para Call Of Duty World at War y BFBC2"},
        {"da5", "Visita nuestra web pinchando en la imagen:"}        
    };
	
	@Override
	public Object[][] getContents(){
		return clave_traduccion;
	}
}
