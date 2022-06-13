package org.myoggradio.stbko.impl;
import java.io.File;
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
				XMLKOTurnierSaver xml = new XMLKOTurnierSaver();
				xml.save(turnier,aus);
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
