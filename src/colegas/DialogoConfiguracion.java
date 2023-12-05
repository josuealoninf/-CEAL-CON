package colegas;

import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mediador.IMediador;

import comandos.comandosMediador.ComandoCancelarConfiguracion;
import comandos.comandosMediador.ComandoGuardarConfiguracion;

/**
 * DialogoConfiguracion. Muestra la interfaz necesaria para configurar [CEAL]CON
 * @author Alatriste
 * @version 0.3
 */
public final class DialogoConfiguracion extends JDialog implements IColega{
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = 6289115289044613652L;
	
	/** El mediador correspondiente*/	
	private IMediador mediador;
	
	/** Las opciones de log que le daremos a elegir la usuario */
	private static final String[] posibilidadesdelog = {"OFF", "ALL"};
	/** Los lenguajes que soporta CEALCON actualmente*/
	private static final String[] lenguajes = {"English", "Español"};
	
	/** El panel que contiene el dialogo configuracion*/
	private JPanel panelConfiguracion;	
	/** La etiqueta del nivel de log*/
	private JLabel etiquetanivelLog;
	/** La etiqueta de los idiomas disponibles */
	private JLabel etiquetalanguage;
	/** Guarda la configuracion seleccionada */	
	private JButton botonGuardarConfiguracion;
	/** Para cerrar el dialogo y que cuadren bien las dos filas de columnas */
	private JButton botonCancelarConfiguracion;
	/** Los niveles de log disponibles */
	private JComboBox nivelLog;
	/** Los idiomas disponibles*/
	private JComboBox language;
	
	/** Hacemos la conversion del locale literal "en y es" a una posicion del array de lenguajes */
	private int localeActual;
	
	public DialogoConfiguracion(JFrame frame, ResourceBundle i18n, boolean modal, int nivelLogActual){
		//llamamos al constructor de la superclase, que para algo heredamos de JDialog
		super(frame, i18n.getString("dcf1"), modal);	
				
		panelConfiguracion = new JPanel();		
		
		etiquetanivelLog = new JLabel(i18n.getString("dcf2"), SwingConstants.CENTER);
		etiquetalanguage = new JLabel(i18n.getString("dcf3"), SwingConstants.CENTER);
		
		nivelLog = new JComboBox(posibilidadesdelog);
		language = new JComboBox(lenguajes);
		
		//Ponemos los rolldown en los valores que tenemos guardados en el fichero de configuracion
		nivelLog.setSelectedIndex(nivelLogActual);
		localeActual = i18n.getLocale().getLanguage().equals("en") ? 0 : 1;
		language.setSelectedIndex(localeActual);
		
		botonGuardarConfiguracion = new ComandoGuardarConfiguracion(i18n.getString("dcf4"));
		botonCancelarConfiguracion = new ComandoCancelarConfiguracion(i18n.getString("dcf5"));
				
		panelConfiguracion.setLayout(new GridLayout(0,2,5,5));
		
		panelConfiguracion.add(etiquetanivelLog);
		panelConfiguracion.add(nivelLog);
		
		panelConfiguracion.add(etiquetalanguage);
		panelConfiguracion.add(language);
		
		panelConfiguracion.add(botonGuardarConfiguracion);	
		panelConfiguracion.add(botonCancelarConfiguracion);
		
		this.setContentPane(panelConfiguracion);
		 
	    this.setSize(300, 130);	    
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
		botonGuardarConfiguracion.addActionListener(mediador);	
		botonCancelarConfiguracion.addActionListener(mediador);
	}

	public JComboBox getNivelLog() {
		return nivelLog;
	}

	public JComboBox getLanguage() {
		return language;
	}
}