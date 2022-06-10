package org.myoggradio.stb;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
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
			FileOutputStream fos = new FileOutputStream(aus);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(Parameter.turnier);
			oos.flush();
			oos.close();
		}
		catch (Exception e)
		{
			Protokol.write("Shutdown:run:Exception:");
			Protokol.write(e.toString());
		}
	}
}
