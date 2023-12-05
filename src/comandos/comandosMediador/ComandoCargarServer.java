package comandos.comandosMediador;

import java.awt.AWTEvent;

import javax.swing.JButton;

import mediador.IMediador;

import comandos.IComando;

/**
 * ComandoCargarServer Se ejecuta cuando se pulsa el botón Cargar Server
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoCargarServer extends JButton implements IComando{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = 4131711278283070967L;
	
	/**
	 * @param nombreComandoCargarServer El nombre del botón Cargar Server
	 */
	public ComandoCargarServer (String nombreComandoCargarServer){
		super(nombreComandoCargarServer);		
	}

	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoCargarServer();
	}
}
