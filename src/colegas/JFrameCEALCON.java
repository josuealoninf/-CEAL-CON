package colegas;

import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import mediador.IMediador;

import comandos.comandosMediador.ComandoMenuAcercade;
import comandos.comandosMediador.ComandoMenuCifrarRCON;
import comandos.comandosMediador.ComandoMenuConexion;
import comandos.comandosMediador.ComandoMenuConfiguracion;


/**
 * JFrameCEALCON  frame principal de la aplicación
 * @author Alatriste
 * @version 0.3
 */
public final class JFrameCEALCON extends JFrame implements IColega{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = 3869242905344044465L;

	/** Aqui se engancha el mediador correspondiente */
	private IMediador mediador;
	
	/** Es la barra de menu donde van enganchados los menus*/
	private JMenuBar barradeMenu;
	/** Es el menu archivo que luego engancharemos a la barra de menu*/
	private JMenu menuArchivo;	
	/** Forma parte del menuArchivo y permite la conexion del programa*/
	private JMenuItem conectar;	
	/** Aloja el menu de configuracion*/
	private JMenuItem configuracion;
	/** Permite a los administradores cifrar sus RCON de forma segura con RSA de 128bits*/
	private JMenuItem cifrarRCON;
	/** Es el menu ayuda q luego engancharemos a la barra de menu*/
	private JMenu menuAyuda;	
	/** Forma parte del menu ayuda y muestra un pequeño dialogo con la version de [CEAL]CON*/
	private JMenuItem acerca;	
	
	/**
	 * Creamos un frame con el nombre de la consola y su version
	 * @param i18n cadenas de traducción en función del lenguaje
	 * @param imagenCEAL nuestro logo
	 */
	public JFrameCEALCON(ResourceBundle i18n, ImageIcon imagenCEAL){	
		
		super(i18n.getString("jf1"));	
		
		this.setJMenuBar(construirMenu(i18n));	
		
		// Le ponemos la imagen de CEAL en vez de la de Java
		this.setIconImage(imagenCEAL.getImage());			
		
		//también hace falta impedir que se cierre, por defecto la X siempre cierra, hagas lo que hagas desde la aplicación
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		this.setSize(650, 750);
		//NO queremos que cambie su tamaño
		this.setResizable(false);
		this.setLocationRelativeTo(null);			
		this.setVisible(true);	
	}	
	
	/**
	 * de esta forma conocemos el mediador
	 */
	public void setMediador( IMediador mediador ) {
		this.mediador = mediador;	
	}
	
	/**
	 * Metodo que se encarga de generar el menu q se ve en la parte superior y que es igual para todos los
	 * hijos de cliente.
	 * @return devuelve el menu ya creado y con sus oyentes enganchados.
	 */
	private JMenuBar construirMenu(ResourceBundle i18n){
		
		barradeMenu = new JMenuBar();
		
		menuArchivo = new JMenu(i18n.getString("jf2"));	
		menuAyuda = new JMenu(i18n.getString("jf3"));	
		
		barradeMenu.add(menuArchivo);
		barradeMenu.add(menuAyuda);		
		
		conectar = new ComandoMenuConexion(i18n.getString("jf4"));	
		
		configuracion = new ComandoMenuConfiguracion(i18n.getString("jf5"));
		cifrarRCON = new ComandoMenuCifrarRCON(i18n.getString("jf6"));
		
		menuArchivo.add(conectar);		
		menuArchivo.addSeparator();
		menuArchivo.add(configuracion);
		menuArchivo.add(cifrarRCON);				
		
		acerca = new ComandoMenuAcercade(i18n.getString("jf8"));	
				
		menuAyuda.add(acerca);	
		
		return barradeMenu;	
	}	
	
	/**
	 * Para separar la construcción del menú de la lógica de los botones
	 */
	public void añadirOyentes(){
		conectar.addActionListener(mediador);		
		configuracion.addActionListener(mediador);
		cifrarRCON.addActionListener(mediador);
		acerca.addActionListener(mediador);
		//Añadimos el mediador como oyente de window listener para detectar el cierre de la ventana y sacar el menú de desconexión
		this.addWindowListener(mediador);
	}	
	
	public JMenuItem getConectar() {
		return conectar;
	}
	
	public JMenuItem getCifrarRCON() {
		return cifrarRCON;
	}
	
	public JMenuItem getConfiguracion() {
		return configuracion;
	}
}