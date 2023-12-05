package colegas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import mediador.IMediador;

import comandos.comandosMediador.ComandoRefresco;
import comandos.comandosMediadorBFBC2.ComandoBFBC2BaneoTemporal;
import comandos.comandosMediadorBFBC2.ComandoBFBC2ChatyChatPrivado;
import comandos.comandosMediadorBFBC2.ComandoBFBC2Kick;
import comandos.comandosMediadorBFBC2.ComandoBFBC2Move;
import comandos.comandosMediadorBFBC2.ComandoBFBC2ReiniciarMapa;
import comandos.comandosMediadorBFBC2.ComandoBFBC2SiguienteMapa;
import comandos.comandosMediadorBFBC2.ComandoBFBC2YellyYellPrivado;

public final class PanelBFBC2 extends JPanel implements IColega{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = -450758439941789772L;
	
	/** Aqui se engancha el mediador correspondiente */
	private IMediador mediador;
	
	/** Panel Norte, que contiene toda la interfaz necesaria para mover a un jugador de equipo y squad*/
	private JPanel panelNorte;	
	/** Panel que contiene toda la interfaz del cambio de equipo, es parte del panel norte*/
	private JPanel panelCambiarEquipo;
	/** Panel que contiene toda la informacion del numero de jugadores y los equipos a los que pertenecen*/
	private JPanel panelNumeroJugadores;
	/** Panel que contiene los botones Reiniciar mapa y siguiente mapa*/
	private JPanel panelBotonesMapas;
	/** Panel Sur, contiene el Refresco, el kick y el chat. El resto de paneles van añadidos directamente al JFrame*/
	private JPanel panelSur;
	/** Panel que contiene el modulo de jugadores actuales en el server y la posibilidad de kikearlos o banearlos.*/
	private JPanel panelJugadores;
	/** Panel que contiene todos los elementos de kick, baneo temporal*/
	private JPanel panelKick;
	/** Panel que contiene en una unica fila los botones de Banear, Refresco y Kickear*/
	private JPanel panelBotonesKick;
	/** Panel que contiene el modulo de chat, que permite mandar says al servidor*/
	private JPanel panelChat;
	/** Panel que contiene los botones del chat*/
	private JPanel panelBotonesChat;
	
	/** Botón que cambia de equipo a un jugador*/
	private JButton botonCambiarEquipo;
	/** Texto que indica cual es el rolldown del teamid */
	private JLabel teamId; 
	/** Roll down con todos los equipos posibles para mover a un jugador*/
	private JComboBox scrollTeamId;
	/** Texto que indica cual es el rolldown del squadid */
	private JLabel squadId;
	/** Roll down con todos las squads posibles para mover a un jugador*/
	private JComboBox scrollSquadId;
	/**Indica si el jugador debe morir*/
	private JCheckBox forzarMuerteJugador;
	/** Texto que indica donde va la expresion Jugadores actuales / maximo jugadores*/
	private JLabel textoNumeroJugadores;	
	/** Texto que indica el numero de jugadores en el equipo 1*/
	private JLabel textoJugadoresTeam1;
	/** Texto que indica el numero de jugadores en el equipo 2*/
	private JLabel textoJugadoresTeam2;
	/** Indica Jugadores actuales / maximo jugadores*/
	private JTextField numeroJugadores;	
	/** Indica el numero de jugadores en el equipo 1*/
	private JTextField jugadoresTeam1;
	/** Indica el número de jugadores en el equipo 2*/
	private JTextField jugadoresTeam2;
	/** Botón para reiniciar el mapa*/
	private JButton botonReiniciarMapa;
	/** Botón para cambiar al siguiente mapa*/
	private JButton botonSiguienteMapa;

	/** Campo de texto para introducir la frase que aparecera en el servidor*/
	private JTextField chat;
	/** Campo de texto para introducir la frase que aparecera en el servidor cuando se kickea a un jugador*/
	private JTextField chatKick;
	
	/** Boton que actualiza la lista de jugadores*/
	private JButton botonRefresco;	
	/** Boton que kickea a un jugador*/
	private JButton botonKickear;	
	/** Boton que banea temporalmente a un jugador*/
	private JButton botonBaneoTemporal;
	/** Etiqueta que indica que el scroll son minutos*/
	private JLabel minutosBaneoTemporal;
	/** Scroll que muestra los minutos elegibles al banear temporalmente un jugador*/
	private JComboBox scrollBaneoTemporal;

	/** Boton que dice en el servidor el texto tecleado en el campo de texto*/
	private JButton botonDecir;
	/** Boton que dice en el servidor el texto tecleado en el campo de texto únicamente al usuario deseado*/
	private JButton botonDecirenPrivado;
	/** Boton que implementa el comando Yell*/
	private JButton botonGritar;	
	/** Boton que implementa el comando Yell para el usuario deseado*/
	private JButton botonGritarenPrivado;
	/** Etiqueta que indica que el scroll son segundos*/
	private JLabel segundosGritar;
	/** Scroll que muestra los segundos elegibles al gritar algo por el chat*/
	private JComboBox scrollGritar;
	
