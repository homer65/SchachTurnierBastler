package org.myoggradio.stb;
public class Main 
{
	public static void main(String[] args) // Spieler in Turnierphase l�schen
	{
		Initialisierung ini = Factory.getInitialisierung();
		ini.start();
		MainMenu mm = Factory.getMainMenu();
		mm.anzeigen();
	}
}
