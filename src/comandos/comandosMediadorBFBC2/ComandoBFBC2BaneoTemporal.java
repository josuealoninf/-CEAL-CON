package comandos.comandosMediadorBFBC2;

import java.awt.AWTEvent;

import javax.swing.JButton;

import mediador.IMediador;

import comandos.IComando;

/**
 * ComandoBFBC2BaneoTemporal Se ejecuta al pulsar en el botón de baneo temporal del BFBC2
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoBFBC2BaneoTemporal extends JButton implements IComando{

	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = -2618559247586509108L;

	/**
	 * @param nombreComandoBFBC2BaneoTemporal el nombre del botón
	 */
	public ComandoBFBC2BaneoTemporal(String nombreComandoBFBC2BaneoTemporal){
		super(nombreComandoBFBC2BaneoTemporal);
	}
	
	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoBFBC2BaneoTemporal();
	}
}
