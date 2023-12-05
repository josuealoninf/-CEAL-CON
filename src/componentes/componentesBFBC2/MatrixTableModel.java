package componentes.componentesBFBC2;

import javax.swing.table.AbstractTableModel;

public final class MatrixTableModel extends AbstractTableModel{
	
	/**
	 * Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo
	 */
	private static final long serialVersionUID = 8261776732623828900L;
	
	private String[] columnas;
    private String[][] matrixjugadores;
    
    public MatrixTableModel(String[] columnas, String[][] matrixjugadores){
    	this.columnas = columnas;
    	this.matrixjugadores = matrixjugadores;
    }
    
    /**
	 * El recuento de las columnas */
	public int getColumnCount(){
		return columnas.length;
	}
	
	/**
	 * El recuento de las filas*/
	public int getRowCount(){
		return matrixjugadores.length;
	}
	
	/**
	 * Devuelve el valor correspondiente en cada punto de la tabla. simplemente se consulta la matriz en la fila y columnas solicitadas
	 */
	public String getValueAt(int fila, int columna){
		return matrixjugadores[fila][columna];		
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
		return columnas[columna];
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
		matrixjugadores = null;
	}
	
	/**
	 * Sustituimos los viejos jugadores por los nuevos en cada refresco
	 * @param matrixjugadores Los nuevos datos de los jugadores
	 */
	public void addAllRows(String[][] matrixjugadores){
		this.matrixjugadores = matrixjugadores;
	}
}
