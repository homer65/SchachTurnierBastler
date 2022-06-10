package org.myoggradio.stbko;
import org.myoggradio.stb.*;
import java.util.ArrayList;
public interface KOTurnier 
{
	public void setSpieler(ArrayList<Spieler> spieler);
	public void setNextRunde(KORunde runde);
	public KORunde getRunde(int nummer);
	public KORunde getAktiveRunde();
	public int getNummerAktiveRunde();
	public ArrayList<Spieler> getSpieler();
	public void start();
	public boolean storniereAktiveRunde();
}
