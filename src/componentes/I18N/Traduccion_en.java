package componentes.I18N;

import java.util.ListResourceBundle;

public final class Traduccion_en extends ListResourceBundle{

	private static final Object[][] clave_traduccion = {
		
		//Mediador
		
		//Mediador.comandoGuardarServer
        {"mcg1", "Data saved properly.\n"},  
        {"mcg2", "Saving data..."},
        {"mcg3", "Impossible to save the data. \n"},                    
        {"mcg4", "Error making the file"},
        {"mcg5", "There is already a saved server with this name.\n"},                    
        {"mcg6", "¿Do you want to override the changes?"},
        {"mcg7", "Duplicate name"},
        
        //Mediador.comandoCifrarRCON
        {"mccr1", "You can not cipher an empty RCON.\n"},  
        {"mccr2", "Modify yours server's RCON."},                              
        {"mccr3", "Empty RCON"},
        
        {"mccr4", "The following encrypted RCON: \n\n"},  
        {"mccr5", "has been copied into your clipboard.\n\n"},                              
        {"mccr6", "Give it to your member's clan and / or paste it\n"},
        {"mccr7", "through CONTROL+V inmediately in the connection menu\n"},  
        {"mccr8", "to access rigth now to your server."},                              
        {"mccr9", "RCON encrypted"},
        
        //Mediador.comandoConectar
        {"mcc1", "Impossible to connect [CEAL]CON. Unknown Host.\n"},  
        {"mcc2", "Make sure the data is correct and try again.\n"},                              
        {"mcc3", "Error connecting..."},  
        
        {"mcc4", "Impossible to connect [CEAL]CON.\n"},                      
        {"mcc5", "Make sure the ip, the puerto and the password typed are correct,\n"},                    
        {"mcc6", "match with an active server and try again.\n"},
        {"mcc7", "Error connecting..."},
        
        {"mcc8", "Impossible to connect [CEAL]CON.\n"},                              
        {"mcc9", "Make sure the encrypted RCON typed \n"},                       
        {"mcc10", "is correct. If you are sure, probably\n"},                      
        {"mcc11", "the RCON has changed. Ask your admin."},                    
        {"mcc12", "Incorrect password"},
        
        //Mediador.comandoAcercade
        {"mcad1", "http://www.cealweb.net"},  
        {"mcad2", "The browser can not be opened.\n"},                              
        {"mcad3", "To visit our web go to http://www.cealweb.net"},
        {"mcad4", "Error opening browser"},
        
        //Mediador.comandoCerrar
        {"mcce1", "This action will close [CEAL]CON.\n"},  
        {"mcce2", "¿Are you sure?\n"},                              
        {"mcce3", "Closing..."},
        
        //Mediador.cargarDatosLogyLocale
        {"mcdlyl1", "Impossible to open [CEAL]CON configuration file data.\n"},  
        {"mcdlyl2", "Maybe bin folder has been modified, damaged or deleted. Restore it.\n"},                              
        {"mcdlyl3", "Now default data will be loaded."},        
        {"mcdlyl4", "Error opening files"},       
        
        {"mcdlyl5", "Impossible to handle the files.\n"},                    
        {"mcdlyl6", "Maybe bin folder has been modified or damaged. Restore it.\n"},
        {"mcdlyl7", "Now default data will be loaded."},  
        {"mcdlyl8", "Error handling files"},  
        
        //Mediador.validacionCamposDialogoConexion
        {"mvcdc1", "Fill the empty fields.\n"},  
        {"mvcdc2", "Fill the gaps"},                              
        {"mvcdc3", "You MUST choose a game to connect to.\n"},                       
        {"mvcdc4", "Choose a game"},                      
        {"mvcdc5", "The port MUST be a number.\n"},                    
        {"mvcdc6", "Impossible port"},
        {"mvcdc7", "The port cannot be greater than 65535\n"},                    
        {"mvcdc8", "Impossible port"},
        
        //Mediador.chequear_tablaactualizada_jugadorseleccionado
        {"mctaj1", "Table MUST be updated to choose a player.\n"},
        {"mctaj2", "Push REFRESH"},
        {"mctaj3", "Push REFRESH"},
        {"mctaj4", "You MUST choose a player.\n"},                      
        {"mctaj5", "Choose a player"},
        
        //Mediador.noRespondeServidor
        {"mnrs1", "The server has not replyed.\n"},
        {"mnrs2", "This is, maybe, because some data packet has been lost,\n"},
        {"mnrs3", "which happens when the server is changing the map, for example.\n"},
        {"mnrs4", "Other reasons are that the connection is failing or \n"},
        {"mnrs5", "that the current rcon command has not been finished properly.\n"},                    
        {"mnrs6", "Make sure the connection with the server is still alive\n"},
        {"mnrs7", "and try again in a few seconds."},  
        {"mnrs8", "The server has not replyed"},
        
        //Mediador.desincronizacionSecuencia
        {"mds1", "Fail in [CEAL]CON.\n"},
        {"mds2", "Desynchronization has been detected between [CEAL]CON and server.\n"},
        {"mds3", "Maybe [CEAL]CON is frozen or shows incorrect data.\n"},
        {"mds4", "If you see this message several times, restart [CEAL]CON and try again.\n"},                      
        {"mds5", "Desynchronization error"},
        
        //Mediador.comandoBorrarServer
        {"mcbs1", "Configuration server data deleted properly."},
        {"mcbs2", "File deleted"},
        {"mcbs3", "The selected configuration can not be deleted."},
        {"mcbs4", "File NOT deleted"},
        
        {"mcbs5", "Choose a server to delete its data.\n"},  
        {"mcbs6", "Choose a server"},  
        
        //Mediador.comandoCargarServer
        {"mccs1", "Impossible to open data server file.\n"},  
        {"mccs2", "Maybe bin folder has been modified, damaged or deleted. Restore it.\n"},                              
        {"mccs3", "No data will be loaded."},        
        {"mccs4", "Error opening file"},       
        
        {"mccs5", "Impossible to handle the files.\n"},                    
        {"mccs6", "Maybe bin folder has been modified or damaged. Restore it.\n"},
        {"mccs7", "The data you see probably are incorrect. Check it out."},  
        {"mccs8", "Error handling files"},  
        
        {"mccs9", "Stored game of the server was incorrect.\n"},                       
        {"mccs10", "Probably was broked or modified \n"},                      
        {"mccs11", "by hand in configuration files.\n"},                    
        {"mccs12", "Choose the correct one and save configuration."},
        {"mccs13", "Impossible game"}, 
        
        {"mccs14", "Choose a server to load its data.\n"},  
        {"mccs15", "Choose a server"},  
        
        //Mediador.comandoGuardarConfiguracion
        {"mcgyc1", "Data saved properly.\n"},  
        {"mcgyc2", "A restart is needed to make effect."},                              
        {"mcgyc3", "Change in configuration"},        
        
        {"mcgyc4", "Impossible to open [CEAL]CON configuration files\n"},  
        {"mcgyc5", "Maybe bin folder has been modified, damaged or deleted. Restore it.\n"},                              
        {"mcgyc6", "Now default data will be loaded."},        
        {"mcgyc7", "Error opening file"},       
        
        {"mcgyc8", "Impossible to handle the files.\n"},                    
        {"mcgyc9", "Maybe bin folder has been modified or damaged. Restore it.\n"},
        {"mcgyc10", "Now default data will be loaded."},  
        {"mcgyc11", "Error handling files"},
        
        //Mediador.comandoMenuAyuda
        {"mcma1", "Help file can not be opened.\n"},  
        {"mcma2", "Error opening web browser"},  
        
        //MediadorBFBC2
        
        //MediadorBFBC2.comandoBFBC2Move
        {"mbfbc2cm1", "Player with name "},
        {"mbfbc2cm2", " moved properly\n to team: "},
        {"mbfbc2cm3", " squad: "},
        {"mbfbc2cm4", "Player moved"},
        
        {"mbfbc2cm5", "Server has replyed unknown answer.\n"},
        {"mbfbc2cm6", "Refresh, check and try again."},
        {"mbfbc2cm7", "Error in command"},
        
        {"mbfbc2cm8", "The team number "},
        {"mbfbc2cm9", " is invalid."},
        {"mbfbc2cm10", "InvalidTeamId"},
        
        {"mbfbc2cm11", "The squad number "},
        {"mbfbc2cm12", " is invalid."},
        {"mbfbc2cm13", "InvalidSquadId"},
        
        {"mbfbc2cm14", "Player with name "},
        {"mbfbc2cm15", " is not dead.\n Click in Force death and try again."},
        {"mbfbc2cm16", "PlayerNotDead"},
        
        {"mbfbc2cm17", "Player with name "},
        {"mbfbc2cm18", "\n can not be assigned to team number "},
        {"mbfbc2cm19", "SetTeamFailed"},
        
        {"mbfbc2cm20", "Player with name "},
        {"mbfbc2cm21", "\n can not be assigned to squad number "},
        {"mbfbc2cm22", "SetSquadFailed"},
        
        {"mbfbc2cm23", "You MUST choose a team AND\n"},
        {"mbfbc2cm24", "a squad to move a player."},
        {"mbfbc2cm25", "Missing data"},
        
        //MediadorBFBC2.comandoBFBC2Kick
        {"mbfbc2ck1", "Player kicked."},
        {"mbfbc2ck2", "Player kicked"}, 
        
        {"mbfbc2ck3", "The slot is empty and nobody can be kicked.\n"},
        {"mbfbc2ck4", "Maybe player leaved it \n"},
        {"mbfbc2ck5", "after refresh.\n"},
        {"mbfbc2ck6", "Refresh and try again."},                      
        {"mbfbc2ck7", "Nobody kicked"},
        
        {"mbfbc2ck8", "Server has replyed unknown answer.\n"},
        {"mbfbc2ck9", "Refresh, check and try again."},
        {"mbfbc2ck10", "Error in command"},
        
        {"mbfbc2ck11", "The message can not be longer than "},
        {"mbfbc2ck12", " chars.\n"}, 
        {"mbfbc2ck13", "Kick reason"},        
        
        {"mbfbc2ck14", "You MUST type a reason to kick a player.\n"},
        {"mbfbc2ck15", "Kick reason"}, 
        
        //MediadorBFBC2.comandoBFBC2BaneoTemporal
        {"mbfbc2bt1", "Player banned properly for "},
        {"mbfbc2bt2", " minutes."},
        {"mbfbc2bt3", "Player banned"},
        
        {"mbfbc2bt4", "Ban list is full and player can not be banned.\n"},
        {"mbfbc2bt5", "Ask your admin to empty ban list\n"},
        {"mbfbc2bt6", "and try again.\n"},
        {"mbfbc2bt7", "Full Ban List"},
        
        {"mbfbc2bt8", "Server has replyed unknown answer.\n"},
        {"mbfbc2bt9", "Refresh, check and try again."},
        {"mbfbc2bt10", "Error in command"},
        
        {"mbfbc2bt11", "The message can not be longer than "},
        {"mbfbc2bt12", " chars.\n"},
        {"mbfbc2bt13", "Ban reason"},
        
        {"mbfbc2bt14", "You MUST type a reason to ban a player AND\n"},
        {"mbfbc2bt15", "choose the time in minutes to ban him"},
        {"mbfbc2bt16", "Missing data"},
        
        //MediadorBFBC2.comandoBFBC2ChatyChatPrivado
        {"mbfbc2ccp1", "The message can not be longer than "},
        {"mbfbc2ccp2", " chars.\n"},
        {"mbfbc2ccp3", "Talk in the chat"},
        
        {"mbfbc2ccp4", "You MUST type something to talk in the chat.\n"},
        {"mbfbc2ccp5", "Talk in the chat"},
        
        //MediadorBFBC2.comandoBFBC2YellyYellPrivado
        {"mbfbc2cyyp1", "The message can not be longer than "},
        {"mbfbc2cyyp2", " chars.\n"},
        {"mbfbc2cyyp3", "Yelling"},
        
        {"mbfbc2cyyp4", "You MUST type something to yell AND\n"},
        {"mbfbc2cyyp5", "choose the time in seconds to yell it"},
        {"mbfbc2cyyp6", "Missing data"},    
        
        //MediadorBFBC2.comandoBFBC2NextMap
        {"mbfbc2cnm1", "Server is loading next map."},  
        {"mbfbc2cnm2", "Next Map"},
        
        //MediadorBFBC2.comandoBFBC2RestartMap
        {"mbfbc2crm1", "Server is restarting current map"},  
        {"mbfbc2crm2", "Map restart"},
        
        //MediadorCODWW
        
        //MediadorCODWW.comandoCODWWChat
        {"mcodwwcc1", "You MUST type something to talk in the chat.\n"},
        {"mcodwwcc2", "Talk in the chat"},
        
        //MediadorCODWW.comandoCODWWChatPrivado
        {"mcodwwccp1", "You MUST type something to talk in the chat.\n"},
        {"mcodwwccp2", "Talk in the chat"},
        {"mcodwwccp3", " ^1 PRIVATE MESSAGE: ^7"},
        
        //MediadorCODWW.comandoCODWWNextMap
        {"mcodwwcnm1", "Server is loading next map."},  
        {"mcodwwcnm2", "Next Map"},
        
        //MediadorCODWW.comandoCODWWFastRestart
        {"mcodwwcrm1", "Server is restarting current map"},  
        {"mcodwwcrm2", "Map restart"},
        
        //MediadorCODWW.kickear_BanearTemporalmente_Jugador
        {"mcodwwkbtj1", "^7: Kicking "},
        {"mcodwwkbtj2", " Reason: "},
        
        {"mcodwwkbtj3", "Player is out of server."},
        {"mcodwwkbtj4", "Player ejected"},
        
        {"mcodwwkbtj5", "The slot is empty and nobody can be kicked.\n"},
        {"mcodwwkbtj6", "Maybe player leaved it \n"},
        {"mcodwwkbtj7", "after refresh.\n"},
        {"mcodwwkbtj8", "Refresh and try again."},                      
        {"mcodwwkbtj9", "Nobody kicked"},       
        
        {"mcodwwkbtj10", "The selected slot does not exists.\n"},
        {"mcodwwkbtj11", "Maybe the table data is corrupted.\n"},
        {"mcodwwkbtj12", "Refresh and try again."},
        {"mcodwwkbtj13", "Nobody kicked"},
        
        {"mcodwwkbtj14", "Server has replyed unknown answer.\n"},
        {"mcodwwkbtj15", "Maybe the player has been kicked or banned,\n"},
        {"mcodwwkbtj16", "but it is not sure.\n"},
        {"mcodwwkbtj17", "Refresh, check and try again."},
        {"mcodwwkbtj18", "Error in command"},  
        
        {"mcodwwkbtj19", "You MUST type a reason to kick/ban a player.\n"},
        {"mcodwwkbtj20", "Reason"},
        
        //JFrameCEALCON
        
        {"jf1", "[CEAL]CON 0.3"},
        {"jf2", "File"},
        {"jf3", "About"},
        {"jf4", "Connect [CEAL]CON"},
        {"jf5", "Configuration"},
        {"jf6", "Cipher RCON"},       
        {"jf8", "About [CEAL]CON"},
        
        //PanelBFBC2
        
        //PanelBFBC2.construirPanelNorte
        {"pbfbc2cpn1", "Change player"},
        {"pbfbc2cpn2", "TeamId"},
        {"pbfbc2cpn3", "SquadId"},
        {"pbfbc2cpn4", "Force death"},
        {"pbfbc2cpn5", "Players:"},
        {"pbfbc2cpn6", "Attackers(1):"},
        {"pbfbc2cpn7", "Defenders(2):"},
        {"pbfbc2cpn8", "Restart Map"},
        {"pbfbc2cpn9", "Next Map"},
        
        //PanelBFBC2.construirRefrescoyKick
        {"pbfbc2crk1", "REFRESH"},
        {"pbfbc2crk2", "Kick"},
        {"pbfbc2crk3", "Temporal Ban"},
        {"pbfbc2crk4", "Minutes"},
        {"pbfbc2crk5", "Actions"},
        {"pbfbc2crk6", "Click here to get player's data"},
        
        //PanelBFBC2.construirChat
        {"pbfbc2cc1", "Say"},
        {"pbfbc2cc2", "Private say"},
        {"pbfbc2cc3", "¡YELL!"},
        {"pbfbc2cc4", "¡PRIVATE YELL!"},
        {"pbfbc2cc5", "Seconds"},
        {"pbfbc2cc6", "Chat"},
        
        //PanelCODWW
        
        //PanelCODWW.construirPanelNorte
        {"pcodwwcpn1", "Fast_restart"},
        {"pcodwwcpn2", "Next Map"},
        
        //PanelCODWW.construirRefrescoyKick
        {"pcodwwcrk1", "REFRESH"},
        {"pcodwwcrk2", "Kick"},
        {"pcodwwcrk3", "Temporal Ban"},
        {"pcodwwcrk4", "Acciones"},
        
        //PanelCODWW.construirChat
        {"pcodwwcc1", "Private say"},
        {"pcodwwcc2", "Say"},
        {"pcodwwcc3", "Chat"},
        
        //HiloMensajeBFBC2
        
        {"hmbfbc21", "[CEAL]CON 0.3 monitoring..."},
        {"hmbfbc22", "Remote Access console made in [CEAL] clan http://www.cealweb.net"},
        
        //HiloMensajeCODWW
        
        {"hmcodww1", "^1[C^3EA^1L]^7CON ^10.3 monitoring..."},
        {"hmcodww2", "^1Remote Access console made in [C^3EA^1L] clan http://www.cealweb.net"},        
                
        //DialogoConexion
        
        {"dc1", "Connect"},
        {"dc2", "Nick"},
        {"dc3", "IP"},
        {"dc4", "Port"},
        {"dc5", "Encrypted RCON"},
        {"dc6", "Choose a game"},
        {"dc7", "Connect"},
        {"dc8", "Save Server Data"},
        {"dc9", "Server Name"},
        {"dc10", "Load Server Data"},
        {"dc11", "Delete Server Data"},
        {"dc12", "Configuration data server saved:"},
        {"dc13", "Name typed here will be seen in the server when you exec a command"},
        {"dc14", "With this name server data will be saved if you push Save Server Data"},
        {"dc15", "Type here the Encrypted RCON that you server admin gave to you"},        
        
        //DialogoConfiguracion
        
        {"dcf1", "Configuration"},
        {"dcf2", "Log level"},
        {"dcf3", "Language"},
        {"dcf4", "Save"},  
        {"dcf5", "Cancel"},   
        
        //DialogoCifrarRCON
        
        {"dcrcon1", "Cipher RCON"},
        {"dcrcon2", "RCON"},
        {"dcrcon3", "Cipher RCON"},
        
        //DialogoAcercade
        
        {"da1", "About"},
        {"da2", "[CEAL]CON 0.3\n"},
        {"da3", "Remote Access console made in [CEAL]"},
        {"da4", "Concrete development for Call Of Duty World at War and BFBC2"},
        {"da5", "Check out our web, click in the logo:"}        
    };
	
	@Override
	public Object[][] getContents(){
		return clave_traduccion;
	}
}
