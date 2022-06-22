package org.myoggradio.stb.impl;
import java.io.File;
import org.myoggradio.stb.*;
public class FileTurnierAutoSaver implements TurnierAutoSaver
{
	@Override
	public void save(Turnier turnier) 
	{
		try
		{
			if (Parameter.autoSaveDirectory != null)
			{
				File test = new File(Parameter.autoSaveDirectory);
				if (!test.isDirectory())
				{
					Parameter.autoSaveDirectory = ".";
				}
			}
			else 
			{
				Parameter.autoSaveDirectory = ".";
			}
			int n = turnier.getNummerAktiveRunde();
			File aus = new File(Parameter.autoSaveDirectory + File.separator + "SchachTurnierBastler-Turnier-Runde-" + n + "-AutoSave.stb");
			String pfad = aus.getAbsolutePath();
			Protokol.write("FileTurnierAutosaver:save: " + pfad);
			XMLTurnierSaver xml = new XMLTurnierSaver();
			xml.save(turnier,aus);
		}
		catch (Exception e)
		{
			Protokol.write("FileTurnierAutoSaver:save:Exception:");
			Protokol.write(e.toString());
		}
	}
}
