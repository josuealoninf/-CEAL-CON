package comandos.comandosMediadorBFBC2;

import java.awt.AWTEvent;

import javax.swing.JButton;

import mediador.IMediador;

import comandos.IComando;

/**
 * ComandoBFBC2ReiniciarMapa Se ejecuta al pulsar en el bot�n de reiniciar el mapa
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoBFBC2ReiniciarMapa extends JButton implements IComando{
	
	/** Para que no de el co�azo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = 1135509440636650208L;
	
	/**	
	 * @param nombreComandoBFBC2ReiniciarMapa El nombre del bot�n
	 */
	public ComandoBFBC2ReiniciarMapa(String nombreComandoBFBC2ReiniciarMapa){
		super(nombreComandoBFBC2ReiniciarMapa);
	}

	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoBFBC2RestartMap();
	}
}
