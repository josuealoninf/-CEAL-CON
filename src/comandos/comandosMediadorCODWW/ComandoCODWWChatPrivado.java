package comandos.comandosMediadorCODWW;

import java.awt.AWTEvent;

import javax.swing.JButton;

import comandos.IComando;


import mediador.IMediador;

/**
 * ComandoCODWWChatPrivado Se ejecuta al pulsar el botón Decir en privado
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoCODWWChatPrivado extends JButton implements IComando{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = 934165848628875136L;	

	/**	
	 * @param nombreComandoCODWWChatPrivado El nombre del boton
	 */
	public ComandoCODWWChatPrivado (String nombreComandoCODWWChatPrivado){
		super(nombreComandoCODWWChatPrivado);		
	}
	
	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
//		 Los chequeos para mandar un chat privado son los mismos que el kick asi q usamos esta funcion
		mediador.comandoCODWWChatPrivado();
	}
}
