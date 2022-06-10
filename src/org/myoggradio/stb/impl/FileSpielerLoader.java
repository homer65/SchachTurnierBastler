package org.myoggradio.stb.impl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
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
				Reader rdr = new FileReader(ein);
				BufferedReader br = new BufferedReader(rdr);
				String satz = br.readLine();
				while (satz != null)
				{
					Spieler spieler = Factory.getSpieler();
					spieler.fromString(satz);
					if (spieler.getName() != null)
					{
						erg.add(spieler);
					}
					satz = br.readLine();
				}
				br.close();
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
