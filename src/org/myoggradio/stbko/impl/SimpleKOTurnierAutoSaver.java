package org.myoggradio.stbko.impl;
import java.io.File;
import org.myoggradio.stb.*;
import org.myoggradio.stbko.*;
public class SimpleKOTurnierAutoSaver implements KOTurnierAutoSaver
{
	@Override
	public void save(KOTurnier turnier) 
	{
		try
		{
			int n = turnier.getNummerAktiveRunde();
			File aus = new File(Parameter.autoSaveDirectory + File.separator + "SchachTurnierBastler-KOTurnier-Runde-" + n + "-AutoSave.stb");
			String pfad = aus.getAbsolutePath();
			Protokol.write("SimpleKOTurnierAutosaver:save: " + pfad);
			XMLKOTurnierSaver xml = new XMLKOTurnierSaver();
			xml.save(turnier,aus);
		}
		catch (Exception e)
		{
			Protokol.write("SimpleKOTurnierAutoSaver:save:Exception:");
			Protokol.write(e.toString());
		}
	}
}
