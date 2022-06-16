package org.myoggradio.stbjgj;
import java.util.ArrayList;
import org.myoggradio.stb.Auswertung;
public interface JGJTurnierManager 
{
	public void starteFolgeRunden(JGJTurnier turnier);
	public void starteErsteRunde(JGJTurnier turnier);
	public ArrayList<Auswertung> getAuswertung(int rundenNummer);
}
