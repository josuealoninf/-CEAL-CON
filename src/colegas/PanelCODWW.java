package colegas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import mediador.IMediador;

import comandos.comandosMediador.ComandoRefresco;
import comandos.comandosMediadorCODWW.ComandoCODWWChat;
import comandos.comandosMediadorCODWW.ComandoCODWWChatPrivado;
import comandos.comandosMediadorCODWW.ComandoCODWWFastRestart;
import comandos.comandosMediadorCODWW.ComandoCODWWKickeo_BaneoTemporal;
import comandos.comandosMediadorCODWW.ComandoCODWWNextMap;

public final class PanelCODWW extends JPanel implements IColega{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = -450758439941789772L;
	
	/** Aqui se engancha el mediador correspondiente */
	private IMediador mediador;
	
	/** panel Norte, contiene los botones de siguiente mapa y botonfast_restart*/
	private JPanel panelNorte;
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

	/** Campo de texto para introducir la frase que aparecera en el servidor*/
	private JTextArea chat;
	/** Campo de texto para introducir la frase que aparecera en el servidor cuando se kickea a un jugador*/
	private JTextField chatKick;
	/** Scroll asocidado al Chat*/
	private JScrollPane scrollChat;

	/** Botón que reinicia el mapa*/
	private JButton botonfast_restart;
	/** Botón que cambia al siguiente mapa*/
	private JButton botonSiguienteMapa;
	/** Boton que actualiza la lista de jugadores*/
	private JButton botonRefresco;	
	/** Boton que kickea a un jugador*/
	private JButton botonKickear;	
	/** Boton que banea temporalmente a un jugador*/
	private JButton botonBaneoTemporal;

	/** Boton que dice en el servidor el texto tecleado en el campo de texto*/
	private JButton botonDecir;
	/** Boton que dice en el servidor el texto tecleado en el campo de texto únicamente al usuario deseado*/
	private JButton botonDecirenPrivado;
	
	public PanelCODWW(JFrameCEALCON jFrameCEALCON, JTable listadeJugadores, ResourceBundle i18n) {
			
		panelSur = new JPanel();
		panelSur.setLayout(new BorderLayout());
		panelSur.add(construirRefrescoyKick(i18n), BorderLayout.CENTER);
		panelSur.add(construirChat(i18n), BorderLayout.SOUTH);
		
		this.setLayout(new BorderLayout());
		this.add(construirJugadores(listadeJugadores), BorderLayout.CENTER);
		this.add(panelSur, BorderLayout.SOUTH);
		this.add(construirPanelNorte(i18n), BorderLayout.NORTH);
		
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
		panelNorte.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 1));
		
		botonfast_restart = new ComandoCODWWFastRestart(i18n.getString("pcodwwcpn1"));
		botonSiguienteMapa = new ComandoCODWWNextMap(i18n.getString("pcodwwcpn2"));
		
		panelNorte.add(botonfast_restart);
		panelNorte.add(botonSiguienteMapa);
		
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
										, BorderFactory.createTitledBorder(new LineBorder(Color.GRAY),"Call Of Duty: World at War")
										));		
		
		panelJugadores.add(new JScrollPane(listadeJugadores));
		
		return panelJugadores;		
	}
	
	private Component construirRefrescoyKick(ResourceBundle i18n){
		
		panelKick = new JPanel();
		panelKick.setLayout(new BorderLayout(1, 5));
		
		panelBotonesKick = new JPanel();				
		FlowLayout flayout = new FlowLayout();
		flayout.setHgap(20);
		panelBotonesKick.setLayout(flayout);
		
		botonRefresco = new ComandoRefresco(i18n.getString("pcodwwcrk1"));	
		
		chatKick = new JTextField(100);			
			
		botonKickear = new ComandoCODWWKickeo_BaneoTemporal(i18n.getString("pcodwwcrk2"), true);		
		botonBaneoTemporal = new ComandoCODWWKickeo_BaneoTemporal(i18n.getString("pcodwwcrk3"), false);
						
		panelBotonesKick.add(botonKickear);
		panelBotonesKick.add(botonBaneoTemporal);
		
		panelKick.add(botonRefresco, BorderLayout.NORTH);
		panelKick.add(chatKick);
		panelKick.add(panelBotonesKick, BorderLayout.SOUTH);
		
		panelKick.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(
				1,// arriba
				10,//izquierda
				1,// abajo
				10)// derecha
				, BorderFactory.createTitledBorder(new LineBorder(Color.GRAY), i18n.getString("pcodwwcrk1"))
				));	
		return panelKick;
	}
	
	private Component construirChat(ResourceBundle i18n){
		
		panelChat = new JPanel();		
		panelChat.setLayout(new BorderLayout());
		
		panelBotonesChat = new JPanel();
		FlowLayout flayout = new FlowLayout();
		flayout.setHgap(20);
		panelBotonesChat.setLayout(flayout);
		
		chat = new JTextArea(2,50);
		scrollChat = new JScrollPane(chat);
		
		botonDecirenPrivado = new ComandoCODWWChatPrivado(i18n.getString("pcodwwcc1"));		
		botonDecir = new ComandoCODWWChat(i18n.getString("pcodwwcc2"));	
		
		panelBotonesChat.add(botonDecirenPrivado);
		panelBotonesChat.add(botonDecir);
			
		panelChat.add(scrollChat);
		panelChat.add(panelBotonesChat, BorderLayout.SOUTH);
		
		panelChat.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(
																			1,// arriba
																			10,//izquierda
																			1,// abajo
																			10)// derecha
										, BorderFactory.createTitledBorder(new LineBorder(Color.GRAY), i18n.getString("pcodwwcc3"))
										));		
		return panelChat;
	}
	
	public void añadirOyentes(){
		
		botonfast_restart.addActionListener(mediador);
		botonSiguienteMapa.addActionListener(mediador);
		
		botonRefresco.addActionListener(mediador); 
		botonKickear.addActionListener(mediador); 	
		botonBaneoTemporal.addActionListener(mediador);
		
		botonDecirenPrivado.addActionListener(mediador);
		botonDecir.addActionListener(mediador);	
	}
	
	public JTextArea getChat() {
		return chat;
	}

	public JTextField getChatKick() {
		return chatKick;
	}	
}