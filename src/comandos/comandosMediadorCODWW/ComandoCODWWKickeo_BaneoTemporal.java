package comandos.comandosMediadorCODWW;

import java.awt.AWTEvent;

import javax.swing.JButton;

import comandos.IComando;

import mediador.IMediador;

/**
 * ComandoCODWWKickeo_BaneoTemporal Se ejecuta al pulsar el botón kickear
 * @author Alatriste
 * @version 0.2
 */
public final class ComandoCODWWKickeo_BaneoTemporal extends JButton implements IComando{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = -3359528523541627689L;
	/** La variable que nos dice si el en vez de un kick se va a hacer un baneo temporal al jugador*/
	private boolean kickear;

	/**	
	 * @param nombreComandoCODWWKickeo_BaneoTemporal El nombre del boton
	 */
	public ComandoCODWWKickeo_BaneoTemporal (String nombreComandoCODWWKickeo_BaneoTemporal, boolean kickear){
		super(nombreComandoCODWWKickeo_BaneoTemporal);		
		this.kickear = kickear;
	}
	
	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoCODWWKickeo_BaneoTemporal(kickear);
	}
}
