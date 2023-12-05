package componentes.componentesBFBC2;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import componentes.Hex;
import componentes.RSAmodificado;

import excepciones.ContraseñaInvalidaException;
import excepciones.DesincronizacionSecuencia;
import excepciones.HostInalcanzableException;

/**
 * ClienteTCP_BFBC2 Contiene todo el codigo necesario para la conexion/desconexión de la consola y envío/recepción segun el protocolo de BFBC2
 * @author Alatriste
 * @version 0.3
 */
public final class ClienteTCP_BFBC2{	
	
	/** El separador que me he inventado y me sirve para identificar las palabras de un comando*/
	public static final String SEP = "' '";
	/** Milisegundos a esperar entre el envio del comando y su recepción*/
	public static final int ESPERA = 500;		
	
	/** El logger de log4j*/
	private static final Logger log = Logger.getLogger(ClienteTCP_BFBC2.class);
	
	/** Cliente tcp para el patron singleton */
	private static ClienteTCP_BFBC2 clienteTCP_BFBC2 = null;
	
	/** La longitud máxima de los datos de los paquetes de BFBC2, que es de 4 bytes*/
	private static final int LONGITUDDATOS = 4096;
	/** El tiempo en segundos que indica cada cuanto contactara el hilo con el server para que no haya un logout*/
	private static final int TIEMPOHILO = 300;
	/** El charset utilizado*/
	private static final String CHARSET = "ISO-8859-1";
	/** El char nulo, que cierra todas las palabras **/
	private static final char NULLCHAR = '\0';
	/** Los bytes extras de longitud que tiene una palabra, que son su 4 bytes al principio indicando la longitud de la palabra + 1 byte con el NULLCHAR*/ 
	private static final int BYTESADICIONALESPALABRA = 5;
	/** El protocolo del red del BFBC2 contempla 3 bytes iniciales de control que tienen diferentes funciones. 
	    Esta variable delimita la longitud del primer byte*/
	private static final int PRIMERBYTE = 4;
	/** El protocolo del red del BFBC2 contempla 3 bytes iniciales de control que tienen diferentes funciones. 
    Esta variable delimita la longitud del segundo byte*/
	private static final int SEGUNDOBYTE = 8;
	/** El protocolo del red del BFBC2 contempla 3 bytes iniciales de control que tienen diferentes funciones. 
    Esta variable delimita la longitud del tercer byte*/
	private static final int TERCERBYTE = 12;	
	
	/** La IP a la que nos conectaremos*/
	private InetAddress ip = null;
	/** El socket con el que haremos la conexión */
	private Socket cliente;
	
	/** El Stream de entrada dado por el canal, en el que leeremos lo que nos responda el server. Nos hace falta nada mas, iremos leyendo
	 * y formateando directamente desde el array de bytes*/
	private InputStream canalentrada;
	/** El Stream de salida dado por el canal, en el escribiremos la salida*/
	private DataOutputStream canalsalida;	
	/** Stream temporal donde guardaré la codificación de las palabras del comando. Debido al formato del protocolo, tengo que escribir antes
	 * la longitud del mensaje total y el numero de palabas que las palabras en sí y eso no puedo saberlo de antemano. Así que guardo la codificación 
	 * aparte para incluirla después de esos dos parámetros*/
	private ByteArrayOutputStream palabrascodificadasbytes;
	/** Necesario para hacer las operaciones de escritura mas basicas, writeInt, etc*/
    private DataOutputStream palabrascodificadas;
	
	/** la secuencia de mensajes en la que vamos*/
	private static int secuencia = 0;
	/** La contraseña rcon descifrada, para logearnos contra el server*/
	private String passwordplana;
	/** El hilo que saca mensajes acojonatorios por pantalla*/
	private HiloMensajeBFBC2 hiloMensajeBFBC2;
		
	/**
	 * Constructor privado en blanco para la implementación del patrón singleton	
	 */
	private ClienteTCP_BFBC2(){}
	
