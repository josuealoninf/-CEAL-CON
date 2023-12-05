package colegas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import mediador.IMediador;

import comandos.comandosMediador.ComandoAcercade;

/**
 * DialogoAcercade
 * @author Alatriste
 * @version 0.2
 */
public final class DialogoAcercade extends JDialog implements IColega{	
	
	/** Para que no de el coñazo esto y cumpla con las novedades de la 1.6, creo*/
	private static final long serialVersionUID = 752283364257259992L;
	
	/** El mediador correspondiente*/	
	private IMediador mediador;	
	/** Agrupa todas las etiquetas de texto para mostrarlas conjuntamente*/
	private JPanel panelEtiquetas;
	private JLabel imagenCEAL;
	private JLabel etiqueta1;
	private JLabel etiqueta2;
	private JLabel etiqueta3;
	private JLabel etiqueta4;
	/** Es el boton que abre nuestra pagina web*/
	private JButton botonCEAL;
	
	/**
	 * Constructor del DialogoAcercade. Se ejecuta cuando se registra en el mediador para tener una instancia de el
	 * y mostrarlo cuando nos interese
	 * 
	 * @param frame el frame sobre el que va mostrado, que es JFrameCEALCON
	 * @param i18n cadenas de traducción en función del lenguaje
	 * @param modal Es un dialogo modal y va a true
	 */
	public DialogoAcercade(JFrame frame, ResourceBundle i18n, boolean modal, ImageIcon iconoCEAL){
		//llamamos al constructor de la superclase, q para algo heredamos de JDialog
		super(frame, i18n.getString("da1"), modal);		
		
		panelEtiquetas = new JPanel();
		
		imagenCEAL = new JLabel(iconoCEAL, SwingConstants.CENTER);
		
		etiqueta1 = new JLabel(i18n.getString("da2"), SwingConstants.CENTER);
		etiqueta2 = new JLabel(i18n.getString("da3"), SwingConstants.CENTER);
		etiqueta3 = new JLabel(i18n.getString("da4"), SwingConstants.CENTER);
		etiqueta4 = new JLabel(i18n.getString("da5"), SwingConstants.CENTER);	
		
		botonCEAL = new ComandoAcercade(new ImageIcon("bin\\images\\logo.jpg"));
			
		panelEtiquetas.setLayout(new GridLayout(0,1));
		
		panelEtiquetas.add(etiqueta1);
		panelEtiquetas.add(etiqueta2);
		panelEtiquetas.add(etiqueta3);
		panelEtiquetas.add(etiqueta4);	

		this.getContentPane().add(imagenCEAL, BorderLayout.NORTH);
		this.getContentPane().add(panelEtiquetas, BorderLayout.CENTER);
		this.getContentPane().add(botonCEAL, BorderLayout.SOUTH);
		
		this.setSize(450, 423);
		//NO queremos que cambie su tamaño
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
	 * Añadimos los oyentes a los botones
	 * @param mediador para añadir los oyentes a los botones
	 */
	public void añadirOyentes(){
		botonCEAL.addActionListener(mediador);		
	}		
}
