package org.myoggradio.stbko.impl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import org.myoggradio.stb.*;
import org.myoggradio.stbko.*;
public class SimpleKOTurnierSaver implements KOTurnierSaver
{
	@Override
	public void save(KOTurnier turnier) 
	{
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("."));
		int rc = fc.showSaveDialog(null);
		if (rc == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				File aus = fc.getSelectedFile();
				FileOutputStream fos = new FileOutputStream(aus);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(turnier);
				oos.flush();
				oos.close();
			}
			catch (Exception e)
			{
				Protokol.write("SimpleKOTurnierSaver:save:Exception:");
				Protokol.write(e.toString());
			}
		}
		return;
	}
}
