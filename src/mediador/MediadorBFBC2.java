package mediador;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableRowSorter;

import colegas.PanelBFBC2;

import componentes.componentesBFBC2.ClienteTCP_BFBC2;
import componentes.componentesBFBC2.MatrixTableModel;

import excepciones.DesincronizacionSecuencia;


/**
 * MediadorBFBC2. Esta clase contiene la lógica particular para BFBC2
 * @author Alatriste
 * @version 0.3
 */
public final class MediadorBFBC2 extends Mediador{
	
	/** Este es el panel particular de la consola de BFBC2*/
	private PanelBFBC2 panelBFBC2;	
	/** Necesitamos guardarlo para la traducción del texto de los comandos*/
	private ResourceBundle i18n;
	
	/** El literal esperado del server, significa que todo ha ido bien. Renombrado a TODOOK para que no coincida con la enumeracion*/
	private static final String TODOOK = "OK";
	
	/** Posición del nombre dentro de la tabla*/
	private static final int POSICIONNOMBRE = 1;
	/** Posición del guid dentro de la tabla*/
	private static final int POSICIONGUID = 2;	
	/** Posición del equipo dentro de la tabla*/
	private static final int POSICIONTEAM = 3;
	
	/** Maxima longitud del mensaje para los comandos de chat y kick*/
	private static final int MAXIMALONGITUDMENSAJE100 = 100;
	/** Maxima longitud del mensaje para el comando banear temporalmente*/
	private static final int MAXIMALONGITUDMENSAJE80 = 80;	
	
	/** La longitud total de las filas de los jugadores, en el caso del BFBC2 es 9*/
	private static final int LONGITUDFILAJUGADOR = 9;
	/** Los Strings iniciales del comando refresco que no nos interesan y queremos descartar*/
	private static final int DESECHABLES = 12;
	/** La posición de la lista donde está el número de jugadores*/
	private static final int NUMEROJUGADORES = 11;
	
	/** El modelo que utiliza la tabla de jugadores*/
	private MatrixTableModel matrixTableModel;
	/** La tabla de los jugadores*/ 
	private JTable listadeJugadores;	
	/** Las columnas de BFBC2, array de inicialización fija, no cambia en toda la ejecución de la apliación*/
	private String[] columnas;
	/** La matriz de Strings que contiene todos los datos de los jugadores actuales en el servidor*/
	private String[][] matrixJugadores;	
	/** La respuesta del server convertida en array de Strings */
	private String[] jugadoresSinFormato;
	
	protected MediadorBFBC2(ResourceBundle i18n){
		
		this.i18n = i18n;
		
		columnas = new String[]{"clanTag", "name", "guid", "teamId", "squadId", "kills", "deaths", "score", "ping"};
		
		matrixTableModel = new MatrixTableModel(columnas, new String[0][LONGITUDFILAJUGADOR]);
		listadeJugadores = new JTable(matrixTableModel);		
		listadeJugadores.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listadeJugadores.setRowSorter(new TableRowSorter<MatrixTableModel>(matrixTableModel));
		listadeJugadores.getColumnModel().getColumn(POSICIONNOMBRE).setPreferredWidth(200);
		listadeJugadores.getColumnModel().getColumn(POSICIONGUID).setPreferredWidth(200);		
		
		panelBFBC2 = new PanelBFBC2(jFrameCEALCON, listadeJugadores, i18n);
	}
	
	@Override
	public void registrarPaneldeJuego(IMediador mediador){	
		
		panelBFBC2.setMediador(mediador);
		panelBFBC2.añadirOyentes();
	}	
	
	private enum resultadosComandoMove {OK, InvalidArguments, InvalidTeamId, InvalidSquadId, InvalidPlayerName, InvalidForceKill
									   , PlayerNotDead, SetTeamFailed, SetSquadFailed}
	
