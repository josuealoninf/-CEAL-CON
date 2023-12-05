package comandos.comandosMediador;

import java.awt.AWTEvent;

import javax.swing.JButton;

import mediador.IMediador;

import comandos.IComando;

public final class ComandoCancelarConfiguracion extends JButton implements IComando{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = -1709896223203745646L;
	
	/**
	 * @param nombreComandoCancelarConfiguracion El nombre del botón cancelar configuracion 
	 */
	public ComandoCancelarConfiguracion(String nombreComandoCancelarConfiguracion){
		super(nombreComandoCancelarConfiguracion);
	}

	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoCancelarConfiguracion();
	}
}