	/**
	 * Metodo que permite recuperar la unica instancia que existe del ClienteTCP_BFBC2
	 * @return la instancia de clienteTCP_BFBC2 si existe, si no instanciamos uno
	 */
	public static ClienteTCP_BFBC2 getInstance() {
		if (clienteTCP_BFBC2 == null){
			clienteTCP_BFBC2 = new ClienteTCP_BFBC2();				
		}
		return clienteTCP_BFBC2;
	}
		
	/**
	 * Creamos la conexión con los datos introducidos. 
	 * @param host el host al que se conecta
	 * @param puerto el puerto al que se conecta
	 * @param password la password del servidor
	 * @throws UnknownHostException 
	 * @throws SocketException
	 * @throws IOException
	 * @throws HostInalcanzableException Esto es por si se han metido datos correctos sintacticamente hablando pero erroneos
	 * porque no corresponden con ningun servidor de bfbc2
	 */
	public void crearConexion(String host, int puerto, String password, int timeout, ResourceBundle i18n) 
	throws IOException, ContraseñaInvalidaException, DesincronizacionSecuencia, NoSuchAlgorithmException{
	
		ip = InetAddress.getByName(host);	
			
		cliente = new Socket(ip, puerto);
		cliente.setSoTimeout(timeout);	
		
		//recuperamos la password cifrada
		passwordplana = crearPassword(password);		
		
		//obtenemos la entrada y la salida de este socket, para escribir en ellas		
		canalentrada = cliente.getInputStream();
		canalsalida = new DataOutputStream(cliente.getOutputStream());	
		
		palabrascodificadasbytes = new ByteArrayOutputStream();
	    palabrascodificadas = new DataOutputStream(palabrascodificadasbytes);	  
		
		if(cliente.isBound() && cliente.isConnected() && !cliente.isClosed()){
			// Primer paso del login hashed, recibir el salt
			enviar("login.hashed");
			esperandoaRecibir(ESPERA);
			String respuesta = recibir();
			//partimos la respuesta, que es un Ok y el salt y se lo pasamos al generador para que nos de la hashed que volvemos a enviar al server
			String[] listaRespuesta = respuesta.split("\" \"");		
						
			enviar("login.hashed" + SEP + generarHashPassword(listaRespuesta[1], passwordplana));
			esperandoaRecibir(ESPERA);
			respuesta = recibir();
			
			if(respuesta.contains("InvalidPasswordHash") || respuesta.contains("PasswordNotSet"))
				throw new ContraseñaInvalidaException();			
			
			hiloMensajeBFBC2 = new HiloMensajeBFBC2("HiloMensajeBFBC2", true, TIEMPOHILO, i18n);
			hiloMensajeBFBC2.start();
		}
	}
	
	/**
	 * Metodo que construye el paquete con la rcon y la orden correspondiente y lo manda
	 * @param texto La orden correspondiente
	 * @throws IOException Si no llega por algún motivo
	 */
	public synchronized void enviar (String palabras) throws IOException{		
		
		if(cliente.isConnected()){
			codificarmensaje(false, false, palabras);
			secuencia = (secuencia + 1) & 0x3fffffff;
		}
		else{
			if(log.isDebugEnabled())
				log.debug("No se ha podido enviar el mensaje porque el cliente no esta conectado");			
		}
	}
	
	/** Cuando el server esta muy petado, tarda en responder. Espera artificial para que no se pierda información*/
	public synchronized void esperandoaRecibir(int espera){
		try {
			Thread.sleep(espera);
		} catch (InterruptedException e) {
			log.error(e);
		}
	}
	
