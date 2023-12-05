package mediador;

import java.awt.AWTEvent;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import colegas.DialogoAcercade;
import colegas.DialogoCifrarRCON;
import colegas.DialogoConexion;
import colegas.DialogoConfiguracion;
import colegas.JFrameCEALCON;

import comandos.IComando;
import componentes.RSAmodificado;
import componentes.componentesBFBC2.ClienteTCP_BFBC2;
import componentes.componentesCODWW.ClienteUDPCODWW;

import excepciones.ContraseñaInvalidaException;
import excepciones.DesincronizacionSecuencia;

/**
 * Mediador. Esta clase contiene toda la lógica básica de la aplicación y la interacción entre los diferentes colegas
 * Heredamos de WindowAdapter para no tener que hacer tantos metodos en blanco, total eso solo lo queremos para controlar el cierre y nada más
 * @author Alatriste
 * @version 0.3
 */
public class Mediador extends WindowAdapter implements IMediador{
	
	/** Instancia del Mediador para el patron singleton */
	private static Mediador mediador = null;
	/** El logger de log4j*/
	protected static final Logger log = Logger.getLogger(Mediador.class);
	
	/** Tiempo de validez de la tabla, antes de obligar a refrescar, en ms*/
	private static final int TIMEOUT_TABLA = 60000;
	/** Viejo guarda el valor en el tiempo para obligar a refrescar la tabla*/
	protected static long viejo = 0;
	/** Nombre que va a mostrarse en pantalla en los comandos say y tell*/
	protected static String usuarioCEAL = null;
	
	/** La imagen del clan*/
	private static ImageIcon iconoCEAL;
	/** Componente principal de la aplicación que engloba los demás */	
	protected static JFrameCEALCON jFrameCEALCON;
	/** El diálogo que muestra los parámetros de conexión para que el usuario los introduzc y/o modifique */
	private static DialogoConexion dialogoConexion;	
	/** El diálogo que muestra la interfaz para lograr una RCON cifrada con RSA de 128 bits */
	private static DialogoCifrarRCON dialogoCifrarRCON;
	/** El diálogo que permite escojer el nivel del log y el lenguaje en el que se mostrará la aplicación*/
	private static DialogoConfiguracion dialogoConfiguracion;
	/** El diálogo acerca de que muestra el logo de nuestro clan con un enlace a nuestra pagina web */
	private static DialogoAcercade dialogoAcercade;	
		
	/** IP contra la que se hace la conexion*/
	private static String iP = null;
	/** Este parametro nos informa del puerto donde va a establecerse la comunicacion*/
	private static int puerto = 0;	
	/** El Rcon del servidor*/
	private static String password = null;
	/** El nivel de log de la aplicación*/ 
	private static int nivelLogActual;
	/** Contiene la informacion de en que idioma vamos a mostrar la aplicación*/
	private static Locale locale;
	/** De aquí sacaremos la clave valor para cada literal*/
	private static ResourceBundle i18n;
	/** Los juegos disponibles en la consola*/
	private static final String[] juegos = {"", "Call Of Duty: World at War", "Battlefield Bad Company 2"};
	/** El juego seleccionado/cargado por el usuario*/
	private static int juegoSeleccionado;	
	/** Los servidores guardados que el usuario ha ido añadiendo al árbol, para evitar que los duplique*/
	private static ArrayList<String> servidoresGuardadosUsuario;
	
	/** Tiempo de timeout, en milisegundos*/
	private static final int TIMEOUT = 1523;
	
	/**
	 * Hacemos protected el mediador para que nadie salvo el metodo getInstance y los que heredan puedan instanciarlo */
	protected Mediador(){
		PropertyConfigurator.configure("bin\\log4j.properties");		
	}
	
	/**
	 * Metodo getInstace que devuelve la instancia unica del Mediador, cumpliendo asi el patron Singleton
	 * Creamos toda la interfaz gráfica común de la aplicación
	 * @return el mediador*/
	public static Mediador getInstance(){
		if(mediador == null){
			mediador = new Mediador();		
			
			cargarDatosLogyLocale();		
			
			iconoCEAL = new ImageIcon("bin\\images\\ceal.jpg");
			jFrameCEALCON = new JFrameCEALCON(i18n, iconoCEAL);			
			dialogoConexion = new DialogoConexion(jFrameCEALCON, i18n, true, juegos, servidoresGuardadosUsuario);			
			dialogoCifrarRCON = new DialogoCifrarRCON(jFrameCEALCON, i18n, true);	
			dialogoConfiguracion = new DialogoConfiguracion(jFrameCEALCON, i18n, true, nivelLogActual);
			dialogoAcercade = new DialogoAcercade(jFrameCEALCON, i18n, true, iconoCEAL);						
		}		
		return mediador;
	}
	
