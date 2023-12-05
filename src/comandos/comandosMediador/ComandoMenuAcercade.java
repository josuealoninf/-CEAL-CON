package comandos.comandosMediador;

import java.awt.AWTEvent;

import javax.swing.JMenuItem;

import comandos.IComando;

import mediador.IMediador;


/**
 * ComandoMenuAcercade Se ejecuta al pulsar en el men� Acerca de
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoMenuAcercade extends JMenuItem implements IComando{
	
	/** Para que no de el co�azo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = -1335202642852378983L;

	/**
	 * 
	 * @param nombreComandoMenuAcercade Nombre de la entrada del men�
	 */
	public ComandoMenuAcercade(String nombreComandoMenuAcercade){
		super(nombreComandoMenuAcercade);
	}
	
	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoMenuAcercade();
	}
}
