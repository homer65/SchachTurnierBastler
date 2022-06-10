package org.myoggradio.stb;
import java.util.ArrayList;
public interface TurnierManager 
{
	public Runde starteNaechsteRunde(Turnier turnier);
	public ArrayList<Auswertung> getAuswertung(int rundenNummer);
}
