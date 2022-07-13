package org.myoggradio.stb.impl;
import javax.swing.*;
import org.myoggradio.stb.*;
import org.myoggradio.stb.img.Locator;
import org.myoggradio.stb.img.TuxPanel;
import org.myoggradio.stbjgj.JGJFactory;
import org.myoggradio.stbjgj.JGJParameter;
import org.myoggradio.stbjgj.JGJTurnier;
import org.myoggradio.stbjgj.JGJTurnierMenu;
import org.myoggradio.stbko.*;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.prefs.Preferences;
public class SimpleMainMenu extends JFrame implements ActionListener, MainMenu
{
	private static final long serialVersionUID = 1;
	private JMenuBar menu = new JMenuBar();
	private JMenu m1 = new JMenu("Turnier");
	private JMenu m2 = new JMenu("Spieler");
	private JMenu m3 = new JMenu("Info");
	private JMenu m4 = new JMenu("Einstellungen");
	private JMenuItem m11 = new JMenuItem("Start schweizer System");
	//private JMenuItem m12 = new JMenuItem("Anzahl Runden schweizer System");
	//private JMenuItem m13 = new JMenuItem("laden schweizer System");
	//private JMenuItem m15 = new JMenuItem("laden KO System");
	private JMenuItem m14 = new JMenuItem("Start KO System");
	private JMenuItem m16 = new JMenuItem("Start JGJ System");
	//private JMenuItem m17 = new JMenuItem("laden JGJ System");
	private JMenuItem m18 = new JMenuItem("Laden autodetect");
	private JMenuItem m19 = new JMenuItem("Laden letzte Shutdown Sicherung");
	private JMenuItem m21 = new JMenuItem("laden");
	private JMenuItem m22 = new JMenuItem("speichern");
	private JMenuItem m23 = new JMenuItem("einzelnen Spieler hinzufuegen");
	private JMenuItem m24 = new JMenuItem("Spieler loeschen");
	private JMenuItem m25 = new JMenuItem("Spieler anzeigen");
	private JMenuItem m26 = new JMenuItem("Spieler aendern");
	private JMenuItem m31 = new JMenuItem("Version");
	private JMenuItem m32 = new JMenuItem("Autosave Directory");
	private JMenuItem m41 = new JMenuItem("Anzahl Runden schweizer System");
	private JMenuItem m42 = new JMenuItem("Maximale Anzahl Iterationen");
	private JMenuItem m43 = new JMenuItem("Anzahl Iterationen bis zur Meldung");
	private JMenuItem m44 = new JMenuItem("Auto Save Directory");
	private JMenuItem m45 = new JMenuItem("Malus Farbdifferenz2");
	private JMenuItem m46 = new JMenuItem("Malus gleich gut");
	public SimpleMainMenu()
	{
		this.setName("SchachTurnierBastler");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		m1.add(m11);
		m1.add(m14);
		//m1.add(m12);
		//m1.add(m13);
		//m1.add(m15);
		m1.add(m16);
		//m1.add(m17);
		m1.add(m18);
		m1.add(m19);
		m2.add(m21);
		m2.add(m22);
		m2.add(m23);
		m2.add(m24);
		m2.add(m25);
		m2.add(m26);
		m3.add(m31);
		m3.add(m32);
		m4.add(m41);
		m4.add(m42);
		m4.add(m43);
		m4.add(m44);
		m4.add(m45);
		m4.add(m46);
		menu.add(m1);
		menu.add(m2);
		menu.add(m3);
		menu.add(m4);
		this.setJMenuBar(menu);
		m11.addActionListener(this);
		//m12.addActionListener(this);
		//m13.addActionListener(this);
		m14.addActionListener(this);
		//m15.addActionListener(this);
		m16.addActionListener(this);
		//m17.addActionListener(this);
		m18.addActionListener(this);
		m19.addActionListener(this);
		m21.addActionListener(this);
		m22.addActionListener(this);
		m23.addActionListener(this);
		m24.addActionListener(this);
		m25.addActionListener(this);
		m26.addActionListener(this);
		m31.addActionListener(this);
		m32.addActionListener(this);
		m41.addActionListener(this);
		m42.addActionListener(this);
		m43.addActionListener(this);
		m44.addActionListener(this);
		m45.addActionListener(this);
		m46.addActionListener(this);
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
		//if (source == m12) // Anzahl Runden festlegen
		//{
		//	AnzahlRundenDialog ard = Factory.getAnzahlRundenDialog();
		//	ard.anzeigen();
		//}
		if (source == m18) // Turnier laden autodetect
		{
			AutoLoader loader = new AutoLoader();
			boolean ok = loader.load();
			if (ok) dispose();
		}
		if (source == m19)
		{
			if (Parameter.autoSaveDirectory != null)
			{
				File test = new File(Parameter.autoSaveDirectory);
				if (!test.isDirectory())
				{
					Parameter.autoSaveDirectory = ".";
				}
			}
			else 
			{
				Parameter.autoSaveDirectory = ".";
			}
			File file = new File(Parameter.autoSaveDirectory + File.separator + "SchachTurnierBastler-Shutdown-AutoSave.stb");
			AutoLoader loader = new AutoLoader();
			loader.load(file);
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
		if (source == m26) // Spieler aendern
		{
			SpielerAendernDialog sad = Factory.getSpielerAendernDialog();
			sad.setSpieler(Parameter.spieler);
			sad.anzeigen();
		}
		if (source == m31) // Version anzeigen
		{
			JOptionPane.showMessageDialog(null,Parameter.version,"Version",JOptionPane.INFORMATION_MESSAGE);
		}
		if (source == m32) // Autosave Directory
		{
			File aus = new File(Parameter.autoSaveDirectory);
			JOptionPane.showMessageDialog(null,aus.getAbsolutePath(),"Autosave Directory",JOptionPane.INFORMATION_MESSAGE);
		}
		if (source == m41)
		{
			Preferences prefs = Preferences.userRoot();
			String anzahlRunden = prefs.get("SchachTurnierBastler_anzahlRunden","" + Parameter.anzahlRunden);
			anzahlRunden = JOptionPane.showInputDialog("Anzahl Runden schweizer System",anzahlRunden);
			if (anzahlRunden != null)
			{
				try
				{
					int temp = Integer.parseInt(anzahlRunden);
					if (temp > 2 && temp < 11)
					{
						Parameter.anzahlRunden = Integer.parseInt(anzahlRunden);
						prefs.put("SchachTurnierBastler_anzahlRunden",anzahlRunden);
					}
					else
					{
						Protokol.write("SimpleMainMenu:actionPerformed:m41:");
						Protokol.write("Einstellung ausserhalb des Normalen");
					}
				}
				catch (Exception e)
				{
					Protokol.write("SimpleMainMenu:actionPerformed:m41:Exception:");
					Protokol.write(e.toString());
				}
			}
		}
		if (source == m42)
		{
			Preferences prefs = Preferences.userRoot();
			String maxiter = prefs.get("SchachTurnierBastler_maxiter","" + Parameter.maxiter);
			maxiter = JOptionPane.showInputDialog("Maximale Anzahl Iterartionen",maxiter);
			if (maxiter != null)
			{
				try
				{
					int temp = Integer.parseInt(maxiter);
					if (temp > 499000 && temp < 50000001)
					{
						Parameter.maxiter = Integer.parseInt(maxiter);
						prefs.put("SchachTurnierBastler_maxiter",maxiter);
					}
					else
					{
						Protokol.write("SimpleMainMenu:actionPerformed:m42:");
						Protokol.write("Einstellung ausserhalb des Normalen");
					}
				}
				catch (Exception e)
				{
					Protokol.write("SimpleMainMenu:actionPerformed:m42:Exception:");
					Protokol.write(e.toString());
				}
			}	
		}
		if (source == m43)
		{
			Preferences prefs = Preferences.userRoot();
			String itermsg = prefs.get("SchachTurnierBastler_itermsg","" + Parameter.itermsg);
			itermsg = JOptionPane.showInputDialog("Anzahl Iterationen bis zur Meldung",itermsg);
			if (itermsg != null)
			{
				try
				{
					int temp = Integer.parseInt(itermsg);
					if (temp > 4999 && temp < 1000001)
					{
						Parameter.itermsg = Integer.parseInt(itermsg);
						prefs.put("SchachTurnierBastler_itermsg",itermsg);
					}
					else
					{
						Protokol.write("SimpleMainMenu:actionPerformed:m43:");
						Protokol.write("Einstellung ausserhalb des Normalen");	
					}
				}
				catch (Exception e)
				{
					Protokol.write("SimpleMainMenu:actionPerformed:m43:Exception:");
					Protokol.write(e.toString());
				}
			}
		}
		if (source == m44)
		{
			Preferences prefs = Preferences.userRoot();
			String autoSaveDirectory = prefs.get("SchachTurnierBastler_autoSaveDirectory","" + Parameter.autoSaveDirectory);
			if (autoSaveDirectory == null)
			{
				autoSaveDirectory = ".";
			}
			File directory = new File(autoSaveDirectory);
			JFileChooser chooser = new JFileChooser();
			chooser.setCurrentDirectory(directory);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int ok = chooser.showOpenDialog(null);
			if (ok == JFileChooser.APPROVE_OPTION)
			{
				File selectedDirectory = chooser.getSelectedFile();
				if (selectedDirectory != null)
				{
					if (selectedDirectory.isDirectory())
					{
						Parameter.autoSaveDirectory = selectedDirectory.getAbsolutePath();
						prefs.put("SchachTurnierBastler_autoSaveDirectory",Parameter.autoSaveDirectory);
						Protokol.write("SimpleMainMenu:actionPerformed:m44:");
						Protokol.write(Parameter.autoSaveDirectory);
					}
					else
					{
						Protokol.write("SimpleMainMenu:actionPerformed:m44:Kein Directory:");
						Protokol.write(selectedDirectory.getAbsolutePath());
					}
				}
				else
				{
					Protokol.write("SimpleMainMenu:actionPerformed:m44:Null Directory:");
				}
			}
		}
		if (source == m45)
		{
			Preferences prefs = Preferences.userRoot();
			String malusfarbdifferenz2 = prefs.get("SchachTurnierBastler_malusfarbdifferenz2","" + Parameter.malusFarbdifferenz2);
			malusfarbdifferenz2 = JOptionPane.showInputDialog("Malus Farbdifferenz2",malusfarbdifferenz2);
			if (malusfarbdifferenz2 != null)
			{
				try
				{
					int temp = Integer.parseInt(malusfarbdifferenz2);
					if (temp > 0 && temp < 51)
					{
						Parameter.malusFarbdifferenz2 = Integer.parseInt(malusfarbdifferenz2);
						prefs.put("SchachTurnierBastler_malusfarbdifferenz2",malusfarbdifferenz2);
					}
					else
					{
						Protokol.write("SimpleMainMenu:actionPerformed:m45:");
						Protokol.write("Einstellung ausserhalb des Normalen");	
					}
				}
				catch (Exception e)
				{
					Protokol.write("SimpleMainMenu:actionPerformed:m45:Exception:");
					Protokol.write(e.toString());
				}
			}
		}
		if (source == m46)
		{
			Preferences prefs = Preferences.userRoot();
			String malusgleichgut = prefs.get("SchachTurnierBastler_malusgleichgut","" + Parameter.malusGleichGut);
			malusgleichgut = JOptionPane.showInputDialog("Malus gleich gut",malusgleichgut);
			if (malusgleichgut != null)
			{
				try
				{
					int temp = Integer.parseInt(malusgleichgut);
					if (temp > 0 && temp < 51)
					{
						Parameter.malusGleichGut = Integer.parseInt(malusgleichgut);
						prefs.put("SchachTurnierBastler_malusgleichgut",malusgleichgut);
					}
					else
					{
						Protokol.write("SimpleMainMenu:actionPerformed:m46:");
						Protokol.write("Einstellung ausserhalb des Normalen");	
					}
				}
				catch (Exception e)
				{
					Protokol.write("SimpleMainMenu:actionPerformed:m46:Exception:");
					Protokol.write(e.toString());
				}
			}	
		}
	}
}
