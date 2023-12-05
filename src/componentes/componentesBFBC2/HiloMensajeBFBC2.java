package componentes.componentesBFBC2;

import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import excepciones.DesincronizacionSecuencia;


/**
 * HiloMensajeBFBC2 saca por pantalla el mensaje informativo acojonatorio XDD y mantiene la consola conectada para evitar un timeout
 * @author Alatriste
 * @version 0.3
 */
public final class HiloMensajeBFBC2 extends Thread{
	
	/** El logger de log4j*/
	private static final Logger log = Logger.getLogger(HiloMensajeBFBC2.class);
	/** Controla la ejecución del hilo y permite su parada*/
	private boolean continuar;
	/** Establece cada cuanto comunicaremos con el server para evitar el timeout*/
	private int tiempoHilo;
	/** Establece el texto a enviar al servidor*/
	private String textoParaEnviar;
	/** Establece las repeticiones que hay que esperar para que salga por pantalla el mensaje acojonatorio*/
	private int contador;
	
	/** De aquí sacaremos la clave valor para cada literal*/
	private ResourceBundle i18n;
		
    public HiloMensajeBFBC2(String nombre, boolean continuar, int tiempoHilo, ResourceBundle i18n) {
        super(nombre);
        this.continuar = continuar;
        this.tiempoHilo = tiempoHilo;
        this.i18n = i18n;
        contador = 0;
    }    
    
    @Override
	public void run() {
        
    	while(continuar){    		
    		try {    
    			if(contador == 12){ //Una vez por hora, 300 segundos * 12 veces, sacamos el mensaje acojonatorio
    				
	    			textoParaEnviar = "admin.say" + ClienteTCP_BFBC2.SEP + i18n.getString("hmbfbc21") + ClienteTCP_BFBC2.SEP + "all";	
	    			ClienteTCP_BFBC2.getInstance().enviar(textoParaEnviar);				
	    			ClienteTCP_BFBC2.getInstance().esperandoaRecibir(ClienteTCP_BFBC2.ESPERA);
	    			ClienteTCP_BFBC2.getInstance().recibir();
	    			
	    			textoParaEnviar = "admin.say" + ClienteTCP_BFBC2.SEP + i18n.getString("hmbfbc22") + ClienteTCP_BFBC2.SEP + "all";	
	    			ClienteTCP_BFBC2.getInstance().enviar(textoParaEnviar);				
	    			ClienteTCP_BFBC2.getInstance().esperandoaRecibir(ClienteTCP_BFBC2.ESPERA);
	    			ClienteTCP_BFBC2.getInstance().recibir();	
	    			
	    			//Volvemos a empezar
	    			contador = 0;
    			}
    			else{    				
    				// Simplemente le preguntamos al server el nivel actual
    				textoParaEnviar = "admin.currentLevel";	
	    			ClienteTCP_BFBC2.getInstance().enviar(textoParaEnviar);				
	    			ClienteTCP_BFBC2.getInstance().esperandoaRecibir(ClienteTCP_BFBC2.ESPERA);
	    			ClienteTCP_BFBC2.getInstance().recibir();
	    			contador++;
    			}
        		sleep(tiempoHilo * 1000);        		
			} catch(InterruptedException e) {				
				log.error(e);
			} catch(IOException e){				
				log.error(e);
			} catch(DesincronizacionSecuencia de){
				log.error(de);
			}
    	}
    	if(log.isDebugEnabled())
    		log.debug("se acabo el hilo");    	
    }   
	
	/**
	 * Controla la ejecución del hilo y permite su parada
	 * @param continuar true para continuar, false para parar
	 */
	public synchronized void setContinuar(boolean continuar) {
		this.continuar = continuar;
	}
}