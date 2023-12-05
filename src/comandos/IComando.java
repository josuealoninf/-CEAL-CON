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
	   M�todo que llama al mediador para que se realicen las operaciones propias del
	   *comando.
	   *@param mediador Objeto encargado de recibir eventos y ejecutar comandos. (Patr�n de dise�o Mediador)
	   *@param event El evento producido, necesario en algunos casos para sacar m�s informaci�n
	   */
	  public void ejecutar( IMediador mediador, AWTEvent event );
}
