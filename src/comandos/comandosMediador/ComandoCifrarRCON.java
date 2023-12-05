package comandos.comandosMediador;

import java.awt.AWTEvent;

import javax.swing.JButton;

import comandos.IComando;

import mediador.IMediador;

/**
 * ComandoCifrarRCON Se ejecuta al pulsar en el botón Cifrar
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoCifrarRCON extends JButton implements IComando{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = 5055686934322447577L;

	/**	
	 * @param nombreComandoCifrarRCON El nombre del boton
	 */
	public ComandoCifrarRCON (String nombreComandoCifrarRCON){
		super(nombreComandoCifrarRCON);		
	}
	
	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoCifrarRCON();
	}
}
