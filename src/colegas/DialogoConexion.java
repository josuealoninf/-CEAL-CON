package colegas;

import java.awt.GridLayout;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import mediador.IMediador;

import comandos.comandosMediador.ComandoBorrarServer;
import comandos.comandosMediador.ComandoCargarServer;
import comandos.comandosMediador.ComandoConexion;
import comandos.comandosMediador.ComandoGuardarServer;
import comandos.comandosMediador.ComandoJuegos;

/**
 * DialogoConexion. Muestra la interfaz necesaria para conectar [CEAL]CON al server deseado
 * @author Alatriste
 * @version 0.3
 */
public final class DialogoConexion extends JDialog implements IColega{
		
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = -5178951781508383817L;
	
	/** El mediador correspondiente*/	
	private IMediador mediador;	
	
	/** Contiene todos los datos básicos a tipear o cargar por el usuario */
	private JPanel panelConexion;
	/** Contiene una caja con la interfaz para manejar las configuraciones de servers guardadas*/
	private Box cajaServers_Guardados;
	/** Contiene los botones de borrar y cargar el server en el árbol*/
	private JPanel panelBotonesServers_Guardados;	
	
	/** Etiqueta que indica el nombre con el que se guarda el server*/
	private JLabel etiquetaNombreServer;
	/** Etiqueta que indica el nombre del usuario que conecta la consola*/
	private JLabel etiquetaLogin;	
	/** Etiqueta que indica la IP del server al que nos conectamos*/
	private JLabel etiquetaIP;
	/** Etiqueta que indica el puerto del server al que nos conectamos*/
	private JLabel etiquetaPuerto;
	/** Etiqueta que indica la password cifrada del server al que nos conectamos */
	private JLabel etiquetaPassword;	
	/** Etiqueta del combo de juegos*/
	private JLabel etiquetaJuegos;
	/** Etiqueta del arbol de configuracion de servers guardada*/
	private JLabel etiquetaServers_Guardados;
	
	/** El campo de texto que recoje el nombre con el que se guarda el server*/
	private JTextField campoNombreServer;
	/** El campo de texto que recoje el nombre del usuario que conecta la consola*/
	private JTextField campoUsuario;
	/** El campo de texto que recoje la IP del server al que nos conectamos*/
	private JTextField campoIP;
	/** El campo de texto que recoje el puerto del server al que nos conectamos*/
	private JTextField campoPuerto;
	/** El campo de texto que recoje la password cifrada del server al que nos conectamos*/ 
	private JTextField campoPassword;	
	/** El combo que muestra los tipos de servers a los que podemos conectar [CEAL]CON*/
	private JComboBox campoJuegos;
	
	/** Botón que intenta conectar [CEAL]CON al server especificado*/
	private JButton botonConectar;	
	/** Botón que guarda la configuración del server actual*/
	private JButton botonGuardarServer;	
	/** Botón que carga la configuración del server seleccionado*/
	private JButton botonCargarServer;
	/** Botón que borra la configuración del server seleccionado*/
	private JButton botonBorrarServer;
	
	/** El árbol de servers guardados por el usuario*/
	private JTree servers_Guardados;
	/** la puta mierda del modelo del arbol que hace falta para todo no se para q quiero el puto arbol si no hace nada*/
	private DefaultTreeModel dtm;
	/** El nodo raiz*/
	private DefaultMutableTreeNode rootNode;
	/** Los distintos servers del arbol*/
	private DefaultMutableTreeNode hijo;
	/** El scrollpane que le pone las barras al arbol de servers guardados por el usuario*/
	private JScrollPane scroll_servers_Guardados;

