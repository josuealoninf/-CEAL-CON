package mediador;

import java.awt.AWTEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;


/**
 * Interface IMediador
 * Hereda de Action Listener, MouseListener, WindowListener para desacoplar del mediador concreto a todos los colegas
 * @author Alatriste
 * @version 0.3
 */
public interface IMediador extends ActionListener, MouseListener, WindowListener{

	//Comandos de patrón mediador
	public void registrarJFrameCEALCON(IMediador mediador);
	public void registrarPaneldeJuego(IMediador mediador);
	public void registrarDialogoConexion(IMediador mediador);
	public void registrarDialogoCifrarRCON(IMediador mediador);
	public void registrarDialogoConfiguracion(IMediador mediador);
	public void registrarDialogoAcercade(IMediador mediador);
	
	//Comandos del menú
	public void comandoMenuConexion();	
	public void comandoMenuConfiguracion();
	public void comandoMenuCifrarRCON();	
	public void comandoMenuAcercade();
		
	//Comandos del dialogo de conexión
	public void comandoConectar();	
	public void comandoGuardarServer();
	public void comandoCargarServer();
	public void comandoBorrarServer();
	public void comandoJuegos(AWTEvent event);
	
	//Comandos del dialogo de configuracion
	public void comandoGuardarConfiguracion();
	public void comandoCancelarConfiguracion();
	
	//Comandos comunes
	public void comandoAcercade();
	public void comandoCifrarRCON();	
	public void comandoRefresco();		
	public void comandoCerrar();
	
	//Comandos particulares de CODWW
	public void comandoCODWWChat();	
	public void comandoCODWWChatPrivado();
	public void comandoCODWWKickeo_BaneoTemporal(boolean privado);
	public void comandoCODWWNextMap();
	public void comandoCODWWFastRestart();
	
	//Comandos particulares de BFBC2
	public void comandoBFBC2YellyYellPrivado(boolean privado);
	public void comandoBFBC2ChatyChatPrivado(boolean privado);
	public void comandoBFBC2Move();
	public void comandoBFBC2Kick();
	public void comandoBFBC2BaneoTemporal();	
	public void comandoBFBC2NextMap();
	public void comandoBFBC2RestartMap();
}
