package org.myoggradio.stb;
import java.util.ArrayList;
public interface SpielerStornierenDialog 
{
	/*
	 * Dilog um Spieler aus laufendem Turnier zu entfernen
	 */
	public void setSpieler(ArrayList<Spieler> spieler);
	public void anzeigen();
}
