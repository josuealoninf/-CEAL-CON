package mediador;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableRowSorter;

import colegas.PanelCODWW;

import componentes.componentesCODWW.ArrayListTableModel;
import componentes.componentesCODWW.ClienteUDPCODWW;


/**
 * MediadorCODWW. Esta clase contiene la lógica particular para CODWW
 * @author Alatriste
 * @version 0.3
 */
public final class MediadorCODWW extends Mediador{
	
	/** Este es el panel particular de la consola de CODWW*/
	private PanelCODWW panelCODWW;
	/** Necesitamos guardarlo para la traducción del texto de los comandos*/
	private ResourceBundle i18n;
	
	/** Constante que nos indica la longitud de la fila, en el CODWW es de 9*/
	private final int LONGITUDFILA = 9;
	/** Constante que nos indica la posición del slot del jugador, en el CODWW es la 0*/
	private final int POSICIONSLOT = 0;
	/** Constante que nos indica la posición del nombre, en el CODWW es la 4*/
	private final int POSICIONNOMBRE = 4;
	/** Constante que nos indica la posición de la IP, en el CODWW es la 6*/
	private final int POSICIONIP = 6;		
	/** Constante que nos indica la posición de la guid, en el CODWW es la 6*/
	private final int POSICIONGUID = 3;
	
	private ArrayListTableModel arrayListTableModel;
	private JTable listadeJugadores;
	
	private ArrayList<String> lista  = new ArrayList<String>();
	
	private ArrayList<String> listaaux  = new ArrayList<String>();
	
	private ArrayList<String> listaaux2 = new ArrayList<String>();

	private ArrayList<String> arrayListColumnas;

	private ArrayList<ArrayList<String>> arrayListFilas;

	/** El String con la respuesta total que nos ha dado el servidor*/
	private String respuestaserver="";
	
	protected MediadorCODWW(ResourceBundle i18n){
		
		this.i18n = i18n;
		
		arrayListColumnas = new ArrayList<String>();
		arrayListColumnas.add("num");
		arrayListColumnas.add("score");
		arrayListColumnas.add("ping");
		arrayListColumnas.add("guid");
		arrayListColumnas.add("name");
		arrayListColumnas.add("lastmsg");
		arrayListColumnas.add("address");
		arrayListColumnas.add("qport");
		arrayListColumnas.add("rate");	
		
		arrayListTableModel = new ArrayListTableModel(new ArrayList<ArrayList<String>>(), arrayListColumnas);
		listadeJugadores = new JTable(arrayListTableModel);		
		listadeJugadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listadeJugadores.setRowSorter(new TableRowSorter<ArrayListTableModel>(arrayListTableModel));
		listadeJugadores.getColumnModel().getColumn(POSICIONGUID).setPreferredWidth(150);
		listadeJugadores.getColumnModel().getColumn(POSICIONNOMBRE).setPreferredWidth(300);
		listadeJugadores.getColumnModel().getColumn(POSICIONIP).setPreferredWidth(250);
		
		panelCODWW = new PanelCODWW(jFrameCEALCON, listadeJugadores, i18n);
	}	
		
	@Override
	public void registrarPaneldeJuego(IMediador mediador){			
		
		panelCODWW.setMediador(mediador);
		panelCODWW.añadirOyentes();
	}	
	