	@Override
	public void comandoBFBC2Move(){
		
		if(chequear_tablaactualizada_jugadorseleccionado(listadeJugadores)){
			
			//jugador seleccionado
			String nombre = (String)listadeJugadores.getValueAt(listadeJugadores.getSelectedRow(), POSICIONNOMBRE);
			String equipoDestino = (String)panelBFBC2.getScrollTeamId().getSelectedItem();
			String patrullaDestino = (String)panelBFBC2.getScrollSquadId().getSelectedItem();
			boolean forzarMuerte = panelBFBC2.getForzarMuerteJugador().isSelected();
			String textoParaEnviar;
			String respuestaServer;
			String[] listaRespuestaServer;		
			
			if(panelBFBC2.getScrollTeamId().getSelectedIndex() != 0 && panelBFBC2.getScrollSquadId().getSelectedIndex() != 0){
				try{				
					textoParaEnviar = "admin.movePlayer" + ClienteTCP_BFBC2.SEP + nombre + ClienteTCP_BFBC2.SEP	 
									  + equipoDestino + ClienteTCP_BFBC2.SEP + patrullaDestino + ClienteTCP_BFBC2.SEP + forzarMuerte;
					
					respuestaServer = enviarYRecibir(textoParaEnviar);
					
					listaRespuestaServer = respuestaServer.split("\" \"");	
					
					resultadosComandoMove rCM = resultadosComandoMove.valueOf(listaRespuestaServer[0]);
					
					switch(rCM) {
						case OK:
							JOptionPane.showMessageDialog(jFrameCEALCON,
									i18n.getString("mbfbc2cm1") + nombre + i18n.getString("mbfbc2cm2") + equipoDestino +
									i18n.getString("mbfbc2cm3") + patrullaDestino,
									i18n.getString("mbfbc2cm4"),
									JOptionPane.INFORMATION_MESSAGE);	
							//Refrescamos para que aparezca el cambio en la tabla
							comandoRefresco();
							break;	
						case InvalidArguments:
						case InvalidPlayerName:
						case InvalidForceKill:
							JOptionPane.showMessageDialog(jFrameCEALCON,
									i18n.getString("mbfbc2cm5") +
									i18n.getString("mbfbc2cm6"),
									i18n.getString("mbfbc2cm7"),
									JOptionPane.ERROR_MESSAGE);								
							if(log.isDebugEnabled())
								log.debug("ERROR EN EL COMANDO MOVE" + respuestaServer);
							break;
						case InvalidTeamId:
							JOptionPane.showMessageDialog(jFrameCEALCON,
									i18n.getString("mbfbc2cm8") + equipoDestino+ i18n.getString("mbfbc2cm9"),
									i18n.getString("mbfbc2cm10"),
									JOptionPane.WARNING_MESSAGE);	
							break;
						case InvalidSquadId:
							JOptionPane.showMessageDialog(jFrameCEALCON,
									i18n.getString("mbfbc2cm11") + patrullaDestino + i18n.getString("mbfbc2cm12"),
									i18n.getString("mbfbc2cm13"),
									JOptionPane.WARNING_MESSAGE);
							break;
						case PlayerNotDead:
							JOptionPane.showMessageDialog(jFrameCEALCON,
									i18n.getString("mbfbc2cm14") + nombre + i18n.getString("mbfbc2cm15"),
									i18n.getString("mbfbc2cm16"),
									JOptionPane.WARNING_MESSAGE);
							break;
						case SetTeamFailed:
							JOptionPane.showMessageDialog(jFrameCEALCON,
									i18n.getString("mbfbc2cm17") + nombre + i18n.getString("mbfbc2cm18") + equipoDestino,
									i18n.getString("mbfbc2cm19"),
									JOptionPane.WARNING_MESSAGE);
							break;
						case SetSquadFailed:
							JOptionPane.showMessageDialog(jFrameCEALCON,
									i18n.getString("mbfbc2cm20") + nombre + i18n.getString("mbfbc2cm21") + patrullaDestino,
									i18n.getString("mbfbc2cm22"),
									JOptionPane.WARNING_MESSAGE);
							break;	
						default:
							if(log.isDebugEnabled())
								log.debug("ERROR EN EL COMANDO MOVE" + respuestaServer);
							break;
					}
				} catch (IOException e1) {
					noRespondeServidor();
					log.warn(e1);
				} catch (DesincronizacionSecuencia de) {
					desincronizacionSecuencia();
					log.error(de);
				}
			}
			else{
				JOptionPane.showMessageDialog(jFrameCEALCON,
						i18n.getString("mbfbc2cm23") + i18n.getString("mbfbc2cm24"),
						i18n.getString("mbfbc2cm25"),
						JOptionPane.WARNING_MESSAGE);	
			}
		}			
	}	
	
