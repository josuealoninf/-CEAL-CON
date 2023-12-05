package comandos.comandosMediador;

import java.awt.AWTEvent;

import javax.swing.JButton;

import comandos.IComando;


import mediador.IMediador;

/**
 * ComandoRefresco Se ejecuta al pulsar en el botón Refresco
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoRefresco extends JButton implements IComando{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = 6650532827847188027L;

	/**	
	 * @param nombreComandoRefresco El nombre del boton
	 */
	public ComandoRefresco (String nombreComandoRefresco){
		super(nombreComandoRefresco);		
	}
	
	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoRefresco();
	}
}
