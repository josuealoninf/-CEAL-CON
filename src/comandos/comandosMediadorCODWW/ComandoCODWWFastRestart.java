package comandos.comandosMediadorCODWW;

import java.awt.AWTEvent;

import javax.swing.JButton;

import mediador.IMediador;

import comandos.IComando;

/**
 * ComandoCODWWFastRestart Se ejecuta al pulsar el botón Fast Restart
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoCODWWFastRestart extends JButton implements IComando{

	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = 5908130126148696619L;
	
	/**
	 * @param nombreComandoCODWWFastRestart  El nombre del boton
	 */
	public ComandoCODWWFastRestart(String nombreComandoCODWWFastRestart){
		super(nombreComandoCODWWFastRestart);
	}

	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoCODWWFastRestart();
	}
}
