package org.myoggradio.stb;
import java.util.ArrayList;
public interface Turnier 
{
	public void setSpieler(ArrayList<Spieler> spieler);
	public void setMaxRunden(int n);
	public void setNextRunde(Runde runde);
	public int getMaxrunden();
	public Runde getRunde(int nummer);
	public Runde getAktiveRunde();
	public int getNummerAktiveRunde();
	public int getUngerade();
	public ArrayList<Spieler> getSpieler();
	public void start();
	public boolean storniereAktiveRunde();
	public void storniereSpieler(Spieler spieler);
	public void ergaenzeSpieler(Spieler spieler);
	public void setMaxRundenPlus();
}
