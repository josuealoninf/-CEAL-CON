package comandos.comandosMediadorCODWW;

import java.awt.AWTEvent;

import javax.swing.JButton;

import comandos.IComando;

import mediador.IMediador;

/**
 * ComandoCODWWChat Se ejecuta al pulsar el botón Decir
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoCODWWChat extends JButton implements IComando{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = -8421622238446098990L;

	/**	
	 * @param nombreComandoCODWWChat El nombre del botón
	 */
	public ComandoCODWWChat (String nombreComandoCODWWChat){
		super(nombreComandoCODWWChat);		
	}
	
	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoCODWWChat();
	}
}
