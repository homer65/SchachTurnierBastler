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
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date jetzt = new Date();
			String datum = sdf.format(jetzt);
			File aus = new File("SchachTurnierBastler-JGJTurnier-Shutdown-AutoSave" + datum + ".stb");
			String pfad = aus.getAbsolutePath();
			Protokol.write("JGJShutdown:save: " + pfad);
			XMLJGJTurnierSaver xml = new XMLJGJTurnierSaver();
			xml.save(JGJParameter.turnier,aus);
			File aus2 = new File("SchachTurnierBastler-Shutdown-AutoSave.stb");
			xml.save(JGJParameter.turnier,aus2);
		}
		catch (Exception e)
		{
			Protokol.write("JGJShutdown:run:Exception:");
			Protokol.write(e.toString());
		}
	}
}
