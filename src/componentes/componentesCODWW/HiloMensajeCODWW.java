package componentes.componentesCODWW;

import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;


/**
 * HiloMensajeCODWW saca por pantalla el mensaje informativo acojonatorio XDD
 * @author Alatriste
 * @version 0.2
 */
public final class HiloMensajeCODWW extends Thread{
	
	/** El logger de log4j*/
	private static final Logger log = Logger.getLogger(HiloMensajeCODWW.class);
	
	/** Controla la ejecución del hilo y permite su parada*/
	private boolean continuar;
	/** Establece cada cuanto saldrá el mensaje por pantalla*/
	private int tiempoHilo;
	
	/** De aquí sacaremos la clave valor para cada literal*/
	private ResourceBundle i18n;
		
    public HiloMensajeCODWW(String nombre, boolean continuar, int tiempoHilo, ResourceBundle i18n) {
        super(nombre);
        this.continuar=continuar;
        this.tiempoHilo = tiempoHilo;
        this.i18n = i18n;
    }    
    
    @Override
	public void run() {
        
    	while(continuar){    		
    		try {
    			
    			sleep(tiempoHilo * 1000);  
    			
    			ClienteUDPCODWW.getInstance().enviar("say " + i18n.getString("hmcodww1"));
    			ClienteUDPCODWW.getInstance().recibir();
    			sleep(500);    			
        		ClienteUDPCODWW.getInstance().enviar("say " + i18n.getString("hmcodww2"));
        		ClienteUDPCODWW.getInstance().recibir();       		      		
			} catch (InterruptedException e) {				
				log.error(e);
			} 
			catch(IOException e){				
				log.error(e);
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