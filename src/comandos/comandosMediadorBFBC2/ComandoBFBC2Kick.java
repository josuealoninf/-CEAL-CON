package comandos.comandosMediadorBFBC2;

import java.awt.AWTEvent;

import javax.swing.JButton;

import mediador.IMediador;

import comandos.IComando;

/**
 * ComandoBFBC2Kick Se ejecuta al pulsar en el botón de kickear del BFBC2
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoBFBC2Kick extends JButton implements IComando{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = 2217022870168849455L;
	
	/**	
	 * @param nombreComandoBFBC2Kick nombre del botón
	 */
	public ComandoBFBC2Kick(String nombreComandoBFBC2Kick){
		super(nombreComandoBFBC2Kick);
	}

	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoBFBC2Kick();
	}
}
