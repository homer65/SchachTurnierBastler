package org.myoggradio.stb;
import java.io.File;
import org.myoggradio.stb.impl.XMLTurnierSaver;
public class Shutdown extends Thread
{
	@Override
	public void run() 
	{
		try
		{
			File aus = new File("SchachTurnierBastler-Turnier-Shutdown-AutoSave.stb");
			String pfad = aus.getAbsolutePath();
			Protokol.write("Shutdown:save: " + pfad);
			XMLTurnierSaver xml = new XMLTurnierSaver();
			xml.save(Parameter.turnier,aus);
		}
		catch (Exception e)
		{
			Protokol.write("Shutdown:run:Exception:");
			Protokol.write(e.toString());
		}
	}
}
