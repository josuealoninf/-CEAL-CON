package comandos.comandosMediador;

import java.awt.AWTEvent;

import javax.swing.JButton;

import mediador.IMediador;

import comandos.IComando;

/**
 * ComandoGuardarConfiguracion Se ejecuta cuando se pulsa el bot�n Guardar Configuracion
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoGuardarConfiguracion extends JButton implements IComando{

	/** Para que no de el co�azo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = 928172946493947920L;

	/**
	 * @param nombreComandoGuardarConfiguracion El nombre del bot�n Guardar Configuraci�n
	 */
	public ComandoGuardarConfiguracion (String nombreComandoGuardarConfiguracion){
		super(nombreComandoGuardarConfiguracion);
	}
	
	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoGuardarConfiguracion();
	}
}
