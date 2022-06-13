package org.myoggradio.stb.impl;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import org.myoggradio.stb.*;
public class FileSpielerLoader implements SpielerLoader
{
	@Override
	public ArrayList<Spieler> load() 
	{
		ArrayList<Spieler> erg = new ArrayList<Spieler>();
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("."));
		int rc = fc.showOpenDialog(null);
		if (rc == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				File ein = fc.getSelectedFile();
				XMLSpielerLoader xml = new XMLSpielerLoader();
				erg = xml.load(ein);
			}
			catch (Exception e)
			{
				Protokol.write("FileSpielerLoader:load:Exception:");
				Protokol.write(e.toString());
			}
		}
		return erg;
	}
}