	@Override
	public void comandoRefresco(){		
						
		String textoParaEnviar;
		String respuestaServer;
		int[] numerosJugadores;
		String[] maximoNumeroJugadores;
		
		viejo = System.currentTimeMillis();		

		try{
			textoParaEnviar = "admin.listPlayers" + ClienteTCP_BFBC2.SEP + "all";
			
			respuestaServer = enviarYRecibir(textoParaEnviar);
			
			if(log.isDebugEnabled() && !respuestaServer.contains(TODOOK)){
				log.debug("ERROR EN EL COMANDO REFRESCO " + respuestaServer);
			}		
			
			jugadoresSinFormato = respuestaServer.split("\" \"");				
			numerosJugadores = parsearJugadores(jugadoresSinFormato);
			
			textoParaEnviar = "vars.currentPlayerLimit";			
			respuestaServer = enviarYRecibir(textoParaEnviar);
			
			if(log.isDebugEnabled() && !respuestaServer.contains(TODOOK)){
				log.debug("ERROR EN EL COMANDO REFRESCO " + respuestaServer);
			}
			
			maximoNumeroJugadores = respuestaServer.split("\" \"");	
					
			panelBFBC2.getNumeroJugadores().setText(String.valueOf(numerosJugadores[0]) + "/" + maximoNumeroJugadores[1]);
			panelBFBC2.getJugadoresTeam1().setText(String.valueOf(numerosJugadores[1]));
			panelBFBC2.getJugadoresTeam2().setText(String.valueOf(numerosJugadores[2]));
			
			matrixTableModel.removeAllRows();
			matrixTableModel.addAllRows(matrixJugadores);				
			matrixTableModel.fireTableDataChanged();
		} catch (IOException e1) {
			noRespondeServidor();	
			log.warn(e1);
		} catch (DesincronizacionSecuencia e) {
			log.error(e);
		}
	}
	
	@Override
	public void comandoBFBC2Kick(){
		
		if (chequear_tablaactualizada_jugadorseleccionado(listadeJugadores)) {
			//jugador seleccionado
			String nombre = (String) listadeJugadores.getValueAt(listadeJugadores.getSelectedRow(), POSICIONNOMBRE);
			String mensajeRadiado = panelBFBC2.getChatKick().getText();
			String textoParaEnviar;
			String respuestaServer;			
						
			if(!mensajeRadiado.isEmpty()){								
				if(mensajeRadiado.length() < MAXIMALONGITUDMENSAJE100){
					try{
						textoParaEnviar = "admin.kickPlayer" + ClienteTCP_BFBC2.SEP + nombre + ClienteTCP_BFBC2.SEP + usuarioCEAL + ": " + mensajeRadiado;
						
						respuestaServer = enviarYRecibir(textoParaEnviar);
						
						if(respuestaServer.contains(TODOOK)){
							JOptionPane.showMessageDialog(jFrameCEALCON,
									i18n.getString("mbfbc2ck1"),
									i18n.getString("mbfbc2ck2"),
									JOptionPane.INFORMATION_MESSAGE);
							//Refrescamos para que aparezca el cambio en la tabla
							comandoRefresco();
						}
						else{
							if(respuestaServer.contains("PlayerNotFound")){
								JOptionPane.showMessageDialog(jFrameCEALCON,
										i18n.getString("mbfbc2ck3") +
										i18n.getString("mbfbc2ck4") +
										i18n.getString("mbfbc2ck5") +
										i18n.getString("mbfbc2ck6"),
										i18n.getString("mbfbc2ck7"),
										JOptionPane.WARNING_MESSAGE);
							}
							else{
								JOptionPane.showMessageDialog(jFrameCEALCON,
										i18n.getString("mbfbc2ck8") +
										i18n.getString("mbfbc2ck9"),
										i18n.getString("mbfbc2ck10"),
										JOptionPane.ERROR_MESSAGE);								
								if(log.isDebugEnabled())
									log.debug("ERROR EN EL COMANDO KICK" + respuestaServer);
							}
						}
					} catch (IOException e1) {
						noRespondeServidor();
						log.warn(e1);
					} catch (DesincronizacionSecuencia de) {
						desincronizacionSecuencia();
						log.error(de);
					} 	
				}
				else{
					JOptionPane.showMessageDialog(jFrameCEALCON,
							i18n.getString("mbfbc2ck11") + MAXIMALONGITUDMENSAJE100 + i18n.getString("mbfbc2ck12"),
							i18n.getString("mbfbc2ck13"),
							JOptionPane.WARNING_MESSAGE);
				}			
			}				
			else{
				JOptionPane.showMessageDialog(jFrameCEALCON,
						i18n.getString("mbfbc2ck14") ,
						i18n.getString("mbfbc2ck15"),
						JOptionPane.WARNING_MESSAGE);
			}				
		}				
	}
	
