package org.myoggradio.stb.impl;
import java.io.File;
import javax.swing.JFileChooser;
import org.myoggradio.stb.*;
public class FileTurnierSaver implements TurnierSaver
{
	@Override
	public void save(Turnier turnier) 
	{
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("."));
		int rc = fc.showSaveDialog(null);
		if (rc == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				File aus = fc.getSelectedFile();
				XMLTurnierSaver xml = new XMLTurnierSaver();
				xml.save(turnier,aus);
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