	/**
	 * Metodo que recoge todo lo que el servidor nos devuelve y lo convierte a un String	
	 */
	public synchronized String recibir () throws IOException, DesincronizacionSecuencia{
		
		byte[] datosLeidos = new byte[LONGITUDDATOS];
		//Array de bytes vacío para ir encadenando todo lo que leamos hasta formar un paquete completo
		byte[] datosTotales = new byte[0];
		String texto_mensaje = null;
		int bytesleidos;
		int bytesdelpaquete;
		
		if (cliente.isConnected()){
			do {
				//Intentamos leer el máximo de bytes y guardamos la longitud del paquete para saber si tenemos que leer mas o no
				bytesleidos = canalentrada.read(datosLeidos, 0, LONGITUDDATOS);
				bytesdelpaquete = construirInt(Arrays.copyOfRange(datosLeidos, PRIMERBYTE, SEGUNDOBYTE));
				//Guardamos en un unico array de bytes todo lo leido, lo concatenamos con lo anterior para que no se pierda nada
				datosTotales = concatenar2Arraysdebytes(datosTotales, datosLeidos);
				//si hemos leido menos de los que dice el paquete que tiene, hay que leer mas
			} while (bytesleidos < bytesdelpaquete);	
			
			texto_mensaje = decodificarmensaje(datosTotales);	
			if(log.isDebugEnabled())
				log.debug("DECODIFICADO  "+ texto_mensaje);			
		}	
		else{
			if(log.isDebugEnabled())
				log.debug("No se ha podido enviar el mensaje porque el cliente no esta conectado");			
		}
		
		datosLeidos = null;
		datosTotales = null;
		return texto_mensaje;
	}	
	
	/**
	 * Paramos el hilo si esta arrancado y cerramos el socket	
	 */
	public void cerrarConexion() throws InterruptedException, IOException, DesincronizacionSecuencia{
		
		//La comprobacion de null pa cuando se sale sin haber conectado a CEALCON
		if(cliente != null){
			
			enviar("logout");
			esperandoaRecibir(ESPERA);
			String respuesta = recibir();
			
			if(log.isDebugEnabled())
				log.debug("Cierre de aplicacion "+ respuesta);
			
			//Puede que el usuario no haya llegado a activarlo y no haya hilo ejecutandose
			if(hiloMensajeBFBC2 != null && hiloMensajeBFBC2.isAlive()){				
				if(log.isDebugEnabled())
					log.debug("amos a parar el hilo");
				hiloMensajeBFBC2.setContinuar(false);
				hiloMensajeBFBC2.interrupt();
				hiloMensajeBFBC2.join(150);
			}	
			palabrascodificadasbytes.close();
			palabrascodificadas.close();
			canalentrada.close();
			canalsalida.close();
			cliente.close();		
		}		
	}
	
	/**
	 * Metodo que desencripta la contraseña y la crea para que el programa la use
	 * @param password La contraseña cifrada	 
	 */
	private String crearPassword(String passwordCifrada){
				
		return RSAmodificado.getInstance().decryptString(passwordCifrada);	
	}
	
	private void codificarmensaje(boolean isDelServer, boolean isRespuesta, String palabras) throws IOException{
		
		int longitudmensaje = 0;	      
		
	    //Extraemos las palabras del comando
		String[] palabrasacodificar = palabras.split(SEP);		
		
		//Int32 de la cabecera		
		canalsalida.writeInt(littleEndianint(codificarHeader(isDelServer, isRespuesta)));		
		
		// Int32 de la longitud del paquete, que es la longitud de las palabras mas 12, que son los bytes de la cabecera,
		// los de la longitud del paquete y el numero de palabras
		longitudmensaje = (codificarPalabras(palabrasacodificar)) + TERCERBYTE;
		
		canalsalida.writeInt(littleEndianint(longitudmensaje));	
		
		// Int32 del numero de palabras		
		canalsalida.writeInt(littleEndianint(palabrasacodificar.length));		
		
		// Resto del mensaje, las palabras codificadas propiamente dichas
		canalsalida.write(palabrascodificadasbytes.toByteArray());		
		canalsalida.flush();
		
		//Hacemos reset para reutilizar la memoria de los streams en el siguiente mensaje
		palabrasacodificar = null;
		palabrascodificadasbytes.reset();		
	}
	
	private int codificarHeader(boolean isDelServer, boolean isRespuesta){
		//Hacemos el & a nivel de bit para dejarlos todos iguales menos los dos primeros, que se ponen a cero
		int cabecera = secuencia & 0x3fffffff;
		//Segun el protocolo de comunicación, si es del server el primer bit va a 1 y si es respuesta es el segundo bit el que va a uno
		if(isDelServer)
			cabecera += 0x80000000;
		if(isRespuesta)
			cabecera += 0x40000000;		
		return cabecera;
	}
	
