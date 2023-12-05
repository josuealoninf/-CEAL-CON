package comandos;

import java.awt.AWTEvent;

import mediador.IMediador;


/**
 * Interface IComando
 * @author Alatriste
 * @version 0.3
 */
public interface IComando {
	
	/**
	   Método que llama al mediador para que se realicen las operaciones propias del
	   *comando.
	   *@param mediador Objeto encargado de recibir eventos y ejecutar comandos. (Patrón de diseño Mediador)
	   *@param event El evento producido, necesario en algunos casos para sacar más información
	   */
	  public void ejecutar( IMediador mediador, AWTEvent event );
}
