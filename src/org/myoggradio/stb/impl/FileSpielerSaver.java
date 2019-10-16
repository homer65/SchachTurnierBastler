package org.myoggradio.stb.impl;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import org.myoggradio.stb.*;
public class FileSpielerSaver implements SpielerSaver
{
	@Override
	public void save(ArrayList<Spieler> spieler) 
	{
		JFileChooser fc = new JFileChooser();
		int rc = fc.showSaveDialog(null);
		if (rc == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				File aus = fc.getSelectedFile();
				Writer wrt = new FileWriter(aus);
				for (int i=0;i<spieler.size();i++)
				{
					Spieler s = spieler.get(i);
					String satz = s.toString();
					wrt.write(satz + "\n");
				}
				wrt.close();
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