	/**
	 * Este metodo captura todos los eventos de los botones ya hace que se ejecute la accion correspondiente
	 */
	public final void actionPerformed(ActionEvent e) {	
		IComando cmd = ( IComando ) e.getSource();	
		cmd.ejecutar( this, e);
	}
	
	/**
	 * Este metodo captura el evento de raton cuando el clickado sobre algo.
	 * En nuestro caso particular, solo lo usamos con el JTextField del Tiempo de mensaje en 
	 * pantalla, para que al pulsarlo se le informe al usuario de lo que ocurre si modifica este 
	 * parámetro 
	 */
	public final void mouseClicked(MouseEvent e){
    	IComando cmd = ( IComando ) e.getSource();	
		cmd.ejecutar( this, e );
    }

	/**Con implementación vacía para cumplir la interface MouseListener*/
    public final void mousePressed(MouseEvent e){}    
    /**Con implementación vacía para cumplir la interface MouseListener*/
    public final void mouseReleased(MouseEvent e){}    
    /**Con implementación vacía para cumplir la interface MouseListener*/
    public final void mouseEntered(MouseEvent e){}    
    /**Con implementación vacía para cumplir la interface MouseListener*/
    public final void mouseExited(MouseEvent e){}     
    
    @Override
	public final void windowClosing(WindowEvent e){
    	comandoCerrar();
    }   
    
    public final void comandoJuegos(AWTEvent event){
		JComboBox cb = (JComboBox)event.getSource();
        juegoSeleccionado = cb.getSelectedIndex();
	}
	
    public final void registrarJFrameCEALCON(IMediador mediador){
    	jFrameCEALCON.setMediador(mediador);
		jFrameCEALCON.añadirOyentes();		
    }
    
	public void registrarPaneldeJuego(IMediador mediador){}
	
	public final void registrarDialogoConexion(IMediador mediador){
		dialogoConexion.setMediador(mediador);
		dialogoConexion.añadirOyentes();		
	}
		
	public final void registrarDialogoCifrarRCON(IMediador mediador){
		dialogoCifrarRCON.setMediador(mediador);
		dialogoCifrarRCON.añadirOyentes();		
	}
	
	public final void registrarDialogoConfiguracion(IMediador mediador){
		dialogoConfiguracion.setMediador(mediador);
		dialogoConfiguracion.añadirOyentes();
	}
		
	public final void registrarDialogoAcercade(IMediador mediador){
		dialogoAcercade.setMediador(mediador);
		dialogoAcercade.añadirOyentes();		
	}
	
	public final void comandoMenuConexion(){
		dialogoConexion.setVisible(true);
	}	
	
	public final void comandoMenuConfiguracion(){
		dialogoConfiguracion.setVisible(true);
	}
	
	public final void comandoMenuCifrarRCON(){		
		dialogoCifrarRCON.setVisible(true);
	}
	
	public final void comandoMenuAcercade(){			
		dialogoAcercade.setVisible(true);
	}
	
