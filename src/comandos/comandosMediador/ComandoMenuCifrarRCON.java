package comandos.comandosMediador;

import java.awt.AWTEvent;

import javax.swing.JMenuItem;

import comandos.IComando;

import mediador.IMediador;


/**
 * ComandoMenuCifrarRCON Se ejecuta al pulsar en el men� CifrarRCON
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoMenuCifrarRCON extends JMenuItem implements IComando{
	
	/**Para que no de el co�azo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = -4115201259411903073L;

	/**
	 * 
	 * @param nombreComandoMenuDesconexion
	 */
	public ComandoMenuCifrarRCON(String nombreComandoMenuDesconexion){
		super(nombreComandoMenuDesconexion);
	}
	
	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoMenuCifrarRCON();
	}
}
