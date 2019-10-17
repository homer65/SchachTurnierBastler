package org.myoggradio.stb.impl;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import javax.swing.JFileChooser;
import org.myoggradio.stb.*;
public class FileTurnierLoader implements TurnierLoader
{
	@Override
	public Turnier load() 
	{
		Turnier erg = null;
		JFileChooser fc = new JFileChooser();
		int rc = fc.showOpenDialog(null);
		if (rc == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				File ein = fc.getSelectedFile();
				FileInputStream fis = new FileInputStream(ein);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Object obj = ois.readObject();
				ois.close();
				if (obj != null)
				{
					if (obj instanceof Turnier)
					{
						erg = (Turnier) obj;
						Parameter.spieler = erg.getSpieler();
					}
					else
					{
						Protokol.write("FileTurnierLoader:load:kein Turnier Object");
					}
				}
				else
				{
					Protokol.write("FileTurnierLoader:load:Null Object gelesen");
				}

			}
			catch (Exception e)
			{
				Protokol.write("FileTurnierLoader:load:Exception:");
				Protokol.write(e.toString());
			}
		}
		return erg;
	}
}
