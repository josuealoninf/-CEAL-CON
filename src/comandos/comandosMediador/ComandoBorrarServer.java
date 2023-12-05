package comandos.comandosMediador;

import java.awt.AWTEvent;

import javax.swing.JButton;

import mediador.IMediador;

import comandos.IComando;

/**
 * ComandoBorrarServer Se ejecuta cuando se pulsa el botón Borrar Server
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoBorrarServer extends JButton implements IComando{

	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = -9056794529608455803L;
	
	/**
	 * @param nombreComandoBorrarServer El nombre del botón Borrar Server
	 */
	public ComandoBorrarServer (String nombreComandoBorrarServer){
		super(nombreComandoBorrarServer);		
	}

	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoBorrarServer();
	}
}
