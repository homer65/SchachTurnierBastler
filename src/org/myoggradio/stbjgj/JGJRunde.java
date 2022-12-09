package org.myoggradio.stbjgj;
import org.myoggradio.stb.*;
public interface JGJRunde 
{
	public void setMaxPartien(int n);
	public void setPartie(Partie partie,int nummer);
	public int getMaxPartien();
	public Partie getPartie(int nummer);
	public boolean alleErgebnisEingetragen();
	public Spieler[] getSpieler();
	public void setSpieler(Spieler[] spieler);
	public String getErgebnis(Spieler spieler1,Spieler spieler2);
}