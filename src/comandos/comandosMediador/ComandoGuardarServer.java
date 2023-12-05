package comandos.comandosMediador;

import java.awt.AWTEvent;

import javax.swing.JButton;

import comandos.IComando;

import mediador.IMediador;

/**
 * ComandoGuardarServer Se ejecuta cuando se pulsa el botón Guardar Server
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoGuardarServer extends JButton implements IComando{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = -6266289699953029073L;

	/**
	 * @param nombreComandoGuardar El nombre del botón Guardar Server
	 */
	public ComandoGuardarServer (String nombreComandoGuardar){
		super(nombreComandoGuardar);		
	}
	
	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoGuardarServer();
	}
}
