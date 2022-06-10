package org.myoggradio.stbko.impl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
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
			File aus = new File("SchachTurnierBastler-KOTurnier-Runde-" + n + "-AutoSave.stb");
			String pfad = aus.getAbsolutePath();
			Protokol.write("SimpleKOTurnierAutosaver:save: " + pfad);
			FileOutputStream fos = new FileOutputStream(aus);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(turnier);
			oos.flush();
			oos.close();
		}
		catch (Exception e)
		{
			Protokol.write("SimpleKOTurnierAutoSaver:save:Exception:");
			Protokol.write(e.toString());
		}
	}
}
