package excepciones;


/**
 * DesincronizacionSecuencia Se lanza cuando se ha perdido la cuenta entre
 * la secuencia enviada al server y la recibida
 * @author Alatriste
 * @version 0.3
 */
public final class DesincronizacionSecuencia extends Exception{

	/**
	 * Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo
	 */
	private static final long serialVersionUID = 5213121737390790558L;
	
	public DesincronizacionSecuencia(String textoexcepcion){
		super(textoexcepcion);
	}
}
