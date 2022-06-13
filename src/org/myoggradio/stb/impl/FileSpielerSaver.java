package org.myoggradio.stb.impl;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import org.myoggradio.stb.*;
public class FileSpielerSaver implements SpielerSaver
{
	@Override
	public void save(ArrayList<Spieler> spieler) 
	{
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("."));
		int rc = fc.showSaveDialog(null);
		if (rc == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				File aus = fc.getSelectedFile();
				XMLSpielerSaver xml = new XMLSpielerSaver();
				xml.save(spieler,aus);
			}
			catch (Exception e)
			{
				Protokol.write("FileSpielerSaver:save:Exception:");
				Protokol.write(e.toString());
			}
		}
		return;
	}
}
