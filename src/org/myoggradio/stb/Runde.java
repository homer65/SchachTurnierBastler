package org.myoggradio.stb;
import java.util.ArrayList;
public interface Runde 
{
	public void setMaxPartien(int n);
	public void setPartie(Partie partie,int nummer);
	public void addFreilos(Spieler spieler);
	public void removeFreilos();
	public int getMaxPartien();
	public Partie getPartie(int nummer);
	public ArrayList<Spieler> getFreilos();
	public boolean alleErgebnisEingetragen();
	public void storniereSpieler(Spieler spieler);
	public void ergaenzeSpieler(Spieler spieler);
}
