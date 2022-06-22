package org.myoggradio.stbjgj.impl;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.myoggradio.stb.Parameter;
import org.myoggradio.stb.Protokol;
import org.myoggradio.stbjgj.JGJParameter;
public class JGJShutdown extends Thread
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
			File aus = new File(Parameter.autoSaveDirectory + File.separator + "SchachTurnierBastler-JGJTurnier-Shutdown-AutoSave" + datum + ".stb");
			String pfad = aus.getAbsolutePath();
			Protokol.write("JGJShutdown:save: " + pfad);
			XMLJGJTurnierSaver xml = new XMLJGJTurnierSaver();
			xml.save(JGJParameter.turnier,aus);
			File aus2 = new File(Parameter.autoSaveDirectory + File.separator + "SchachTurnierBastler-Shutdown-AutoSave.stb");
			xml.save(JGJParameter.turnier,aus2);
		}
		catch (Exception e)
		{
			Protokol.write("JGJShutdown:run:Exception:");
			Protokol.write(e.toString());
		}
	}
}