	private int codificarPalabras(String[] palabras) throws IOException{		
		
		//El size del stream no nos vale porque se acumula segun se escribe en él, así que hay que contar la longitud a pelo
		int longitudpalabras = 0;
		
	    for(int i=0; i<palabras.length; i++){			
			palabrascodificadas.writeInt(littleEndianint(palabras[i].getBytes(CHARSET).length));			
			palabrascodificadas.write(palabras[i].getBytes(CHARSET));	
			palabrascodificadas.write(charTo1Byte(NULLCHAR));		
			longitudpalabras += palabras[i].getBytes(CHARSET).length + BYTESADICIONALESPALABRA;
		}		
		palabrascodificadas.flush();		
		return longitudpalabras;
	}
	
	/**
	 * Java por defecto escribe un char como 2 bytes. No me vale eso, el server espera 1 char = 1 byte
	 * La forma eficiente de hacerlo es como esta hecha la palabra, es decir obtienes los bytes de la palabra con el charset correspondiente, pero
	 * aqui esta hecho solo para el char nulo que cierra la palabra y es mas costoso que hacerlo string y luego llamar a getBytes.
	 * Además, así queda aquí expuesta la explicación para el futuro, cuando no me acuerde de como lo hice.
	 * @param x el char nulo a tranformar en byte
	 * @return el byte del char nulo
	 * @throws UnsupportedEncodingException
	 */
	private byte[] charTo1Byte(final char x) throws UnsupportedEncodingException{
		String temp = new String(new char[] {x});
		
		return temp.getBytes(CHARSET);		
	}
	
	/**
	 * Java por defecto trabaja con bytes en bigEndian, es decir, el valor más alto del byte al principio en vez de al final. El server de BFBC2
	 * los espera en littleEndian, es decir el valor más alto del byte al final. Este es el metodo que pasa de uno a otro formato.
	 * @param bigEndian el byte en bigEndian a convertir
	 * @return el int en littleEndian
	 */
	private int littleEndianint(int bigEndian){
		
		ByteBuffer bb = ByteBuffer.allocate(PRIMERBYTE);	
		
	    bb.putInt(bigEndian);
	    bb.order(ByteOrder.LITTLE_ENDIAN);
	    /*Al hacer el put el cursor suma 4 para meter el siguiente, al no haber mas espacio (4 bytes) se detiene al final de buffer.
		 Si se hace simplemente un getInt, como esta al final casca y no se recupera una mierda, por eso hay que hacer el get desde el cero */
		return bb.getInt(0);	
	}
	
	private String decodificarmensaje(byte[] entradabytes) throws DesincronizacionSecuencia, IOException{
		
		int[] triocabecera;			
		int tamañototalpaquete;
		int numeropalabras;
		
		//Copiamos los primeros 4 bytes del array de entrada y construimos el int inicial, que es la cabecera, luego lo decodificamos
		triocabecera = decodificarHeader(construirInt(Arrays.copyOfRange(entradabytes, 0, PRIMERBYTE)));
		
		if(log.isDebugEnabled()){
			if(triocabecera[0] == 1)
				log.debug(("ISFROMSERVER"+ " "));						
			else
				log.debug("ISFROMCLIENT"+ " ");					
			if(triocabecera[0] == 1)
				log.debug("RESPONSE"+ " ");			
			else
				log.debug("REQUEST"+ " ");	
		}
		
		/*Le restamos uno a la secuencia puesto que al mandar el paquete se lo hemos sumado para el siguiente, y ahora al llegarnos la respuesta
		del primer paquete pues hay que restarle uno a la secuencia porque sino, no coincidirá*/
		if(triocabecera[2] != (secuencia - 1)){
			throw new DesincronizacionSecuencia("CAGADA "+ triocabecera[2] + " != " + (secuencia - 1));
		}
		
		//Tamaño del paquete en bytes
		tamañototalpaquete = construirInt(Arrays.copyOfRange(entradabytes, PRIMERBYTE, SEGUNDOBYTE));
		
		//Numero de palabras que tiene la respuesta
		numeropalabras = construirInt(Arrays.copyOfRange(entradabytes, SEGUNDOBYTE, TERCERBYTE));
		if(log.isDebugEnabled()){
			log.debug("Secuencia " + (secuencia - 1));
			log.debug("Numero de palabras " + String.valueOf(numeropalabras));
			log.debug("Bytes totales del paquete " + tamañototalpaquete);
		}
		
		return decodificarpalabras(Arrays.copyOfRange(entradabytes, TERCERBYTE, tamañototalpaquete), numeropalabras);
	}
	
