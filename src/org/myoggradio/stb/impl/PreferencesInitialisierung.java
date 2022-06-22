package org.myoggradio.stb.impl;
import java.util.prefs.Preferences;
import org.myoggradio.stb.Initialisierung;
import org.myoggradio.stb.Parameter;
public class PreferencesInitialisierung implements Initialisierung
{

	@Override
	public void start() 
	{
		Preferences prefs = Preferences.userRoot();	
		String anzahlRunden = prefs.get("SchachTurnierBastler_anzahlRunden","7");
		Parameter.anzahlRunden = Integer.parseInt(anzahlRunden);
		String maxiter = prefs.get("SchachTurnierBastler_maxiter","500000");
		Parameter.maxiter = Integer.parseInt(maxiter);
		String itermsg = prefs.get("SchachTurnierBastler_itermsg","50000");
		Parameter.itermsg = Integer.parseInt(itermsg);
		Parameter.autoSaveDirectory = prefs.get("SchachTurnierBastler_autoSaveDirectory", ".");
	}
}
