package cliente;

import mediador.IMediador;
import mediador.Mediador;

/**
 * CEALCONcliente
 * @author Alatriste
 * @version 0.3
 */

public final class CEALCONcliente{
	
	/**
	 * Metodo principal que arranca la aplicacion, registramos los componente principales de la misma
	 * @param args
	 */
	public static void main(String args[]){
		
		IMediador mediador = Mediador.getInstance();	
		
		mediador.registrarJFrameCEALCON(mediador);
		mediador.registrarDialogoConexion(mediador);
		mediador.registrarDialogoConfiguracion(mediador);
		mediador.registrarDialogoCifrarRCON(mediador);		
		mediador.registrarDialogoAcercade(mediador);		
	}//main
}//CEALCONcliente
