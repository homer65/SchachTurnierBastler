package org.myoggradio.stbjgj.impl;
import java.io.File;
import javax.swing.JFileChooser;
import org.myoggradio.stb.*;
import org.myoggradio.stbjgj.JGJTurnier;
import org.myoggradio.stbjgj.JGJTurnierLoader;
public class SimpleJGJTurnierLoader implements JGJTurnierLoader
{
	@Override
	public JGJTurnier load() 
	{
		JGJTurnier erg = null;
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("."));
		int rc = fc.showOpenDialog(null);
		if (rc == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				File ein = fc.getSelectedFile();
				XMLJGJTurnierLoader xml = new XMLJGJTurnierLoader();
				erg = xml.load(ein);
				Parameter.spieler = erg.getSpieler();
			}
			catch (Exception e)
			{
				Protokol.write("SimpleJGJTurnierLoader:load:Exception:");
				Protokol.write(e.toString());
			}
		}
		return erg;
	}
}
