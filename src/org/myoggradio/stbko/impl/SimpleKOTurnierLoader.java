package org.myoggradio.stbko.impl;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
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
				FileInputStream fis = new FileInputStream(ein);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Object obj = ois.readObject();
				ois.close();
				if (obj != null)
				{
					if (obj instanceof KOTurnier)
					{
						erg = (KOTurnier) obj;
						Parameter.spieler = erg.getSpieler();
					}
					else
					{
						Protokol.write("SimpleKOTurnierLoader:load:kein KOTurnier Object");
					}
				}
				else
				{
					Protokol.write("SimpleKOTurnierLoader:load:Null Object gelesen");
				}

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
