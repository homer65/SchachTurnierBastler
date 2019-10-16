package org.myoggradio.stb.impl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import javax.swing.JFileChooser;
import org.myoggradio.stb.*;
public class FileTurnierSaver implements TurnierSaver
{
	@Override
	public void save(Turnier turnier) 
	{
		JFileChooser fc = new JFileChooser();
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
				Protokol.write("FileTurnierSaver:save:Exception:");
				Protokol.write(e.toString());
			}
		}
		return;
	}
}
