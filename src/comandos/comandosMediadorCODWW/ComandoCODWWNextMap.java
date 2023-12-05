package comandos.comandosMediadorCODWW;

import java.awt.AWTEvent;

import javax.swing.JButton;

import mediador.IMediador;

import comandos.IComando;

/**
 * ComandoCODWWNextMap Se ejecuta al pulsar el botón Siguiente mapa
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoCODWWNextMap extends JButton implements IComando{

	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = 7327884820102362557L;
	
	/**
	 * @param nombreComandoCODWWNextMap  El nombre del boton
	 */
	public ComandoCODWWNextMap(String nombreComandoCODWWNextMap){
		super(nombreComandoCODWWNextMap);
	}

	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoCODWWNextMap();
	}
}