	public PanelBFBC2(JFrameCEALCON jFrameCEALCON, JTable listadeJugadores, ResourceBundle i18n) {
			
		panelSur = new JPanel();
		panelSur.setLayout(new BorderLayout());
		panelSur.add(construirRefrescoyKick(i18n), BorderLayout.CENTER);
		panelSur.add(construirChat(i18n), BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		
		this.add(construirPanelNorte(i18n), BorderLayout.NORTH);
		this.add(construirJugadores(listadeJugadores), BorderLayout.CENTER);
		this.add(panelSur, BorderLayout.SOUTH);
		
		jFrameCEALCON.setContentPane(this);		
		jFrameCEALCON.validate();
		jFrameCEALCON.repaint();
	}	
	
	/**
	 * de esta forma conocemos el mediador
	 */
	public void setMediador( IMediador mediador ) {
		this.mediador = mediador;
	}
	
	private Component construirPanelNorte(ResourceBundle i18n){
		
		panelNorte = new JPanel();
		panelCambiarEquipo = new JPanel();
		panelNumeroJugadores = new JPanel();
		panelBotonesMapas = new JPanel();
		
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 10, 3);
		panelCambiarEquipo.setLayout(fl);
		panelNumeroJugadores.setLayout(fl);
		panelBotonesMapas.setLayout(fl);
					
		GridLayout gl = new GridLayout(3,1,0,0);		
		panelNorte.setLayout(gl);			
		
		botonCambiarEquipo = new ComandoBFBC2Move(i18n.getString("pbfbc2cpn1"));
		teamId = new JLabel(i18n.getString("pbfbc2cpn2"));
		scrollTeamId = new JComboBox(new String[]{"", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"});
		squadId = new JLabel(i18n.getString("pbfbc2cpn3"));
		scrollSquadId = new JComboBox(new String[]{"", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16"});
		forzarMuerteJugador = new JCheckBox(i18n.getString("pbfbc2cpn4"));
		botonReiniciarMapa = new ComandoBFBC2ReiniciarMapa(i18n.getString("pbfbc2cpn8"));
		botonSiguienteMapa = new ComandoBFBC2SiguienteMapa(i18n.getString("pbfbc2cpn9"));
		
		textoNumeroJugadores = new JLabel(i18n.getString("pbfbc2cpn5"));		
		textoJugadoresTeam1 = new JLabel(i18n.getString("pbfbc2cpn6"));
		textoJugadoresTeam2 = new JLabel(i18n.getString("pbfbc2cpn7"));
		numeroJugadores = new JTextField(5);		
		jugadoresTeam1 = new JTextField(2);
		jugadoresTeam2 = new JTextField(2);
		numeroJugadores.setBorder(BorderFactory.createEmptyBorder());
		jugadoresTeam1.setBorder(BorderFactory.createEmptyBorder());
		jugadoresTeam2.setBorder(BorderFactory.createEmptyBorder());
		numeroJugadores.setEditable(false);		
		jugadoresTeam1.setEditable(false);
		jugadoresTeam2.setEditable(false);
		numeroJugadores.setOpaque(false);
		jugadoresTeam1.setOpaque(false);
		jugadoresTeam2.setOpaque(false);
		
		panelCambiarEquipo.add(botonCambiarEquipo);
		panelCambiarEquipo.add(teamId);
		panelCambiarEquipo.add(scrollTeamId);
		panelCambiarEquipo.add(squadId);
		panelCambiarEquipo.add(scrollSquadId);
		panelCambiarEquipo.add(forzarMuerteJugador);		
		
		panelNumeroJugadores.add(textoNumeroJugadores);
		panelNumeroJugadores.add(numeroJugadores);			
		panelNumeroJugadores.add(textoJugadoresTeam1);
		panelNumeroJugadores.add(jugadoresTeam1);
		panelNumeroJugadores.add(textoJugadoresTeam2);
		panelNumeroJugadores.add(jugadoresTeam2);
		
		panelBotonesMapas.add(botonReiniciarMapa);
		panelBotonesMapas.add(botonSiguienteMapa);
		
		panelNorte.add(panelBotonesMapas);
		panelNorte.add(panelCambiarEquipo);
		panelNorte.add(panelNumeroJugadores);		
		
		return panelNorte;
	}
	
	private Component construirJugadores(JTable listadeJugadores){	
		
		panelJugadores = new JPanel();
		panelJugadores.setLayout(new BorderLayout());
		panelJugadores.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(
																			1,// arriba
																			10,//izquierda
																			1,// abajo
																			10)// derecha
										, BorderFactory.createTitledBorder(new LineBorder(Color.GRAY),"Battlefield Bad Company 2")
										));		
		
		panelJugadores.add(new JScrollPane(listadeJugadores));
		
		return panelJugadores;		
	}
	
	private Component construirRefrescoyKick(ResourceBundle i18n){
		
		panelKick = new JPanel();
		panelBotonesKick = new JPanel();	
		
		panelKick.setLayout(new BorderLayout(1, 5));					
		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 15, 1);		
		panelBotonesKick.setLayout(fl);	
		
		botonRefresco = new ComandoRefresco(i18n.getString("pbfbc2crk1"));	
		botonRefresco.setToolTipText(i18n.getString("pbfbc2crk6"));
		
		chatKick = new JTextField(100);	
		
		botonKickear = new ComandoBFBC2Kick(i18n.getString("pbfbc2crk2"));					
		botonBaneoTemporal = new ComandoBFBC2BaneoTemporal(i18n.getString("pbfbc2crk3"));
		minutosBaneoTemporal = new JLabel(i18n.getString("pbfbc2crk4"));
		scrollBaneoTemporal = new JComboBox(new String[]{"", "5", "10", "15", "20", "30", "45", "59"});
		
		panelBotonesKick.add(botonKickear);
		panelBotonesKick.add(botonBaneoTemporal);
		panelBotonesKick.add(scrollBaneoTemporal);
		panelBotonesKick.add(minutosBaneoTemporal);		
		
		panelKick.add(botonRefresco, BorderLayout.NORTH);
		panelKick.add(chatKick);
		panelKick.add(panelBotonesKick, BorderLayout.SOUTH);
		
		panelKick.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(
				1,// arriba
				10,//izquierda
				1,// abajo
				10)// derecha
				, BorderFactory.createTitledBorder(new LineBorder(Color.GRAY), i18n.getString("pbfbc2crk5"))
				));	
		return panelKick;
	}
	
	private Component construirChat(ResourceBundle i18n){
		
		panelChat = new JPanel();	
		panelBotonesChat = new JPanel();
		
		panelChat.setLayout(new BorderLayout());		
		FlowLayout flayout = new FlowLayout(FlowLayout.CENTER, 10, 1);
		panelBotonesChat.setLayout(flayout);
		
		chat = new JTextField(100);	
		
		botonDecir = new ComandoBFBC2ChatyChatPrivado(i18n.getString("pbfbc2cc1"), false);			
		botonDecirenPrivado = new ComandoBFBC2ChatyChatPrivado(i18n.getString("pbfbc2cc2"), true);	
		botonGritar = new ComandoBFBC2YellyYellPrivado(i18n.getString("pbfbc2cc3"), false);	
		botonGritarenPrivado = new ComandoBFBC2YellyYellPrivado(i18n.getString("pbfbc2cc4"), true);
		
		segundosGritar = new JLabel(i18n.getString("pbfbc2cc5"), SwingConstants.LEFT);
		scrollGritar = new JComboBox(new String[]{"", "5", "10", "15", "20", "30", "45", "59"});
		
		panelBotonesChat.add(botonDecirenPrivado);
		panelBotonesChat.add(botonDecir);
		panelBotonesChat.add(botonGritarenPrivado);
		panelBotonesChat.add(botonGritar);
		panelBotonesChat.add(scrollGritar);
		panelBotonesChat.add(segundosGritar);		
		
		panelChat.add(chat);
		panelChat.add(panelBotonesChat, BorderLayout.SOUTH);
		
		panelChat.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(
																			1,// arriba
																			10,//izquierda
																			1,// abajo
																			10)// derecha
										, BorderFactory.createTitledBorder(new LineBorder(Color.GRAY), i18n.getString("pbfbc2cc6"))
										));		
		return panelChat;
	}
	
	public void añadirOyentes(){
		
		botonReiniciarMapa.addActionListener(mediador);
		botonSiguienteMapa.addActionListener(mediador);
		
		botonCambiarEquipo.addActionListener(mediador);
		botonRefresco.addActionListener(mediador); 
		botonKickear.addActionListener(mediador); 	
		botonBaneoTemporal.addActionListener(mediador);
		
		botonDecirenPrivado.addActionListener(mediador);
		botonDecir.addActionListener(mediador);	
		botonGritarenPrivado.addActionListener(mediador);
		botonGritar.addActionListener(mediador);
	}
	
	public JTextField getChat() {
		return chat;
	}

	public JTextField getChatKick() {
		return chatKick;
	}	
	
	public JComboBox getScrollBaneoTemporal() {
		return scrollBaneoTemporal;
	}

	public JComboBox getScrollGritar() {
		return scrollGritar;
	}

	public JCheckBox getForzarMuerteJugador() {
		return forzarMuerteJugador;
	}

	public JComboBox getScrollSquadId() {
		return scrollSquadId;
	}

	public JComboBox getScrollTeamId() {
		return scrollTeamId;
	}

	public JTextField getJugadoresTeam1() {
		return jugadoresTeam1;
	}

	public JTextField getJugadoresTeam2() {
		return jugadoresTeam2;
	}

	public JTextField getNumeroJugadores() {
		return numeroJugadores;
	}	
}