package org.myoggradio.stbjgj.impl;
import java.io.File;
import javax.swing.JFileChooser;
import org.myoggradio.stb.*;
import org.myoggradio.stbjgj.JGJTurnier;
import org.myoggradio.stbjgj.JGJTurnierSaver;
public class SimpleJGJTurnierSaver implements JGJTurnierSaver
{
	@Override
	public void save(JGJTurnier turnier) 
	{
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("."));
		int rc = fc.showSaveDialog(null);
		if (rc == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				File aus = fc.getSelectedFile();
				XMLJGJTurnierSaver xml = new XMLJGJTurnierSaver();
				xml.save(turnier,aus);
			}
			catch (Exception e)
			{
				Protokol.write("SimpleJGJTurnierSaver:save:Exception:");
				Protokol.write(e.toString());
			}
		}
		return;
	}
}
