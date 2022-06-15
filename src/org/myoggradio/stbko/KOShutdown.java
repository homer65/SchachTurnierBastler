package org.myoggradio.stbko;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
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
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date jetzt = new Date();
			String datum = sdf.format(jetzt);
			File aus = new File("SchachTurnierBastler-KOTurnier-Shutdown-AutoSave" + datum + ".stb");
			String pfad = aus.getAbsolutePath();
			Protokol.write("KOShutdown:save: " + pfad);
			XMLKOTurnierSaver xml = new XMLKOTurnierSaver();
			xml.save(KOParameter.turnier,aus);
		}
		catch (Exception e)
		{
			Protokol.write("KOShutdown:run:Exception:");
			Protokol.write(e.toString());
		}
	}
}
