package comandos.comandosMediador;

import java.awt.AWTEvent;

import javax.swing.JButton;

import comandos.IComando;

import mediador.IMediador;

/**
 * ComandoConexion Se ejecuta al pulsar en el botón conectar
 * @author Alatriste
 * @version 0.2
 */
public final class ComandoConexion extends JButton implements IComando{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = 4345266978654710293L;


	/**	
	 * @param nombreComandoConexion El nombre del boton
	 */
	public ComandoConexion (String nombreComandoConexion){
		super(nombreComandoConexion);		
	}
	
	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoConectar();
	}
}
