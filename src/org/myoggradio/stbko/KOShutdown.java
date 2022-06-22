package org.myoggradio.stbko;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.myoggradio.stb.Parameter;
import org.myoggradio.stb.Protokol;
import org.myoggradio.stbko.impl.XMLKOTurnierSaver;
public class KOShutdown extends Thread
{
	private String pattern = "yyyy-MM-dd_hh-mm-ss";
	@Override
	public void run() 
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
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date jetzt = new Date();
			String datum = sdf.format(jetzt);
			File aus = new File(Parameter.autoSaveDirectory + File.separator + "SchachTurnierBastler-KOTurnier-Shutdown-AutoSave" + datum + ".stb");
			String pfad = aus.getAbsolutePath();
			Protokol.write("KOShutdown:save: " + pfad);
			XMLKOTurnierSaver xml = new XMLKOTurnierSaver();
			xml.save(KOParameter.turnier,aus);
			File aus2 = new File(Parameter.autoSaveDirectory + File.separator + "SchachTurnierBastler-Shutdown-AutoSave.stb");
			xml.save(KOParameter.turnier,aus2);
		}
		catch (Exception e)
		{
			Protokol.write("KOShutdown:run:Exception:");
			Protokol.write(e.toString());
		}
	}
}
