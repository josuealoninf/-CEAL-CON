package comandos.comandosMediador;

import java.awt.AWTEvent;

import javax.swing.JMenuItem;

import comandos.IComando;


import mediador.IMediador;


/**
 * ComandoMenuConexion Se ejecuta al pulsar en el menú Conectar
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoMenuConexion extends JMenuItem implements IComando{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = -1335202642852378983L;

	/**
	 * 
	 * @param nombreComandoMenuConexion
	 */
	public ComandoMenuConexion(String nombreComandoMenuConexion){
		super(nombreComandoMenuConexion);
	}
	
	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoMenuConexion();
	}
}
