package org.myoggradio.stbjgj.impl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
			FileOutputStream fos = new FileOutputStream(aus);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(JGJParameter.turnier);
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
