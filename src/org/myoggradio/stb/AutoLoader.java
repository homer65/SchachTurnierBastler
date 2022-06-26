package org.myoggradio.stb;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.myoggradio.stb.impl.XMLSpielerLoader;
import org.myoggradio.stb.impl.XMLTurnierLoader;
import org.myoggradio.stbjgj.JGJFactory;
import org.myoggradio.stbjgj.JGJParameter;
import org.myoggradio.stbjgj.JGJTurnier;
import org.myoggradio.stbjgj.JGJTurnierMenu;
import org.myoggradio.stbjgj.impl.XMLJGJTurnierLoader;
import org.myoggradio.stbko.KOFactory;
import org.myoggradio.stbko.KOParameter;
import org.myoggradio.stbko.KOTurnier;
import org.myoggradio.stbko.KOTurnierMenu2;
import org.myoggradio.stbko.impl.XMLKOTurnierLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

public class AutoLoader 
{
	public boolean load()
	{
		boolean erg = true;
		try
		{
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File("."));
			int rc = fc.showOpenDialog(null);
			if (rc == JFileChooser.APPROVE_OPTION)
			{
				File file = fc.getSelectedFile();
				erg = load(file);
			}
			else
			{
				erg = false;
				Protokol.write("AutoLoader:load:Keine Datei ausgewaehlt");
			}
		}
		catch (Exception e)
		{
			erg = false;
			Protokol.write("AutoLoader:load:Exception:");
			Protokol.write(e.toString());
		}
		return erg;
	}
	public boolean load(File file)
	{
		Protokol.write("Auotloader:load:File:" + file.getAbsolutePath());
		boolean erg = true;
		try
		{
			InputStream fin = new FileInputStream(file);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new InputSource(fin));
			Node turnier = doc.getFirstChild();
			Element elemturnier = (Element) turnier;
			String tagname = elemturnier.getTagName();
			if (tagname.equals("spielerlist"))
			{
				XMLSpielerLoader xml = new XMLSpielerLoader();
				ArrayList<Spieler> test = xml.load(file);
				if (test.size() > 0) Parameter.spieler = test;
				MainMenu mm = Factory.getMainMenu();
				mm.anzeigen();
				erg = true;
			}
			else if (tagname.equals("turnier"))
			{
				XMLTurnierLoader xml = new XMLTurnierLoader();
				Turnier test = xml.load(file);
				if (test != null)
				{
					Parameter.turnier = test;
					TurnierMenu tm = Factory.getTurnierMenu();
					tm.anzeigen();
					erg = true;
				}
			}
			else if (tagname.equals("koturnier"))
			{
				XMLKOTurnierLoader xml = new XMLKOTurnierLoader();
				KOTurnier test = xml.load(file);
				if (test != null)
				{
					KOParameter.turnier = test;
					KOTurnierMenu2 tm = KOFactory.getKOTurnierMenu2();
					tm.anzeigen();
					erg = true;
				}
			}
			else if (tagname.equals("jgjturnier"))
			{
				XMLJGJTurnierLoader xml = new XMLJGJTurnierLoader();
				JGJTurnier test = xml.load(file);
				if (test != null)
				{
					JGJParameter.turnier = test;
					JGJTurnierMenu tm = JGJFactory.getJGJTurnierMenu();
					tm.anzeigen();
					erg = true;
				}
			}
			else
			{
				erg = false;
				Protokol.write("AutoLoader:load:Kann Datei nicht erkennen");
			}
		}
		catch (Exception e)
		{
			Protokol.write("Autoloader:load:Exception:");
			Protokol.write(e.toString());
		}
		return erg;
	}
}
