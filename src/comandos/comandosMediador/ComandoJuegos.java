package comandos.comandosMediador;

import java.awt.AWTEvent;

import javax.swing.JComboBox;

import comandos.IComando;

import mediador.IMediador;

/**
 * ComandoJuegos Se ejecuta cuando se selecciona el juego a vigilar
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoJuegos extends JComboBox implements IComando{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = -2234403646064383574L;

	/**	
	 * @param nombreComandoGuardar El nombre del botón Guardar
	 */
	public ComandoJuegos (String [] juegos){
		super(juegos);		
	}
	
	/**
	 * se dispara cuando se selecciona el juego
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoJuegos(event);
	}
}
