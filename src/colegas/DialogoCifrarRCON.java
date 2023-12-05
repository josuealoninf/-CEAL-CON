package colegas;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import mediador.IMediador;

import comandos.comandosMediador.ComandoCifrarRCON;


/**
 * DialogoCifrarRCON
 * @author Alatriste
 * @version 0.2
 */
public final class DialogoCifrarRCON extends JDialog implements IColega{
		
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = 8768367263389461807L;
	
	/** El mediador correspondiente*/	
	private IMediador mediador;	
	
	/** Si se meten las cosas directamente en el diálogo y éste se abre repetidas veces, se añaden elementos repetidas veces porque siempre se 
	 * llama a crear diálogo*/	
	private JPanel panelCifrarRCON;	
	private JLabel etiquetaRCON;		
	private JTextField campoRCON;		
	private JButton botonCifrar;		

	/**
	 * Constructor de DialogoCifrarRCON Se encarga de generar el dialogo de cifrado de la RCON. 
	 * @param frame es el frame principal del q depende el dialogo. invisible hasta conexion realizada
	 * @param i18n cadenas de traducción en función del lenguaje
	 * @param modal esta a true para que el dialogo sea modal y no se hagan otras operaciones mientras
	 */
	public DialogoCifrarRCON(JFrame frame, ResourceBundle i18n, boolean modal){
		
		//llamamos al constructor de la superclase, que para algo heredamos de JDialog
		super(frame, i18n.getString("dcrcon1"), modal);			
		
		panelCifrarRCON = new JPanel();
		
		etiquetaRCON = new JLabel(i18n.getString("dcrcon2"), SwingConstants.CENTER);				
		campoRCON = new JTextField("");			
		botonCifrar = new ComandoCifrarRCON(i18n.getString("dcrcon3"));
		
		panelCifrarRCON.setLayout(new BorderLayout(50,5));
		
		panelCifrarRCON.add(etiquetaRCON, BorderLayout.WEST);
		panelCifrarRCON.add(campoRCON, BorderLayout.CENTER);
		
		panelCifrarRCON.add(botonCifrar, BorderLayout.SOUTH);		
		
		this.setContentPane(panelCifrarRCON);
		
		 //le damos tamaño, posicion y lo mostramos
	    this.setSize(650, 85);
	    this.setResizable(false);
	    this.setLocationRelativeTo(this.getParent());
	    //Lo ocultamos, ya lo sacaremos cuando nos lo pidan
	    this.setVisible(false);
	}
	
	/**
	 * Metodo llamado en el registro para tener la instacia del mediador
	 */
	public void setMediador(IMediador mediador){
		this.mediador = mediador;
	}
	
	/**
	 * Añadimos los oyentes a los botones
	 * @param mediador para añadir los oyentes a los botones
	 */
	public void añadirOyentes(){		
		botonCifrar.addActionListener(mediador);			
	}
	
	public JTextField getCampoRCON() {
		return campoRCON;
	}
}
