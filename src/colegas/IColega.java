package colegas;

import mediador.IMediador;

/**
 * Interface IColega
 * @author Alatriste
 * @version 0.2
 */
public interface IColega{
	
	/**
	   *Obtiene una referencia al gestor de eventos de la interfaz gráfica.
	   *@param mediador Gestor de eventos del GUI
	   */
	  public void setMediador( IMediador mediador );
}
