package org.myoggradio.stb.impl;
import java.io.File;
import javax.swing.JFileChooser;
import org.myoggradio.stb.*;
public class FileTurnierLoader implements TurnierLoader
{
	@Override
	public Turnier load() 
	{
		Turnier erg = null;
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("."));
		int rc = fc.showOpenDialog(null);
		if (rc == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				File ein = fc.getSelectedFile();
				XMLTurnierLoader xml = new XMLTurnierLoader();
				erg = xml.load(ein);
				Parameter.spieler = erg.getSpieler();
			}
			catch (Exception e)
			{
				Protokol.write("FileTurnierLoader:load:Exception:");
				Protokol.write(e.toString());
			}
		}
		Parameter.turnier = erg;
		return erg;
	}
}
