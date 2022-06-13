package org.myoggradio.stbko.impl;
import java.io.File;
import javax.swing.JFileChooser;
import org.myoggradio.stb.*;
import org.myoggradio.stbko.*;
public class SimpleKOTurnierLoader implements KOTurnierLoader
{
	@Override
	public KOTurnier load() 
	{
		KOTurnier erg = null;
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("."));
		int rc = fc.showOpenDialog(null);
		if (rc == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				File ein = fc.getSelectedFile();
				XMLKOTurnierLoader xml = new XMLKOTurnierLoader();
				erg = xml.load(ein);
				Parameter.spieler = erg.getSpieler();
			}
			catch (Exception e)
			{
				Protokol.write("SimpleKOTurnierLoader:load:Exception:");
				Protokol.write(e.toString());
			}
		}
		return erg;
	}
}
