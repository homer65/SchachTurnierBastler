package org.myoggradio.stbjgj;
import java.util.ArrayList;
import org.myoggradio.stb.Auswertung;
import org.myoggradio.stb.Spieler;
public interface JGJTurnierManager 
{
	public void starteFolgeRunden(JGJTurnier turnier);
	public void starteErsteRunde(JGJTurnier turnier);
	public ArrayList<Auswertung> getAuswertung(int rundenNummer);
	public String getErgebnis(JGJTurnier turnier,Spieler spieler1,Spieler spieler2,int rundenummer);
}