	@Override
	public void comandoRefresco(){		
						
		arrayListFilas = null;
		arrayListFilas = new ArrayList<ArrayList<String>>();	
		
		viejo = System.currentTimeMillis();		
		
		//Limpiamos la lista y el string para empezar con el nuevo refresco
		lista.clear();
		respuestaserver = null;
		respuestaserver = new String();

		try{
			ClienteUDPCODWW.getInstance().enviar("status");
						
			while(true) {				
				String aux = ClienteUDPCODWW.getInstance().recibir();
				//quitamos el ÿÿÿÿprint\n del comienzo de la cadena en todos los casos
				//son 11 caracteres pero el \n cuenta como uno, asi q hay q quitar 10
				respuestaserver = respuestaserver.concat(aux.substring(10, aux.length()-1));
				//StringUtils.deleteWhitespace(respuestaserver);
				if(log.isDebugEnabled())
					log.debug("Longitud informacion "+ ClienteUDPCODWW.getInstance().getTamañoDatosRecibidos());						
			} 
		}
		catch(SocketTimeoutException stex){}
		catch (IOException e1) {
			noRespondeServidor();	
			log.warn(e1);
		}			

		if(log.isDebugEnabled())
			log.debug("RESPUESTASERVER " +respuestaserver);
	
		// Para evitar el puto problema de que dos jugadores se meten en la misma fila, voy a meter un espacio en todos los ^7 que aparezcan
		// de esta forma si el paquete se corta justo al final de un nombre (q siempre acaba en ^7) pos no pasa nada
		// el efecto colateral es q se meten algunos espacios de mas, pero weno todo no se puede tener...
		respuestaserver.replaceAll("^7", "^7 ");
		
		lista.addAll(Arrays.asList(respuestaserver.split("\n")));	
		
		if(log.isDebugEnabled())
			log.debug("COMO LISTA " +lista);
		
		//borramos el mapa, las columnas y los ----
		lista.remove(0);
		lista.remove(0);
		lista.remove(0);
		parsearJugadores(lista);
	
		if(log.isDebugEnabled()){
			log.debug("vector columnas : " + arrayListColumnas);
			log.debug("vector filas : " + arrayListFilas);
		}
		  
		arrayListTableModel.removeAllRows();
		arrayListTableModel.addAllRows(arrayListFilas);				
		arrayListTableModel.fireTableDataChanged();					
	}
	
	@Override
	public void comandoCODWWKickeo_BaneoTemporal(boolean kickear){
		if (chequear_tablaactualizada_jugadorseleccionado(listadeJugadores)) {
			kickear_BanearTemporalmente_Jugador(listadeJugadores.getSelectedRow(), kickear);
		}				
	}
	
	@Override
	public void comandoCODWWChat(){
		
		try{
			if(!panelCODWW.getChat().getText().isEmpty()){				
				ClienteUDPCODWW.getInstance().enviar("say " + usuarioCEAL + "^7: " + panelCODWW.getChat().getText());	
				//generando una espera de 500 ms...
				Thread.sleep(500);
				ClienteUDPCODWW.getInstance().recibir();
			}
			else{
				JOptionPane.showMessageDialog(jFrameCEALCON,
						i18n.getString("mcodwwcc1"),
						i18n.getString("mcodwwcc2"),
						JOptionPane.WARNING_MESSAGE);
			}
		}
		catch (IOException e1) {
			noRespondeServidor();
			log.warn(e1);
		}
		catch (InterruptedException e1) {
			log.error(e1);			
		}
	}		
	
	@Override
	public void comandoCODWWChatPrivado(){
		
		if(chequear_tablaactualizada_jugadorseleccionado(listadeJugadores)){
			try{
				//jugador seleccionado
				String num = (String) listadeJugadores.getValueAt(listadeJugadores.getSelectedRow(), POSICIONSLOT);
				
				if(!panelCODWW.getChat().getText().isEmpty()){
					ClienteUDPCODWW.getInstance().enviar("tell " + num + " " + usuarioCEAL + i18n.getString("mcodwwccp3") + panelCODWW.getChat().getText());
					//generando una espera de 500 ms...
					Thread.sleep(500);
					ClienteUDPCODWW.getInstance().recibir();
				}
				else{
					JOptionPane.showMessageDialog(jFrameCEALCON,
							i18n.getString("mcodwwccp1") ,
							i18n.getString("mcodwwccp2"),
							JOptionPane.WARNING_MESSAGE);
				}
			}
			catch (IOException e1) {
				noRespondeServidor();	
				log.warn(e1);
			}
			catch (InterruptedException e1) {
				log.error(e1);			
			}
		}
	}	
	
