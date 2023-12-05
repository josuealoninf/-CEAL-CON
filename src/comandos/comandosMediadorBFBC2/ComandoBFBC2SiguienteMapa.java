package comandos.comandosMediadorBFBC2;

import java.awt.AWTEvent;

import javax.swing.JButton;

import mediador.IMediador;

import comandos.IComando;

/**
 * ComandoBFBC2SiguienteMapa Se ejecuta al pulsar en el botón de siguiente mapa
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoBFBC2SiguienteMapa extends JButton implements IComando{

	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = 4308898659003618795L;
	
	/**	
	 * @param nombreComandoBFBC2SiguienteMapa El nombre del botón
	 */
	public ComandoBFBC2SiguienteMapa(String nombreComandoBFBC2SiguienteMapa){
		super(nombreComandoBFBC2SiguienteMapa);
	}

	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoBFBC2NextMap();
	}
}
