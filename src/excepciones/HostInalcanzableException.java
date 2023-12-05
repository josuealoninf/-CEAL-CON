package excepciones;

import java.io.IOException;

/**
 * HostInalcanzableException
 * Nos sirve para saber si una dirección ip correcta no esta accesible y por tanto no crear la consola, q entra en un estado inestable y ni
 * siquiera puede cerrarse
 * @author Alatriste
 * @version 0.2
 */
public final class HostInalcanzableException extends IOException{

	/**
	 * Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo
	 */
	private static final long serialVersionUID = 867425511212178012L;
}
