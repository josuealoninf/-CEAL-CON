package componentes.componentesCODWW;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import componentes.RSAmodificado;

import excepciones.ContraseñaInvalidaException;
import excepciones.HostInalcanzableException;

/**
 * ClienteUDPCODWW
 * @author Alatriste
 * @version 0.2
 */
public final class ClienteUDPCODWW{	

	/** El logger de log4j*/
	private static Logger log = Logger.getLogger(ClienteUDPCODWW.class);
	
	/** Cliente udp para el patron singleton */
	private static ClienteUDPCODWW clienteUDPCODWW = null;	
	
	/** La longitud máxima de los datos de los paquetes de CODWW, lo pondremos asi para que
	 * no se llene nunca, los paquetes máximos observados han sido de 1167 bytes*/
	private static final int LONGITUDDATOS = 1500;
	/** El tiempo en segundos que indica cada cuanto se vera nuestro mensaje promocional en el server*/
	private static final int TIEMPOHILO = 3600;
	
	/** La IP a la que nos conectaremos*/
	private InetAddress ip = null;
	/** El socket con el que haremos la conexión */
	private DatagramSocket cliente;
	/** La rcon del servidor*/
	private String rconPassword = "";	
	/** El puerto al que nos conectamos */
	private int puerto;
	/** La longitud que tienen los datos que recibimos en el paquete del servidor*/
	private int tamañoDatosRecibidos;
	
	private HiloMensajeCODWW hiloMensajeCODWW;
		
	/**
	 * Constructor privado en blanco para la implementación del patrón singleton	
	 */
	private ClienteUDPCODWW(){}
	
	/**
	 * Metodo que permite recuperar la unica instancia que existe del ClienteUDPCODWW
	 * @return la instancia de clienteUDPCODWW si existe, si no instanciamos uno
	 */
	public static ClienteUDPCODWW getInstance() {
		if (clienteUDPCODWW == null){
			clienteUDPCODWW = new ClienteUDPCODWW();			
		}
		return clienteUDPCODWW;
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
	 * porque no corresponden con ningun servidor de codww
	 */
	public void crearConexion(String host, int puerto, String password, int timeout, ResourceBundle i18n) 
	throws IOException, ContraseñaInvalidaException{
	
		ip = InetAddress.getByName(host);
		this.puerto=puerto;		
		
		crearPassword(password);		
		
		if(ip.isReachable(timeout)){			
			cliente = new DatagramSocket();
			cliente.connect(ip, puerto);
			cliente.setSoTimeout(timeout);			
			
			// Enviamos mensaje de prueba al jugador -1 que no existe
			//para ver si la contraseña es valida
			enviar("tell " + -1 + " " + "NO VALIDO" );			
			if(recibir().contains("Invalid password"))
				throw new ContraseñaInvalidaException();
			
			hiloMensajeCODWW = new HiloMensajeCODWW("HiloMensajeCODWW", true, TIEMPOHILO, i18n);
			hiloMensajeCODWW.start();			
		}
		else{
			throw new HostInalcanzableException();
		}		
	}
	
	/**
	 * Metodo que construye el paquete con la rcon y la orden correspondiente y lo manda
	 * @param texto La orden correspondiente
	 * @throws IOException Si no llega por algún motivo
	 */
	public void enviar (String texto) throws IOException{
		String queryString = "ÿÿÿÿrcon "+ "\""+ rconPassword + "\" " + texto ;
		
		byte[] query = queryString.getBytes();
		
		// Creamos paquete
		DatagramPacket dp = new DatagramPacket(query, query.length, ip, puerto);
		
		// y lo mandamos
		
		if (cliente.isConnected()){			
			cliente.send(dp);
		}		
	}
	
	/**
	 * Metodo que recoge lo que el servidor nos devuelve y lo convierte a un String
	 * @return un String que contiene la respuesta del servidor
	 * @throws IOException Si no nos llega por algún motivo
	 */
	public String recibir () throws IOException{
		byte[] responseData = new byte[LONGITUDDATOS];
		
		DatagramPacket responsePacket = new DatagramPacket(responseData, responseData.length);
		
		cliente.receive(responsePacket);	
				
		setTamañoDatosRecibidos(responsePacket.getLength());
		return new String(responsePacket.getData());
	}
	
	/**
	 * Paramos el hilo si esta arrancado y cerramos el socket	
	 */
	public void cerrarConexion() throws InterruptedException{
		
		//La comprobacion de null pa cuando se sale sin haber conectado a CEALCON
		if(cliente != null){
			
			//Puede que el usuario no haya llegado a activarlo y no haya hilo ejecutandose
			if(hiloMensajeCODWW != null && hiloMensajeCODWW.isAlive()){				
				if(log.isDebugEnabled())
					log.debug("amos a parar el hilo");
				hiloMensajeCODWW.setContinuar(false);
				hiloMensajeCODWW.interrupt();
				hiloMensajeCODWW.join(500);
			}	
			cliente.disconnect();
			cliente.close();		
		}		
	}
	
	/**
	 * Metodo que desencripta la contraseña y la crea para que el programa la use
	 * @param password La contraseña cifrada	 
	 */
	public void crearPassword(String passwordCifrada){
				
		//limpiamos la rconPassword cada vez que intentamos conectar, por si se falla y se prueba otra vez
		rconPassword="";
		
		rconPassword = RSAmodificado.getInstance().decryptString(passwordCifrada);	
	}
	
	public int getTamañoDatosRecibidos() {
		return tamañoDatosRecibidos;
	}

	private void setTamañoDatosRecibidos(int tamañoDatos) {
		this.tamañoDatosRecibidos = tamañoDatos;
	}
}