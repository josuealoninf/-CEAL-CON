package comandos.comandosMediador;

import java.awt.AWTEvent;

import javax.swing.JMenuItem;

import comandos.IComando;

import mediador.IMediador;

/**
 * ComandoMenuConfiguracion Se ejecuta al pulsar en el men� Configuraci�n
 * @author Alatriste
 * @version 0.3
 */

public final class ComandoMenuConfiguracion extends JMenuItem implements IComando{
	
	/**Para que no de el co�azo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = 8220328907643293175L;

	/**
	 * @param nombreComandoMenuConfiguracion
	 */
	public ComandoMenuConfiguracion(String nombreComandoMenuConfiguracion){
		super(nombreComandoMenuConfiguracion);
	}

	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoMenuConfiguracion();
	}
}
