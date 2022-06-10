package org.myoggradio.stb.impl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import org.myoggradio.stb.*;
public class FileTurnierAutoSaver implements TurnierAutoSaver
{
	@Override
	public void save(Turnier turnier) 
	{
		try
		{
			int n = turnier.getNummerAktiveRunde();
			File aus = new File("SchachTurnierBastler-Turnier-Runde-" + n + "-AutoSave.stb");
			String pfad = aus.getAbsolutePath();
			Protokol.write("FileTurnierAutosaver:save: " + pfad);
			FileOutputStream fos = new FileOutputStream(aus);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(turnier);
			oos.flush();
			oos.close();
		}
		catch (Exception e)
		{
			Protokol.write("FileTurnierAutoSaver:save:Exception:");
			Protokol.write(e.toString());
		}
	}
}