	public final void comandoConectar(){	
		
		//Solo si los campos son correctos validamos
		if(validacionCamposDialogoConexion()){			
			try{				
				//Aqui intanciamos el mediador correspondiente, los datos los recuperamos en caliente del dialogoConexion
				usuarioCEAL = dialogoConexion.getCampoUsuario().getText();
				iP = dialogoConexion.getCampoIP().getText();
				puerto = Integer.parseInt(dialogoConexion.getCampoPuerto().getText());
				password = dialogoConexion.getCampoPassword().getText();			
				switch(juegoSeleccionado){
					case 1:			
						ClienteUDPCODWW.getInstance().crearConexion(iP, puerto, password, TIMEOUT, i18n);
						//Ya no hay patron singleton porque necesitamos pasar de alguna forma el i18n para la i18n
						//Instanciamos el mediador correspondiente y registramos los paneles correspondientes	
						//La variable es local para evitar en lo posible que se instancie desde otro lugar
						MediadorCODWW mediadorCODWW = new MediadorCODWW(i18n);
						mediadorCODWW.registrarPaneldeJuego(mediadorCODWW);							
						break;							
					case 2:					
						ClienteTCP_BFBC2.getInstance().crearConexion(iP, puerto, password, TIMEOUT, i18n);
						//Ya no hay patron singleton porque necesitamos pasar de alguna forma el i18n para la i18n
						//Instanciamos el mediador correspondiente y registramos los paneles correspondientes		
						//La variable es local para evitar en lo posible que se instancie desde otro lugar
						MediadorBFBC2 mediadorBFBC2 = new MediadorBFBC2(i18n);
						mediadorBFBC2.registrarPaneldeJuego(mediadorBFBC2);									
						break;	
					default:
						if(log.isDebugEnabled())
							log.error("El juego seleccionado " + juegoSeleccionado +" no está en la lista de disponibles.");
						break;
				}
				//deshabilitamos todos los botones del primer menu, puesto que ya estamos conectados
				jFrameCEALCON.getConectar().setEnabled(false);	
				jFrameCEALCON.getConfiguracion().setEnabled(false);
				jFrameCEALCON.getCifrarRCON().setEnabled(false);	
				//ocultamos el dialogo puesto que la conexión ha sido satisfactoria, null las referencias para el gc, no volveran a usarse
				dialogoConexion.setVisible(false);	
				dialogoConexion = null;
				dialogoConfiguracion = null;
				dialogoCifrarRCON = null;
			} catch (UnknownHostException ex){
				JOptionPane.showMessageDialog(jFrameCEALCON,
						i18n.getString("mcc1") +
						i18n.getString("mcc2"),
						i18n.getString("mcc3"),
						JOptionPane.ERROR_MESSAGE);	
				log.error(ex);
			} catch (IOException ex){
				JOptionPane.showMessageDialog(jFrameCEALCON,
						i18n.getString("mcc4") +
						i18n.getString("mcc5") +
						i18n.getString("mcc6"),
						i18n.getString("mcc7"),
						JOptionPane.ERROR_MESSAGE);	
				log.error(ex);
			} catch (ContraseñaInvalidaException ex){
				JOptionPane.showMessageDialog(jFrameCEALCON,
						i18n.getString("mcc8") +
						i18n.getString("mcc9") +
						i18n.getString("mcc10") +
						i18n.getString("mcc11"),
						i18n.getString("mcc12"),
						JOptionPane.ERROR_MESSAGE);	
				log.error(ex);
			} catch (DesincronizacionSecuencia de){
				desincronizacionSecuencia();	
				log.error(de);
			} catch (NoSuchAlgorithmException ne){
				log.error(ne);
			} 
		}			
	}
	