	private int[] decodificarHeader(int cabecera){
		return new int[] {cabecera & 0x80000000,  cabecera & 0x40000000, cabecera & 0x3fffffff};		
	}
	
	private String decodificarpalabras(byte[] palabras, int numeropalabras) throws UnsupportedEncodingException{
		
		String palabrasdecodificadas = new String();
		//Para saber por donde vamos en el array de bytes palabras
		int contadordebytes = 0;
		int longitudpalabra = 0;
		
		for(int i=0; i<numeropalabras; i++){
			//Leemos la longitud de la palabra
			longitudpalabra = construirInt(Arrays.copyOfRange(palabras, contadordebytes, contadordebytes + PRIMERBYTE));
			//Avanzamos el contador 4 bytes, ya que la longitud es un int32
			contadordebytes += PRIMERBYTE;
			/*Creamos la palabra desde el array de bytes palabras, con el CHARSET definido. Los chars que forman la palabra se encuentran entre
			 el contador actual y la longitud de la palabra. Añadimos un espacio con comillas al final para facilitar su posterior split*/
			palabrasdecodificadas = palabrasdecodificadas.concat(new String(Arrays.copyOfRange(palabras, contadordebytes, contadordebytes + longitudpalabra), CHARSET) + "\" \"");
			//avanzamos el contador la longitud de la palabra mas uno, que es el char nulo que cierra todas las palabras
			contadordebytes += longitudpalabra + 1;
		}		
		return palabrasdecodificadas;
	}
	
	/**
	 * Java por defecto trabaja con bytes en bigEndian, es decir, el valor más alto del byte al principio en vez de al final.
	 * El server de BFBC2 los manda en littleEndian, es decir el valor más alto del byte al final. Por esta razón no puedo usar un DataInputStream
	 * para leer los ints, ya que el readInt() lo espera como un bigendian	
	 * @param intBytes el byte en littleEndian a convertir
	 * @return el int reconstruido
	 */	
	private int construirInt (byte[] intBytes){		
		return ByteBuffer.wrap(intBytes).order(ByteOrder.LITTLE_ENDIAN).getInt();
	}
	
	/**
	 * El server manda una hash hexadecimal para que la contraseña no pueda ser snifada. Hay que decodificarla a un array de bytes y luego updatear
	 * el messagedigest con la contraseña de rcon. Lo que resulta de eso hay que computarlo en un string hexadecimal para enviarlo de nuevo al server
	 * y que nos de acceso
	 * @param salt lo enviado por el server
	 * @param password la contraseña rcon
	 * @return la salt computada con la password, en un string hexadecimal y en mayusculas
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 */
	private String generarHashPassword(String salt, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException{
		 
        MessageDigest m = null;
        String passwordComputada;
		
		m = MessageDigest.getInstance("MD5");
		m.update(Hex.decodeHex(salt.toCharArray()));
        m.update(password.getBytes(CHARSET));
        passwordComputada = new String(Hex.encodeHex(m.digest()));
        //Hay que mandarselo en mayusculas, que si no, no te lo acepta
        return passwordComputada.toUpperCase();				
	}	

	/**
	 * Concatena dos array de bytes, lo usamos para unir toda la respuesta del servidor en un único array
	 * @param first
	 * @param second
	 * @return el array resultante de los dos
	 */
	private byte[] concatenar2Arraysdebytes(byte[] first, byte[] second) {
		byte[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}
}