	@Override
	public void comandoBFBC2BaneoTemporal(){
		
		if (chequear_tablaactualizada_jugadorseleccionado(listadeJugadores)) {
			//guid del jugador a banear
			String guid = (String) listadeJugadores.getValueAt(listadeJugadores.getSelectedRow(), POSICIONGUID);			
			String mensajeRadiado = panelBFBC2.getChatKick().getText();
			String textoParaEnviar;
			String respuestaServer;
			//Tiempo de baneo en minutos, lo multiplicaremos por 60 para sacar los segundos
			String minutosBaneo = (String)panelBFBC2.getScrollBaneoTemporal().getSelectedItem();
						
			if(!mensajeRadiado.isEmpty() && panelBFBC2.getScrollBaneoTemporal().getSelectedIndex() != 0){					
				if(mensajeRadiado.length() < MAXIMALONGITUDMENSAJE80){
					try{
						int segundosBaneo = Integer.parseInt(minutosBaneo) * 60;
					
						textoParaEnviar = "banList.add"+ ClienteTCP_BFBC2.SEP + "guid" + ClienteTCP_BFBC2.SEP + guid + ClienteTCP_BFBC2.SEP 
											+ "seconds" + ClienteTCP_BFBC2.SEP + segundosBaneo + ClienteTCP_BFBC2.SEP + usuarioCEAL + ": " + mensajeRadiado;
						
						respuestaServer = enviarYRecibir(textoParaEnviar);
						
						if(respuestaServer.contains(TODOOK)){
							JOptionPane.showMessageDialog(jFrameCEALCON,
									i18n.getString("mbfbc2bt1") + minutosBaneo + i18n.getString("mbfbc2bt2"),
									i18n.getString("mbfbc2bt3"),
									JOptionPane.INFORMATION_MESSAGE);	
							//Refrescamos para que aparezca el cambio en la tabla
							comandoRefresco();
						}
						else{
							if(respuestaServer.contains("BanListFull")){
								JOptionPane.showMessageDialog(jFrameCEALCON,
										i18n.getString("mbfbc2bt4") +
										i18n.getString("mbfbc2bt5") +
										i18n.getString("mbfbc2bt6"),										
										i18n.getString("mbfbc2bt7"),
										JOptionPane.WARNING_MESSAGE);
							}
							else{
								JOptionPane.showMessageDialog(jFrameCEALCON,
										i18n.getString("mbfbc2bt8") +
										i18n.getString("mbfbc2bt9"),
										i18n.getString("mbfbc2bt10"),
										JOptionPane.ERROR_MESSAGE);								
								if(log.isDebugEnabled())
									log.debug("ERROR EN EL COMANDO BANEAR" + respuestaServer);
							}
						}
					} catch (IOException e1) {
						noRespondeServidor();
						log.warn(e1);
					} catch (DesincronizacionSecuencia de) {
						desincronizacionSecuencia();
						log.error(de);
					}
				}
				else{
					JOptionPane.showMessageDialog(jFrameCEALCON,
							i18n.getString("mbfbc2bt11") + MAXIMALONGITUDMENSAJE80 + i18n.getString("mbfbc2bt12"),
							i18n.getString("mbfbc2bt13"),
							JOptionPane.WARNING_MESSAGE);
				}				
			}
			else{
				JOptionPane.showMessageDialog(jFrameCEALCON,
						i18n.getString("mbfbc2bt14") + i18n.getString("mbfbc2bt15"),
						i18n.getString("mbfbc2bt16"),
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}	
	
	@Override
	public void comandoBFBC2ChatyChatPrivado(boolean privado){
		
		if(!privado || (privado && chequear_tablaactualizada_jugadorseleccionado(listadeJugadores))){		
			
			String mensajeRadiado = panelBFBC2.getChat().getText();
			String textoParaEnviar;
			String respuestaServer;
			
			if(!mensajeRadiado.isEmpty()){				
				if(mensajeRadiado.length() < MAXIMALONGITUDMENSAJE100){
					try{
						if(privado){
							//jugador seleccionado
							String nombre = (String) listadeJugadores.getValueAt(listadeJugadores.getSelectedRow(), POSICIONNOMBRE);
							textoParaEnviar = "admin.say" + ClienteTCP_BFBC2.SEP + usuarioCEAL + ": " + mensajeRadiado 
								+ ClienteTCP_BFBC2.SEP + "player" + ClienteTCP_BFBC2.SEP + nombre;
						}
						else{
							textoParaEnviar = "admin.say" + ClienteTCP_BFBC2.SEP + usuarioCEAL + ": " + mensajeRadiado + ClienteTCP_BFBC2.SEP + "all";
						}
						
						respuestaServer = enviarYRecibir(textoParaEnviar);
						
						if(log.isDebugEnabled() && !respuestaServer.contains(TODOOK)){
							log.debug("ERROR EN EL COMANDO CHAT PRIVADO" + respuestaServer);
						}
					} catch (IOException e1) {
						noRespondeServidor();	
						log.warn(e1);
					} catch (DesincronizacionSecuencia de){
						desincronizacionSecuencia();
						log.error(de);
					}
				}
				else{
					JOptionPane.showMessageDialog(jFrameCEALCON,
							i18n.getString("mbfbc2ccp1") + MAXIMALONGITUDMENSAJE100 + i18n.getString("mbfbc2ccp2"),
							i18n.getString("mbfbc2ccp3"),
							JOptionPane.WARNING_MESSAGE);
				}				
			}
			else{
				JOptionPane.showMessageDialog(jFrameCEALCON,
						i18n.getString("mbfbc2ccp4") ,
						i18n.getString("mbfbc2ccp5"),
						JOptionPane.WARNING_MESSAGE);
			}			
		}
	}	
		
	@Override
	public void comandoBFBC2YellyYellPrivado(boolean privado){
		
		if(!privado || (privado && chequear_tablaactualizada_jugadorseleccionado(listadeJugadores))){
			
			String mensajeRadiado = panelBFBC2.getChat().getText();
			//Tiempo de baneo en minutos, lo multiplicaremos por 60 para sacar los segundos
			String segundosGrito = (String)panelBFBC2.getScrollGritar().getSelectedItem();
			String textoParaEnviar;
			String respuestaServer;		
							
			if(!mensajeRadiado.isEmpty() && panelBFBC2.getScrollGritar().getSelectedIndex() != 0){				
				if(mensajeRadiado.length() < MAXIMALONGITUDMENSAJE100){
					try{
						int milisegundosGrito = Integer.parseInt(segundosGrito) * 1000;
						if(privado){
							//jugador seleccionado
							String nombre = (String) listadeJugadores.getValueAt(listadeJugadores.getSelectedRow(), POSICIONNOMBRE);
							textoParaEnviar = "admin.yell" + ClienteTCP_BFBC2.SEP + usuarioCEAL + ": " + mensajeRadiado + ClienteTCP_BFBC2.SEP 
							+ milisegundosGrito + ClienteTCP_BFBC2.SEP + "player" + ClienteTCP_BFBC2.SEP + nombre;
						}
						else{
							textoParaEnviar = "admin.yell" + ClienteTCP_BFBC2.SEP + usuarioCEAL + ": " + mensajeRadiado + ClienteTCP_BFBC2.SEP 
										+ milisegundosGrito + ClienteTCP_BFBC2.SEP + "all";
						}
						
						respuestaServer = enviarYRecibir(textoParaEnviar);
						
						if(log.isDebugEnabled() && !respuestaServer.contains(TODOOK)){
							log.debug("ERROR EN EL COMANDO GRITAR" + respuestaServer);
						}
					} catch (IOException e1) {
						noRespondeServidor();	
						log.warn(e1);
					} catch (DesincronizacionSecuencia de){
						desincronizacionSecuencia();
						log.error(de);
					} 	
				}
				else{
					JOptionPane.showMessageDialog(jFrameCEALCON,
							i18n.getString("mbfbc2cyyp1") + MAXIMALONGITUDMENSAJE100 + i18n.getString("mbfbc2cyyp2"),
							i18n.getString("mbfbc2cyyp3"),
							JOptionPane.WARNING_MESSAGE);
				}			
			}
			else{
				JOptionPane.showMessageDialog(jFrameCEALCON,
						i18n.getString("mbfbc2cyyp4") + i18n.getString("mbfbc2cyyp5") ,
						i18n.getString("mbfbc2cyyp6"),
						JOptionPane.WARNING_MESSAGE);
			}	
		}
	}
	
	@Override
	public void comandoBFBC2NextMap(){
		
		String respuestaServer;
		
		try{
			respuestaServer = enviarYRecibir("admin.runNextLevel");
			
			if(respuestaServer.contains(TODOOK)){
				JOptionPane.showMessageDialog(jFrameCEALCON,
						i18n.getString("mbfbc2cnm1"),
						i18n.getString("mbfbc2cnm2"),
						JOptionPane.INFORMATION_MESSAGE);	
			}
			else{
				if(log.isDebugEnabled())			
					log.debug("ERROR EN EL COMANDO NEXT MAP" + respuestaServer);
			}			
		} catch (IOException e1) {
			noRespondeServidor();	
			log.warn(e1);
		} catch (DesincronizacionSecuencia de){
			desincronizacionSecuencia();
			log.error(de);
		} 		
	}	
	
	@Override
	public void comandoBFBC2RestartMap(){
		
		String respuestaServer;
		
		try{
			respuestaServer = enviarYRecibir("admin.restartMap");
			
			if(respuestaServer.contains(TODOOK)){
				JOptionPane.showMessageDialog(jFrameCEALCON,
						i18n.getString("mbfbc2crm1"),
						i18n.getString("mbfbc2crm2"),
						JOptionPane.INFORMATION_MESSAGE);	
			}
			else{
				if(log.isDebugEnabled())			
					log.debug("ERROR EN EL COMANDO RESTART MAP" + respuestaServer);
			}			
		} catch (IOException e1) {
			noRespondeServidor();	
			log.warn(e1);
		} catch (DesincronizacionSecuencia de){
			desincronizacionSecuencia();
			log.error(de);
		} 		
	}
	
	private int[] parsearJugadores(String[] lista) {	
		
		//Contadores de jugadores en los distintos equipos		
		int jugadoresTeam1 = 0;
		int jugadoresTeam2 = 0;
		/*Calculamos las filas de jugadores que hay. Es la longitud total menos los items de la lista que no queremos (OK, 
		  número de items en cabecera, la cabecera (9 elementos) y el numero de jugadores. Eso nos da un numero divisible entre la longitud
		  de la fila del jugador (9) y con una division sacamos las iteraciones que hemos de hacer y las filas de la matriz de jugadores*/
		int filas = (lista.length - DESECHABLES)/LONGITUDFILAJUGADOR;
		matrixJugadores = new String[filas][LONGITUDFILAJUGADOR];
		
		for(int i = 0; i < filas; i++){
			for(int j = 0; j < LONGITUDFILAJUGADOR; j++){
				//Iteramos por la lista, saltandonos los elementos iniciales que no queremos (j + DESECHABLES) y recorriendo en tramos de 9 elementos (i * LONGITUDFILAJUGADOR)
				matrixJugadores[i][j] = lista[(j + DESECHABLES) + (i * LONGITUDFILAJUGADOR)];
				if(j == POSICIONTEAM){
					switch(Integer.parseInt(matrixJugadores[i][j])){
						case 1:
							jugadoresTeam1++;
							break;
						case 2:
							jugadoresTeam2++;
							break;
					}
				}
			}			
		}	
		return new int[]{Integer.parseInt(lista[NUMEROJUGADORES]), jugadoresTeam1, jugadoresTeam2};
	}	
	
	private String enviarYRecibir(String textoParaEnviar) throws IOException, DesincronizacionSecuencia{
		ClienteTCP_BFBC2.getInstance().enviar(textoParaEnviar);				
		ClienteTCP_BFBC2.getInstance().esperandoaRecibir(ClienteTCP_BFBC2.ESPERA);
		return ClienteTCP_BFBC2.getInstance().recibir();	
	}
}