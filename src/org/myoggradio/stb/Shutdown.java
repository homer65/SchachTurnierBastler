package org.myoggradio.stb;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.myoggradio.stb.impl.XMLTurnierSaver;
public class Shutdown extends Thread
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
			File aus = new File(Parameter.autoSaveDirectory + File.separator + "SchachTurnierBastler-Turnier-Shutdown-AutoSave" + datum + ".stb");
			String pfad = aus.getAbsolutePath();
			Protokol.write("Shutdown:save: " + pfad);
			XMLTurnierSaver xml = new XMLTurnierSaver();
			xml.save(Parameter.turnier,aus);
			File aus2 = new File(Parameter.autoSaveDirectory + File.separator + "SchachTurnierBastler-Shutdown-AutoSave.stb");
			xml.save(Parameter.turnier,aus2);
		}
		catch (Exception e)
		{
			Protokol.write("Shutdown:run:Exception:");
			Protokol.write(e.toString());
		}
	}
}