	public final void comandoGuardarServer(){
		
		DefaultTreeModel dtm = dialogoConexion.getDtm();
    	DefaultMutableTreeNode rootNode = dialogoConexion.getRootNode(); 
    	DefaultMutableTreeNode hijo;
    	String nombreHijotrim;   
    	
    	if(validacionCamposDialogoConexion()){		    
				
			//Añadimos el nuevo server al arbol si su nombre es distinto
			nombreHijotrim = dialogoConexion.getCampoNombreServer().getText().trim();
			if(!servidoresGuardadosUsuario.contains(nombreHijotrim)){					
				hijo = new DefaultMutableTreeNode(nombreHijotrim);
				dtm.insertNodeInto(hijo, rootNode, rootNode.getChildCount());	
				//sino no se muestra al añadir el primer nodo
				dtm.reload(rootNode);
				servidoresGuardadosUsuario.add(nombreHijotrim);	
				guardarServer();
				JOptionPane.showMessageDialog(jFrameCEALCON,
						i18n.getString("mcg1"),
						i18n.getString("mcg2"),
						JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				//Esto es por si se quiere cambiar los datos de un server ya guardado
				int respuesta = JOptionPane.showConfirmDialog(jFrameCEALCON,
						i18n.getString("mcg5") + i18n.getString("mcg6"),
						i18n.getString("mcg7"),
						JOptionPane.OK_CANCEL_OPTION);
				if(respuesta == 0)
					guardarServer();
			}						
    	}
	}
	
	public final void comandoCargarServer(){
		
		Object serverSeleccionado = dialogoConexion.getServers_Guardados().getLastSelectedPathComponent();
		
		if(serverSeleccionado != null){
			File archivoServer = new File("bin\\servers\\"+ serverSeleccionado.toString());
			FileInputStream archivoServerInputStream;
			Properties datosServer;
			
			try {
				datosServer = new Properties();
				archivoServerInputStream = new FileInputStream(archivoServer);
				datosServer.load(archivoServerInputStream);			
				archivoServerInputStream.close();		
				
				if(log.isDebugEnabled()){
					log.debug("El fichero recuperado es: " + serverSeleccionado);				
					log.debug(datosServer.getProperty("Usuario")+ " ");
					log.debug(datosServer.getProperty("IP")+ " ");
					log.debug(datosServer.getProperty("Puerto")+ " ");
					log.debug(datosServer.getProperty("Password")+ " ");				
					log.debug(datosServer.getProperty("JuegoSeleccionado")+ " ");
				}
				
				dialogoConexion.getCampoNombreServer().setText(serverSeleccionado.toString());
				dialogoConexion.getCampoUsuario().setText(datosServer.getProperty("Usuario"));
				dialogoConexion.getCampoIP().setText(datosServer.getProperty("IP"));
				dialogoConexion.getCampoPuerto().setText(datosServer.getProperty("Puerto"));
				dialogoConexion.getCampoPassword().setText(datosServer.getProperty("Password"));
				dialogoConexion.getCampoJuegos().setSelectedIndex(Integer.parseInt(datosServer.getProperty("JuegoSeleccionado")));			
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(jFrameCEALCON,
						i18n.getString("mccs1") +
						i18n.getString("mccs2") +
						i18n.getString("mccs3"),
						i18n.getString("mccs4"),
						JOptionPane.ERROR_MESSAGE);	
				log.error(e);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(jFrameCEALCON,
						i18n.getString("mccs5") +
						i18n.getString("mccs6") +
						i18n.getString("mccs7"),
						i18n.getString("mccs8"),
						JOptionPane.ERROR_MESSAGE);	
				log.error(e);
			} catch(NumberFormatException ex){
				JOptionPane.showMessageDialog(jFrameCEALCON,
						i18n.getString("mccs9") +
						i18n.getString("mccs10") +
						i18n.getString("mccs11") +
						i18n.getString("mccs12"),
						i18n.getString("mccs13"),
						JOptionPane.ERROR_MESSAGE);		
				//Lo ponemos a cero, para borrar el anterior que hubiera y que no haya mas confusión
				dialogoConexion.getCampoJuegos().setSelectedIndex(0);
			}	
		}
		else{
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mccs14"),
					i18n.getString("mccs15"),
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public final void comandoBorrarServer(){
		
		DefaultMutableTreeNode serverSeleccionado = (DefaultMutableTreeNode)dialogoConexion.getServers_Guardados().getLastSelectedPathComponent();
		DefaultTreeModel dtm = dialogoConexion.getDtm();
				
		if(serverSeleccionado != null){
			File archivoServer = new File("bin\\servers\\"+ serverSeleccionado);
			
			if(archivoServer.delete()){	
				// Lo quitamos de la lista por si quiere añadir uno igual mas tarde	
				servidoresGuardadosUsuario.remove(serverSeleccionado.toString());
				dtm.removeNodeFromParent(serverSeleccionado);	
				
				JOptionPane.showMessageDialog(jFrameCEALCON,
						i18n.getString("mcbs1"),
						i18n.getString("mcbs2"),
						JOptionPane.INFORMATION_MESSAGE);
			}
			else{
				JOptionPane.showMessageDialog(jFrameCEALCON,
						i18n.getString("mcbs3"),
						i18n.getString("mcbs4"),
						JOptionPane.ERROR_MESSAGE);
			}
		}
		else{
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mcbs5"),
					i18n.getString("mcbs6"),
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public final void comandoGuardarConfiguracion(){	
			
		File archivosConfiguracion;		
    	FileInputStream archivosConfiguracionInputStream = null;
		FileOutputStream archivosConfiguracionOutputStream = null;	
		Properties datosConf;  
		
		String nivel_log = (String)dialogoConfiguracion.getNivelLog().getSelectedItem();
		String lenguaje = (String)dialogoConfiguracion.getLanguage().getSelectedItem();
    			
		try {
			datosConf = new Properties();
			archivosConfiguracion = new File("bin\\lenguaje.properties");			
			archivosConfiguracionOutputStream = new FileOutputStream(archivosConfiguracion);	
			datosConf.setProperty("Locale", lenguaje);
			datosConf.store(archivosConfiguracionOutputStream, "languaje");	
			archivosConfiguracionOutputStream.close();		
					
			if(log.isDebugEnabled()){
				log.debug("los datos guardados son ");				
				log.debug(datosConf.getProperty("Locale")+ " ");				
			}			
			
			//Ahora guardamos los datos del log, cargando primero para guardar todo lo que tiene el fichero de log4j.properties
			datosConf.clear();
			archivosConfiguracion = new File("bin\\log4j.properties");			
			
			archivosConfiguracionInputStream = new FileInputStream(archivosConfiguracion);				
			datosConf.load(archivosConfiguracionInputStream);	
			archivosConfiguracionInputStream.close();	
			
			datosConf.setProperty("log4j.rootCategory", nivel_log.concat(", File"));					
				
			archivosConfiguracionOutputStream = new FileOutputStream(archivosConfiguracion);			
			datosConf.store(archivosConfiguracionOutputStream, "log4j");
			archivosConfiguracionOutputStream.close();
			
			datosConf.clear();
			datosConf = null;
			
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mcgyc1")+
					i18n.getString("mcgyc2"),					
					i18n.getString("mcgyc3"),
					JOptionPane.INFORMATION_MESSAGE);			
		} catch (FileNotFoundException e) {			
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mcgyc4") +
					i18n.getString("mcgyc5") +
					i18n.getString("mcgyc6"),
					i18n.getString("mcgyc7"),
					JOptionPane.ERROR_MESSAGE);	
			log.error(e);
		} catch (IOException e) {			
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mcgyc8") +
					i18n.getString("mcgyc9") +
					i18n.getString("mcgyc10"),
					i18n.getString("mcgyc11"),
					JOptionPane.ERROR_MESSAGE);	
			log.error(e);
		} 			
	}
	
	public final void comandoCancelarConfiguracion(){
		dialogoConfiguracion.setVisible(false);
	}
	
	public final void comandoCifrarRCON(){
		
		String RCON = dialogoCifrarRCON.getCampoRCON().getText();
		
		if(RCON.isEmpty()){
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mccr1") +
					i18n.getString("mccr2") ,
					i18n.getString("mccr3"),
					JOptionPane.ERROR_MESSAGE);	
		}
		else{
			//Copiamos la RCON cifrada al portapapeles
			Clipboard portapapeles =  Toolkit.getDefaultToolkit().getSystemClipboard();
			String RCONcifrada = RSAmodificado.getInstance().encryptString(RCON);
			portapapeles.setContents(new StringSelection(RCONcifrada), null);
			
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mccr4") +
					RCONcifrada + "\n\n" +
					i18n.getString("mccr5") +
					i18n.getString("mccr6") +
					i18n.getString("mccr7") +
					i18n.getString("mccr8"),
					i18n.getString("mccr9"),
					JOptionPane.INFORMATION_MESSAGE);			
		}
	}
	