	/**
	 * Constructor de DialogoConexión. Se encarga de generar el dialogo de conexion de [CEAL]CON. 
	 * @param frame es el frame principal del q depende el dialogo login. invisible hasta conexion realizada
	 * @param i18n cadenas de traducción en función del lenguaje
	 * @param modal esta a true para que el dialogo sea modal y no se cree el frame principal
	 * @param juegos lista de juegos disponibles para conectar CEALCON
	 * @param servidoresGuardados lista de servidores que el usuario ha guardado hasta el momento 
	 */
	public DialogoConexion(JFrame frame, ResourceBundle i18n, boolean modal, String [] juegos, List<String> servidoresGuardados){
		
		//llamamos al constructor de la superclase, que para algo heredamos de JDialog
		super(frame, i18n.getString("dc1"), modal);	
		
		panelConexion = new JPanel();		
		cajaServers_Guardados = Box.createVerticalBox();
		panelBotonesServers_Guardados = new JPanel();
		
		etiquetaNombreServer = new JLabel(i18n.getString("dc9"), SwingConstants.CENTER);	
		etiquetaLogin = new JLabel(i18n.getString("dc2"), SwingConstants.CENTER);	
		etiquetaIP = new JLabel(i18n.getString("dc3"), SwingConstants.CENTER);
		etiquetaPuerto = new JLabel(i18n.getString("dc4"), SwingConstants.CENTER);
		etiquetaPassword = new JLabel(i18n.getString("dc5"), SwingConstants.CENTER);		
		etiquetaJuegos = new JLabel(i18n.getString("dc6"), SwingConstants.CENTER);
		etiquetaServers_Guardados = new JLabel(i18n.getString("dc12"), SwingConstants.CENTER);		
		
		campoNombreServer = new JTextField(33);
		campoNombreServer.setToolTipText(i18n.getString("dc14"));
		campoUsuario = new JTextField(16);
		campoUsuario.setToolTipText(i18n.getString("dc13"));
		campoIP = new JTextField(15);
		campoPuerto = new JTextField(5);	
		campoPassword = new JTextField(50);		
		campoPassword.setToolTipText(i18n.getString("dc15"));
		
		botonConectar = new ComandoConexion(i18n.getString("dc7"));
		
		botonGuardarServer = new ComandoGuardarServer(i18n.getString("dc8"));	
		botonCargarServer = new ComandoCargarServer(i18n.getString("dc10"));
		botonBorrarServer = new ComandoBorrarServer(i18n.getString("dc11"));
		campoJuegos = new ComandoJuegos(juegos);	
		
		//Componentes de la caja
		
		rootNode = new DefaultMutableTreeNode("Servers");
		dtm = new DefaultTreeModel(rootNode);	
		
		//Construimos el árbol, controlamos el null por si hay casque en el fichero
		if(servidoresGuardados != null){			
			for(int i=0; i<servidoresGuardados.size(); i++){
				hijo = new DefaultMutableTreeNode(servidoresGuardados.get(i));
				dtm.insertNodeInto(hijo, rootNode, rootNode.getChildCount());
			}	
		}
		
		servers_Guardados = new JTree(dtm);		
		servers_Guardados.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);	
		//No queremos ver el raíz, solo los hijos que son los servers guardados
		servers_Guardados.setRootVisible(false);		
			
		scroll_servers_Guardados = new JScrollPane(servers_Guardados);
		
		//Configuracion y construccion del panelConexion
		panelConexion.setLayout(new GridLayout(0,2,25,5));
		
		panelConexion.add(etiquetaNombreServer);
		panelConexion.add(campoNombreServer);
		
		panelConexion.add(etiquetaLogin);
		panelConexion.add(campoUsuario);
				
		panelConexion.add(etiquetaIP);
		panelConexion.add(campoIP);
				
		panelConexion.add(etiquetaPuerto);
		panelConexion.add(campoPuerto);
		
		panelConexion.add(etiquetaPassword);
		panelConexion.add(campoPassword);	
		
		panelConexion.add(etiquetaJuegos);
		panelConexion.add(campoJuegos);
		
		panelConexion.add(botonConectar);
		panelConexion.add(botonGuardarServer);
			
		//Configuración y contruccion del panelBotonesServers_Guardados		
		panelBotonesServers_Guardados.setLayout(new GridLayout(1,2,25,5));
		
		panelBotonesServers_Guardados.add(botonCargarServer);
		panelBotonesServers_Guardados.add(botonBorrarServer);
		
		//Construccion de la caja
		cajaServers_Guardados.add(etiquetaServers_Guardados);
		cajaServers_Guardados.add(scroll_servers_Guardados);
		cajaServers_Guardados.add(panelBotonesServers_Guardados);
		
		//Ensamblaje final	
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.getContentPane().add(panelConexion);
		this.getContentPane().add(cajaServers_Guardados);	
		
	    this.setSize(650, 425);
	    this.setResizable(false);
	    this.setLocationRelativeTo(this.getParent());
	    this.setVisible(false);
	}
	
	/**
	 * Metodo llamado en el registro para tener la instacia del mediador
	 */
	public void setMediador(IMediador mediador){
		this.mediador = mediador;
	}
	
	/**
		Para separar la construcción del menú de la lógica de los botones
	 */
	public void añadirOyentes(){		
		
		botonConectar.addActionListener(mediador);		
		botonGuardarServer.addActionListener(mediador);		
		botonCargarServer.addActionListener(mediador);
		botonBorrarServer.addActionListener(mediador);
		campoJuegos.addActionListener(mediador);
	}

	public JTextField getCampoNombreServer() {
		return campoNombreServer;
	}

	public JTextField getCampoUsuario() {
		return campoUsuario;
	}

	public JTextField getCampoIP() {
		return campoIP;
	}

	public JTextField getCampoPuerto() {
		return campoPuerto;
	}

	public JTextField getCampoPassword() {
		return campoPassword;
	}

	public JComboBox getCampoJuegos() {
		return campoJuegos;
	}

	public JTree getServers_Guardados() {
		return servers_Guardados;
	}

	public DefaultTreeModel getDtm() {
		return dtm;
	}

	public DefaultMutableTreeNode getRootNode() {
		return rootNode;
	}
}