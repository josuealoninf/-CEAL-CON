package comandos.comandosMediadorBFBC2;

import java.awt.AWTEvent;

import javax.swing.JButton;

import mediador.IMediador;

import comandos.IComando;

/**
 * ComandoBFBC2ChatyChatPrivado Se ejecuta al pulsar en el botón de decir y decir en privado del BFBC2
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoBFBC2ChatyChatPrivado extends JButton implements IComando{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = 3031618363792212345L;
	/** La variable que nos dice si el chat va a ser privado o no*/
	private boolean privado;
	
	/**
	 * @param nombreComandoBFBC2ChatyChatPrivado El nombre del boton
	 * @param privado Indica si va a ser privado o no
	 */
	public ComandoBFBC2ChatyChatPrivado(String nombreComandoBFBC2ChatyChatPrivado, boolean privado){
		super(nombreComandoBFBC2ChatyChatPrivado);
		this.privado = privado;
	}
	
	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoBFBC2ChatyChatPrivado(privado);
	}
}
