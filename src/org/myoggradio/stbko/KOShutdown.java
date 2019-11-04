package org.myoggradio.stbko;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import org.myoggradio.stb.Protokol;
public class KOShutdown extends Thread
{
	@Override
	public void run() 
	{
		try
		{
			File aus = new File("SchachTurnierBastler-KOTurnier-Shutdown-AutoSave.stb");
			String pfad = aus.getAbsolutePath();
			Protokol.write("KOShutdown:save: " + pfad);
			FileOutputStream fos = new FileOutputStream(aus);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(KOParameter.turnier);
			oos.flush();
			oos.close();
		}
		catch (Exception e)
		{
			Protokol.write("KOShutdown:run:Exception:");
			Protokol.write(e.toString());
		}
	}
}
