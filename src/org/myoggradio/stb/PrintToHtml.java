package org.myoggradio.stb;
import java.util.ArrayList;
import org.myoggradio.stbjgj.JGJRunde;
import org.myoggradio.stbjgj.JGJTurnier;
import org.myoggradio.stbko.KORunde;
public interface PrintToHtml 
{
	public void print(Runde runde,int rundenummer);
	public void print(KORunde runde);
	public void print(JGJRunde runde,int rundenummer);
	public void print(ArrayList<Auswertung> auswertungen);
	public void print(JGJTurnier turnier,int rundenummer);
}
