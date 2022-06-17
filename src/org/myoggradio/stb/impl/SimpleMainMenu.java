package org.myoggradio.stb.impl;
import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.myoggradio.stb.*;
import org.myoggradio.stb.img.Locator;
import org.myoggradio.stb.img.TuxPanel;
import org.myoggradio.stbjgj.JGJFactory;
import org.myoggradio.stbjgj.JGJParameter;
import org.myoggradio.stbjgj.JGJTurnier;
import org.myoggradio.stbjgj.JGJTurnierMenu;
import org.myoggradio.stbjgj.impl.XMLJGJTurnierLoader;
import org.myoggradio.stbko.*;
import org.myoggradio.stbko.impl.XMLKOTurnierLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
public class SimpleMainMenu extends JFrame implements ActionListener, MainMenu
{
	private static final long serialVersionUID = 1;
	private JMenuBar menu = new JMenuBar();
	private JMenu m1 = new JMenu("Turnier");
	private JMenu m2 = new JMenu("Spieler");
	private JMenu m3 = new JMenu("Info");
	private JMenuItem m11 = new JMenuItem("Start schweizer System");
	private JMenuItem m12 = new JMenuItem("Anzahl Runden schweizer System");
	//private JMenuItem m13 = new JMenuItem("laden schweizer System");
	//private JMenuItem m15 = new JMenuItem("laden KO System");
	private JMenuItem m14 = new JMenuItem("Start KO System");
	private JMenuItem m16 = new JMenuItem("Start JGJ System");
	//private JMenuItem m17 = new JMenuItem("laden JGJ System");
	private JMenuItem m18 = new JMenuItem("Laden autodetect");
	private JMenuItem m21 = new JMenuItem("laden");
	private JMenuItem m22 = new JMenuItem("speichern");
	private JMenuItem m23 = new JMenuItem("einzelnen Spieler hinzufuegen");
	private JMenuItem m24 = new JMenuItem("Spieler loeschen");
	private JMenuItem m25 = new JMenuItem("Spieler anzeigen");
	private JMenuItem m31 = new JMenuItem("Version");
	private JMenuItem m32 = new JMenuItem("Autosave Directory");
	public SimpleMainMenu()
	{
		this.setName("SchachTurnierBastler");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		m1.add(m11);
		m1.add(m14);
		m1.add(m12);
		//m1.add(m13);
		//m1.add(m15);
		m1.add(m16);
		//m1.add(m17);
		m1.add(m18);
		m2.add(m21);
		m2.add(m22);
		m2.add(m23);
		m2.add(m24);
		m2.add(m25);
		m3.add(m31);
		m3.add(m32);
		menu.add(m1);
		menu.add(m2);
		menu.add(m3);
		this.setJMenuBar(menu);
		m11.addActionListener(this);
		m12.addActionListener(this);
		//m13.addActionListener(this);
		m14.addActionListener(this);
		//m15.addActionListener(this);
		m16.addActionListener(this);
		//m17.addActionListener(this);
		m18.addActionListener(this);
		m21.addActionListener(this);
		m22.addActionListener(this);
		m23.addActionListener(this);
		m24.addActionListener(this);
		m25.addActionListener(this);
		m31.addActionListener(this);
		m32.addActionListener(this);
		Locator locator = new Locator();
		URL url = locator.getURL("tux.png");
		Image tux = Toolkit.getDefaultToolkit().getImage(url);
		TuxPanel tp = new TuxPanel(tux);
		setLayout(new FlowLayout());
		add(tp);
		
	}
	public void anzeigen()
	{
		JFrame rahmen = this;
		WindowListener wl = new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				//
				dispose();
			}
		};
		rahmen.addWindowListener(wl);
		rahmen.pack();
		rahmen.setVisible(true);
	}
	public void actionPerformed(ActionEvent ae)
	{
		Object source = ae.getSource();
		if (source == m11) // Start Turnier schweizer System
		{
			Parameter.turnier.setMaxRunden(Parameter.anzahlRunden);
			Parameter.turnier.setSpieler(Parameter.spieler);
			Parameter.turnier.start();
			TurnierMenu tm = Factory.getTurnierMenu();
			tm.anzeigen();
			dispose();
		}
		if (source == m14) // Start Turnier KO System
		{
			KOParameter.spieler = Parameter.spieler;
			KOTurnierMenu tm = KOFactory.getKOTurnierMenu();
			tm.anzeigen();
			dispose();
		}
		if (source == m12) // Anzahl Runden festlegen
		{
			AnzahlRundenDialog ard = Factory.getAnzahlRundenDialog();
			ard.anzeigen();
		}
		if (source == m18) // Turnier laden autodetect
		{
			try
			{
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(new File("."));
				int rc = fc.showOpenDialog(null);
				if (rc == JFileChooser.APPROVE_OPTION)
				{
					File file = fc.getSelectedFile();
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
							dispose();
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
							dispose();
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
							dispose();
						}
					}
					else
					{
						Protokol.write("SimpleMainMenu:actionPerformed:m18:Kann Datei nicht erkennen");
					}
				}
				else
				{
					Protokol.write("SimpleMainMenu:actionPerformed:m18:Keine Datei ausgewaehlt");
				}
			}
			catch (Exception e)
			{
				Protokol.write("SimpleMainMenu:actionPerformed:m18:Exception:");
				Protokol.write(e.toString());
			}
		}
		/*
		if (source == m13) // Turnier laden schweizer System
		{
			TurnierLoader loader = Factory.getTurnierLoader();
			Turnier test = loader.load();
			if (test != null)
			{
				Parameter.turnier = test;
				TurnierMenu tm = Factory.getTurnierMenu();
				tm.anzeigen();
				dispose();
			}
		}
		if (source == m15) // Turnier laden KO System
		{
			KOTurnierLoader loader = KOFactory.getKOTurnierLoader();
			KOTurnier test = loader.load();
			if (test != null)
			{
				KOParameter.turnier = test;
				KOTurnierMenu2 tm = KOFactory.getKOTurnierMenu2();
				tm.anzeigen();
				dispose();
			}
		}
		if (source == m17) // Turnier laden KO System
		{
			JGJTurnierLoader loader = JGJFactory.getJGJTurnierLoader();
			JGJTurnier test = loader.load();
			if (test != null)
			{
				JGJParameter.turnier = test;
				JGJTurnierMenu tm = JGJFactory.getJGJTurnierMenu();
				tm.anzeigen();
				dispose();
			}
		}
		*/
		if (source == m16) // Start Turnier JGJ System
		{
			JGJParameter.spieler = Parameter.spieler;
			JGJTurnier turnier = JGJFactory.getJGJTurnier();
			turnier.setSpieler(Parameter.spieler);
			turnier.start();
			JGJParameter.turnier = turnier;
			JGJTurnierMenu tm = JGJFactory.getJGJTurnierMenu();
			tm.anzeigen();
			dispose();
		}
		if (source == m21) // laden Spieler
		{
			SpielerLoader loader = Factory.getSpielerLoader();
			ArrayList<Spieler> test = loader.load();
			if (test.size() > 0) Parameter.spieler = test;
		}
		if (source == m22) // speichern Spieler
		{
			SpielerSaver saver = Factory.getSpielerSaver();
			saver.save(Parameter.spieler);
		}
		if (source == m23) // einzelnen Spieler hinzufuegen
		{
			NeuerSpielerDialog nsm = Factory.getNeuerSpielerDialog();
			Spieler s = Factory.getSpieler();
			nsm.setSpieler(s);
			nsm.anzeigen();
			if (s.getName() != null)
			{
				Parameter.spieler.add(s);
			}
		}
		if (source == m24) // Spieler loeschen
		{
			SpielerLoeschenDialog nsm = Factory.getSpielerLoeschenDialog();
			nsm.setSpieler(Parameter.spieler);
			nsm.anzeigen();
		}
		if (source == m25) // Spieler anzeigen
		{
			SpielerAnzeigenDialog nsm = Factory.getSpielerAnzeigenDialog();
			nsm.setSpieler(Parameter.spieler);
			nsm.anzeigen();
		}
		if (source == m31) // Version anzeigen
		{
			JOptionPane.showMessageDialog(null,Parameter.version,"Version",JOptionPane.INFORMATION_MESSAGE);
		}
		if (source == m32) // Autosave Directory
		{
			File aus = new File(".");
			JOptionPane.showMessageDialog(null,aus.getAbsolutePath(),"Autosave Directory",JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