	public final void comandoAcercade(){
		
		try{
			// Esto busca el explorador por defecto del sistema y ejecuta la direccion pasada
			Desktop.getDesktop().browse(new URI(i18n.getString("mcad1")));			
		} catch(Exception ex){
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mcad2") +
					i18n.getString("mcad3"),
					i18n.getString("mcad4"),
					JOptionPane.ERROR_MESSAGE);	
			log.error(ex);
		} 
	}
	
	public final void comandoCerrar(){
		int valor = JOptionPane.showConfirmDialog(jFrameCEALCON,
				i18n.getString("mcce1") +
				i18n.getString("mcce2"),
				i18n.getString("mcce3"),
				JOptionPane.YES_NO_OPTION);
		
		if (valor == JOptionPane.YES_OPTION) {				
			try {
				switch(juegoSeleccionado){
					case 1:
						ClienteUDPCODWW.getInstance().cerrarConexion();
						break;					
					case 2:
						ClienteTCP_BFBC2.getInstance().cerrarConexion();
						break;				
					default:
						log.error("No se ha cerrado correctamente el mediador correspondiente. Mediador " + juegoSeleccionado + " desconocido");
						break;
				}								
			} catch (InterruptedException e) {
				log.error(e);
			} catch (IOException ie){
				log.error(ie);
			} catch (DesincronizacionSecuencia de){
				desincronizacionSecuencia();	
				log.error(de);
			} finally {
				System.exit(0);
			}
		}
	}
	
	public void comandoRefresco(){}
	public void comandoCODWWKickeo_BaneoTemporal(boolean privado){}
	public void comandoCODWWChat(){}
	public void comandoCODWWChatPrivado(){}	
	public void comandoCODWWNextMap(){}
	public void comandoCODWWFastRestart(){}
	
	public void comandoBFBC2YellyYellPrivado(boolean privado){}
	public void comandoBFBC2ChatyChatPrivado(boolean privado){}
	public void comandoBFBC2Move(){}
	public void comandoBFBC2Kick(){}
	public void comandoBFBC2BaneoTemporal(){}
	public void comandoBFBC2NextMap(){}
	public void comandoBFBC2RestartMap(){}
	
	private static void cargarDatosLogyLocale(){
		
		File archivosPropiedades;
    	FileInputStream archivosPropiedadesInputStream;      	
    	
    	Properties datosConf;   
    	// Los idiomas estan guardados literalmente para que se entiendan "English y Español", esta variable es para hacer la conversion
    	// entre el literal y el locale real "en y es"
    	String nombreLocale;
    	
    	//Si peta el fichero, hay que tener un locale para escribir los mensajes
    	locale = new Locale("en");
		i18n = ResourceBundle.getBundle("componentes.I18N.Traduccion", locale);
    	
		try {
			datosConf = new Properties();
			archivosPropiedades = new File("bin\\lenguaje.properties");			
			archivosPropiedadesInputStream = new FileInputStream(archivosPropiedades);				
			datosConf.load(archivosPropiedadesInputStream);	
			archivosPropiedadesInputStream.close();		
					
			if(log.isDebugEnabled()){
				log.debug("los datos guardados son ");				
				log.debug(datosConf.getProperty("Locale")+ " ");				
			}			
			
			//Cargamos el locale guardado correspondiente
			nombreLocale = (datosConf.getProperty("Locale")).equals("English") ? "en" : "es";
			locale = new Locale(nombreLocale);	
			i18n = ResourceBundle.getBundle("componentes.I18N.Traduccion", locale);
			
			//Ahora cargamos los datos del log	
			datosConf.clear();
			archivosPropiedades = new File("bin\\log4j.properties");			
			archivosPropiedadesInputStream = new FileInputStream(archivosPropiedades);				
			datosConf.load(archivosPropiedadesInputStream);	
			archivosPropiedadesInputStream.close();		
			//Me parece mas sencillo calcular con un int en que log estamos que extraerlo de la cadena del fichero de configuracion del log4j
			//algunaVariable=(condición que devuelve un valor booleano) ? (valor si devuelve true) : (valor si devuelve false)					
			nivelLogActual = (datosConf.getProperty("log4j.rootCategory")).contains("OFF") ? 0 : 1;			
			
			// Reusamos el archivoPropiedades, para no declarar otro file
			archivosPropiedades = new File("bin\\servers");
			servidoresGuardadosUsuario = new ArrayList<String>(Arrays.asList(archivosPropiedades.list()));			
		} catch (FileNotFoundException e) {			
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mcdlyl1") +
					i18n.getString("mcdlyl2") +
					i18n.getString("mcdlyl3"),
					i18n.getString("mcdlyl4"),
					JOptionPane.ERROR_MESSAGE);	
			log.error(e);
		} catch (IOException e) {			
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mcdlyl5") +
					i18n.getString("mcdlyl6") +
					i18n.getString("mcdlyl7"),
					i18n.getString("mcdlyl8"),
					JOptionPane.ERROR_MESSAGE);	
			log.error(e);
		} 	
	}
	
	private boolean validacionCamposDialogoConexion(){
		
		// Comprobamos que no haya datos vacios
		if(dialogoConexion.getCampoNombreServer().getText().isEmpty() || dialogoConexion.getCampoUsuario().getText().isEmpty() 
			|| dialogoConexion.getCampoIP().getText().isEmpty()	|| dialogoConexion.getCampoPuerto().getText().isEmpty() 
			|| dialogoConexion.getCampoPassword().getText().isEmpty()){
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mvcdc1"),
					i18n.getString("mvcdc2"),
					JOptionPane.INFORMATION_MESSAGE);	
			return false;
		}
		
		// Comprobamos que ha elegido un juego al que conectarse
		if(dialogoConexion.getCampoJuegos().getSelectedIndex() == 0){
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mvcdc3"),
					i18n.getString("mvcdc4"),
					JOptionPane.INFORMATION_MESSAGE);	
			return false;
		}
		
		try{		
			puerto = Integer.parseInt(dialogoConexion.getCampoPuerto().getText());
			if(puerto > 65535){
				JOptionPane.showMessageDialog(jFrameCEALCON,
						i18n.getString("mvcdc7"),
						i18n.getString("mvcdc8"),
						JOptionPane.WARNING_MESSAGE);
				return false;
			}
		}
		//Para que no metan texto en el puerto
		catch(NumberFormatException ex){
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mvcdc5"),
					i18n.getString("mvcdc6"),
					JOptionPane.WARNING_MESSAGE);
			return false;
		}			
		return true;				
	}
	
	private void guardarServer(){		
		try{
			File archivoServer;	    	
	    	FileOutputStream archivoServerOutputStream = null;
	    	Properties datosServer;    	
	    	
			datosServer = new Properties();
			archivoServer = new File("bin\\servers\\"+ dialogoConexion.getCampoNombreServer().getText());
			
			datosServer.setProperty("Usuario", dialogoConexion.getCampoUsuario().getText());
			datosServer.setProperty("IP", dialogoConexion.getCampoIP().getText());
			datosServer.setProperty("Puerto", String.valueOf(dialogoConexion.getCampoPuerto().getText()));
			datosServer.setProperty("Password", dialogoConexion.getCampoPassword().getText());				
			datosServer.setProperty("JuegoSeleccionado", String.valueOf(dialogoConexion.getCampoJuegos().getSelectedIndex()));
			
			if(log.isDebugEnabled()){
				log.debug("El fichero guardado es: " + dialogoConexion.getCampoNombreServer().getText());
				log.debug(datosServer.getProperty("Usuario")+ " ");
				log.debug(datosServer.getProperty("IP")+ " ");
				log.debug(datosServer.getProperty("Puerto")+ " ");
				log.debug(datosServer.getProperty("Password")+ " ");					
				log.debug(datosServer.getProperty("JuegoSeleccionado")+ " ");
			}
			
			archivoServerOutputStream = new FileOutputStream(archivoServer);			
			datosServer.store(archivoServerOutputStream, "Servidor " + dialogoConexion.getCampoNombreServer().getText());
			archivoServerOutputStream.close();
			datosServer.clear();
			datosServer = null;
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mcg3") ,
					i18n.getString("mcg4"),
					JOptionPane.ERROR_MESSAGE);	
			log.error(ex);
		}			
	}
	
	protected boolean chequear_tablaactualizada_jugadorseleccionado(JTable listadeJugadores){
		if ((System.currentTimeMillis() - viejo) > TIMEOUT_TABLA) {// si hace mas de TIMEOUT_TABLA que no se refresca, le obligamos
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mctaj1") +
					i18n.getString("mctaj2"),
					i18n.getString("mctaj3"),
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		} 
		
		if(listadeJugadores == null || listadeJugadores.getSelectedRow() == -1){
			JOptionPane.showMessageDialog(jFrameCEALCON,
					i18n.getString("mctaj4") ,
					i18n.getString("mctaj5"),
					JOptionPane.ERROR_MESSAGE);	
			return false;
		}		
		return true;						
	}
	
	protected void noRespondeServidor(){
		JOptionPane.showMessageDialog(jFrameCEALCON,
				i18n.getString("mnrs1") +
				i18n.getString("mnrs2") +
				i18n.getString("mnrs3") +
				i18n.getString("mnrs4") +
				i18n.getString("mnrs5") +
				i18n.getString("mnrs6") +
				i18n.getString("mnrs7"),
				i18n.getString("mnrs8"),
				JOptionPane.ERROR_MESSAGE);	
	}	
	
	protected void desincronizacionSecuencia(){
		JOptionPane.showMessageDialog(jFrameCEALCON,
				i18n.getString("mds1") +
				i18n.getString("mds2") +
				i18n.getString("mds3") +
				i18n.getString("mds4"),
				i18n.getString("mds5"),
				JOptionPane.ERROR_MESSAGE);
	}
}