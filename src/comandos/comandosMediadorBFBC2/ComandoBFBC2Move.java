package comandos.comandosMediadorBFBC2;

import java.awt.AWTEvent;

import javax.swing.JButton;

import mediador.IMediador;

import comandos.IComando;

/**
 * ComandoBFBC2Move Se ejecuta al pulsar en el botón de cambiar de equipo del BFBC2
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoBFBC2Move extends JButton implements IComando{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = 8395401804080755745L;
	
	/**	
	 * @param ComandoBFBC2Move El nombre del boton
	 */
	public ComandoBFBC2Move (String ComandoBFBC2Move){
		super(ComandoBFBC2Move);		
	}

	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoBFBC2Move();
	}
}