	@Override
	public void comandoCODWWNextMap(){
		try{
						
			ClienteUDPCODWW.getInstance().enviar("map_rotate");	
			//generando una espera de 500 ms...
			Thread.sleep(500);
			while(true)
				ClienteUDPCODWW.getInstance().recibir();
					
		}catch(SocketTimeoutException stex){
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mcodwwcnm1"),
					i18n.getString("mcodwwcnm2"),
					JOptionPane.WARNING_MESSAGE);	
		}
		catch (IOException e1) {
			noRespondeServidor();
			log.warn(e1);
		}
		catch (InterruptedException e1) {
			log.error(e1);			
		}
	}
	
	@Override
	public void comandoCODWWFastRestart(){
		try{
			
			ClienteUDPCODWW.getInstance().enviar("fast_restart");	
			//generando una espera de 500 ms...
			Thread.sleep(500);
			while(true)
				ClienteUDPCODWW.getInstance().recibir();					
		}catch(SocketTimeoutException stex){
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mcodwwcrm1"),
					i18n.getString("mcodwwcrm2"),
					JOptionPane.WARNING_MESSAGE);	
		}
		catch (IOException e1) {
			noRespondeServidor();
			log.warn(e1);
		}
		catch (InterruptedException e1) {
			log.error(e1);			
		}
	}
	
	/**
	 * 
	 * @param lista
	 */
	private void parsearJugadores(List<String> lista) {

		for (int i = 0; i < lista.size(); i++) {

			String cadena = lista.get(i);
			listaaux.addAll(Arrays.asList(cadena.split(" ")));				

			for (int j = 0; j < listaaux.size(); j++) {
				if (!(listaaux.get(j).toString().length() == 0)) {
					// En este caso hay q añadir el elemento, ya que no es nulo
					listaaux2.add(listaaux.get(j).trim());
				} 						
			}
			
			//Limpiamos la lista para volver a empezar con otro jugador
			listaaux.clear();
			
			//Creamos el nuevo vector que contiene el jugador				
			//y lo añadimos al vector de vectores arrayListFilas si realmente hay datos
			if(!(listaaux2.size()==0) || listaaux2.contains("null")){
							
				// Las filas menores de 9, incompletas por estar repartidas entre dos paquetes, no se añadiran
				// se deja el resultado temporal en listaaux2 y se empalma directamente con la siguiente
				// Mejora: empalmar correctamente en el campo q corresponda
				if(listaaux2.size()>LONGITUDFILA){
					
					// El nombre tiene espacios y ocupa mas de lo q debe
					ArrayList<String>aux = new ArrayList<String>(nombreSinEspacios(listaaux2));
					
					//Le quitamos el codigo de colores al nick
					String nickcolorido = aux.get(POSICIONNOMBRE);
					aux.remove(POSICIONNOMBRE);
					aux.add(POSICIONNOMBRE, nickcolorido.replaceAll("\\^[0-9]", ""));
					arrayListFilas.add(aux);
				}
				else{
					if(listaaux2.size()==LONGITUDFILA){
						//Fila normal			
						//Le quitamos el codigo de colores al nick
						String nickcolorido = listaaux2.get(POSICIONNOMBRE);
						listaaux2.remove(POSICIONNOMBRE);
						listaaux2.add(POSICIONNOMBRE, nickcolorido.replaceAll("\\^[0-9]", ""));
						arrayListFilas.add(new ArrayList<String>(listaaux2));							
					}						
				}
				//limpiamos la lista para volver a empezar con otro jugador
				listaaux2.clear();
			}					
		}
	}

	/**
	 * En la lista, al partir por espacios los nombres con espacios ocupan mas posiciones y descuadran la tabla
	 * Esta funcion es para agrupar el nombre en un solo string y meterlo en la casilla correspondiente
	 * @param lista
	 * @return
	 */
	private ArrayList<String> nombreSinEspacios(List<String> lista) {
		String nombre = "";
		ArrayList<String> listaaux = new ArrayList<String>();
		//Iteramos por la lista para juntar el nombre en un solo string. El nombre empieza en la posicion 4
		for (int i = 0; i <= lista.size() - LONGITUDFILA; i++)
			nombre = nombre.concat(lista.get(POSICIONNOMBRE + i));
		//Una vez construido iteramos hasta la posición 4 para insertarlo
		for (int j = 0; j < lista.size(); j++) {
			if (j == POSICIONNOMBRE) {
				listaaux.add(j, nombre);
				//Y ahora avanzamos tantas posiciones como espacios tiene el nombre
				j += lista.size() - LONGITUDFILA;
			} else
				listaaux.add(lista.get(j)); // y añadimos el resto de la lista
		}
		return listaaux;
	}		
	
	private void kickear_BanearTemporalmente_Jugador(int slotJugador, boolean kickeo){
		
		String num = (String) listadeJugadores.getValueAt(slotJugador, POSICIONSLOT);
		String nombrejugador = (String) listadeJugadores.getValueAt(slotJugador, POSICIONNOMBRE);
		String paquetekickeo;
		
		if(log.isDebugEnabled())
			log.debug("num del jugador: "+ listadeJugadores.getValueAt(listadeJugadores.getSelectedRow(), POSICIONSLOT));
		
		if(!panelCODWW.getChatKick().getText().isEmpty()){
			
			try {
				ClienteUDPCODWW.getInstance().enviar("say "+ usuarioCEAL + i18n.getString("mcodwwkbtj1") + nombrejugador + i18n.getString("mcodwwkbtj2") + panelCODWW.getChatKick().getText());
				// generando una espera de 500 ms...
				Thread.sleep(500);
				ClienteUDPCODWW.getInstance().recibir();
				//Esperamos 2 segundos para que se vea la razón en el chat antes de echarlo
				Thread.sleep(2000);		
				
				//Si viene un true kickeamos, sino baneamos temporalmente
				if(kickeo){					
					ClienteUDPCODWW.getInstance().enviar("clientkick " + num);
				}
				else{
					ClienteUDPCODWW.getInstance().enviar("tempBanClient " + num);
				}
				
				paquetekickeo = ClienteUDPCODWW.getInstance().recibir();
				
				if(log.isDebugEnabled())
					log.debug("Despues de kickear pakete 2: "+ paquetekickeo);
				
				if(paquetekickeo.contains("EXE_PLAYERKICKED")){
					//Jugador expulsado correctamente
					JOptionPane.showMessageDialog(jFrameCEALCON,
							i18n.getString("mcodwwkbtj3"),
							i18n.getString("mcodwwkbtj4"),
							JOptionPane.INFORMATION_MESSAGE);
					//Refrescamos para que aparezca el cambio en la tabla
					comandoRefresco();
				}
				else{
					if(paquetekickeo.contains("is not active")){
						//No hay ningún jugador en la plaza
						JOptionPane.showMessageDialog(jFrameCEALCON,
								i18n.getString("mcodwwkbtj5") +
								i18n.getString("mcodwwkbtj6") +
								i18n.getString("mcodwwkbtj7") +
								i18n.getString("mcodwwkbtj8"),
								i18n.getString("mcodwwkbtj9"),
								JOptionPane.WARNING_MESSAGE);
					}
					else{
						if(paquetekickeo.contains("Bad client slot") || paquetekickeo.contains("Bad slot number")){
							//La tabla se ha generado mal
							JOptionPane.showMessageDialog(jFrameCEALCON,
									i18n.getString("mcodwwkbtj10") +
									i18n.getString("mcodwwkbtj11") +
									i18n.getString("mcodwwkbtj12"),
									i18n.getString("mcodwwkbtj13"),
									JOptionPane.WARNING_MESSAGE);								
						}
						else{
							//La cosa se ha desmadrao y no nos llega lo del kick sino otra cosa
							JOptionPane.showMessageDialog(jFrameCEALCON,
									i18n.getString("mcodwwkbtj14") +
									i18n.getString("mcodwwkbtj15") +
									i18n.getString("mcodwwkbtj16") +
									i18n.getString("mcodwwkbtj17"),
									i18n.getString("mcodwwkbtj18"),
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}				
			} catch (IOException e1) {
				noRespondeServidor();
				log.warn(e1);
			} catch (InterruptedException e1) {
				 log.error(e1);
			}
		}
		else{
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mcodwwkbtj19") ,
					i18n.getString("mcodwwkbtj20"),
					JOptionPane.ERROR_MESSAGE);	
		}
	}	
}