package comandos.comandosMediadorBFBC2;

import java.awt.AWTEvent;

import javax.swing.JButton;

import mediador.IMediador;

import comandos.IComando;

/**
 * ComandoBFBC2YellyYellPrivado Se ejecuta al pulsar en el botón de gritar y gritar en privado del BFBC2
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoBFBC2YellyYellPrivado extends JButton implements IComando{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = -3749037226316132739L;
	/** La variable que nos dice si el yell va a ser privado o no*/
	private boolean privado;
	
	/**
	 * @param nombreComandoBFBC2YellyYellPrivado El nombre del boton
	 * @param privado Indica si va a ser privado o no 
	 */
	public ComandoBFBC2YellyYellPrivado (String nombreComandoBFBC2YellyYellPrivado, boolean privado){
		super(nombreComandoBFBC2YellyYellPrivado);		
		this.privado = privado;
	}
	
	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoBFBC2YellyYellPrivado(privado);
	}
}
