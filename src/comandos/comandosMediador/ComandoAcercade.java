package comandos.comandosMediador;

import java.awt.AWTEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import comandos.IComando;

import mediador.IMediador;

/**
 * ComandoAyuda Ejecutado cuando se pincha en el boton con el icono de CEAL
 * @author Alatriste
 * @version 0.3
 */
public final class ComandoAcercade extends JButton implements IComando{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = -61897345353612068L;	
	
	/**
	 * Construye el comando ayuda con el logo del clan 
	 * @param imagenLogoCEAL El logo del clan	
	 */
	public ComandoAcercade (ImageIcon imagenLogoCEAL){
		super(imagenLogoCEAL);		
	}
	
	/**
	 * se dispara cuando se pulsa el boton
	 */
	public void ejecutar( IMediador mediador, AWTEvent event ) {
		mediador.comandoAcercade();
	}
}
