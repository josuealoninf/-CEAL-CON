package componentes.componentesCODWW;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;


/**
 * ArrayListTableModel Implementa el modelo personalizado de la tabla con ArrayList que es lo que tenemos
 * @author Alatriste
 * @version 0.2
 */
public final class ArrayListTableModel extends AbstractTableModel {
	
	/**
	 * Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo
	 */
	private static final long serialVersionUID = 1281141684486064375L;
		
	/** El array de arrays que contiene los datos de los jugadores*/
	private ArrayList<ArrayList<String>> filas;
	/** El array de las columnas*/
	private ArrayList<String> columnas;
	
	/**
	 * Constructor que recibe los datos correspondientes
	 * @param filas  El array de arrays que contiene los datos de los jugadores
	 * @param columnas El array de las columnas
	 */
	public ArrayListTableModel(ArrayList<ArrayList<String>> filas, ArrayList<String> columnas){
		this.filas=filas;
		this.columnas=columnas;		
	}

	/**
	 * El recuento de las columnas
	 */
	public int getColumnCount(){
		return columnas.size();
	}
	
	/**
	 * El recuento de las filas
	 */
	public int getRowCount(){
		return filas.size();
	}
	
	/**
	 * Devuelve el valor correspondiente en cada punto de la tabla. Primero se saca el
	 * array correspondiente del jugador y luego se devuelve la columna solicitada
	 */
	public Object getValueAt(int fila, int columna){
		return (filas.get(fila)).get(columna);		
	}
	
	/**
	 * La tabla no es editable.
	 */
	@Override
	public boolean isCellEditable(int fila, int columna) {
       return false;
    }
	
	/**
	 * Devuelve el nombre de la columna
	 */
	@Override
	public String getColumnName(int columna){
		return columnas.get(columna);
	}

	/**
	 * no se añade ningun elemento a la tabla
	 */
	@Override
	public void setValueAt(Object unvalor, int fila, int columna){}
	
	/**
	 * Elimina todas las filas de los jugadores, para meter nuevos
	 */
	public void removeAllRows(){
		filas.clear();
	}
	
	/**
	 * Sustituimos los viejos jugadores por los nuevos en cada refresco
	 * @param filas Los nuevos datos de los jugadores
	 */
	public void addAllRows(ArrayList<ArrayList<String>> filas){
		this.filas = filas;
	}
	
	// Para la ordenacion... pendiente de hacer un día de estos...
	@Override
	public Class<?> getColumnClass(int columna){
		switch(columna){
		
			case 0:
			case 1:
			case 2:
			case 3:
			case 5:
			case 7:
			case 8:
				return Integer.class;				
			case 4:
			case 6:
				return String.class;
			default:
				return String.class;
		}		
	}
}
