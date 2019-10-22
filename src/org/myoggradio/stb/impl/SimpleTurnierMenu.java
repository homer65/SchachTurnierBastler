package org.myoggradio.stb.impl;
import javax.swing.*;
import org.myoggradio.stb.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
public class SimpleTurnierMenu extends JFrame implements ActionListener, TurnierMenu
{
	private static final long serialVersionUID = 1;
	private int dargestellteRunde = 0;
	private JMenuBar menu = new JMenuBar();
	private JMenu m1 = new JMenu("Turnier");
	private JMenu m2 = new JMenu("Spieler");
	private JMenu m3 = new JMenu("Info");
	private JMenuItem m11 = new JMenuItem("Naechste Runde");
	private JMenuItem m12 = new JMenuItem("Auswertung");
	private JMenuItem m13 = new JMenuItem("speichern");
	private JMenuItem m14 = new JMenuItem("laden");
	private JMenuItem m15 = new JMenuItem("aktive Runde stornieren");
	private JMenuItem m16 = new JMenuItem("Turnier um eine Runde erweitern");
	private JMenuItem m21 = new JMenuItem("anzeigen");
	private JMenuItem m22 = new JMenuItem("speichern");
	private JMenuItem m23 = new JMenuItem("loeschen");
	private JMenuItem m31 = new JMenuItem("Version");
	private JPanel cpan = new JPanel();
	private JPanel rpan = new JPanel();
	private JLabel lab1 = new JLabel("Runde");
	private JButton butt1 = new JButton("-");
	private JLabel lab2 = new JLabel("0");
	private JButton butt2 = new JButton("+");
	private JPanel tpan = new JPanel();
	private JLabel[] labz = null;
	private JLabel[] labtw = null;
	private JLabel[] labts = null;
	private JButton[] butte = null;
	private JPanel fpan = new JPanel();
	private JLabel labf = new JLabel("Freilos ");
	public SimpleTurnierMenu()
	{
		this.setName("SchachTurnierBastler");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Runtime.getRuntime().addShutdownHook(new Shutdown());
		m1.add(m11);
		m1.add(m12);
		m1.add(m13);
		m1.add(m14);
		m1.add(m15);
		m1.add(m16);
		m2.add(m21);
		m2.add(m22);
		m2.add(m23);
		m3.add(m31);
		menu.add(m1);
		menu.add(m2);
		menu.add(m3);
		this.setJMenuBar(menu);
		m11.addActionListener(this);
		m12.addActionListener(this);
		m13.addActionListener(this);
		m14.addActionListener(this);
		m15.addActionListener(this);
		m16.addActionListener(this);
		m21.addActionListener(this);
		m22.addActionListener(this);
		m23.addActionListener(this);
		m31.addActionListener(this);
		butt1.addActionListener(this);
		butt2.addActionListener(this);
		init();
	}
	public void init()
	{
		buildrpan();
		buildfpan();
		buildtpan();
		buildcpan();
		setContentPane(cpan);
		pack();
		setVisible(true);
	}
	public void buildcpan()
	{
		cpan = new JPanel();
		cpan.setLayout(new BorderLayout());
		cpan.add(rpan,BorderLayout.NORTH);
		cpan.add(fpan,BorderLayout.CENTER);
		JScrollPane scrpan=new JScrollPane(tpan);
		scrpan.setPreferredSize(new Dimension(Parameter.scrwidth,Parameter.scrheight));
		cpan.add(scrpan,BorderLayout.SOUTH);
	}
	public void buildtpan()
	{
		tpan = new JPanel();
		Runde runde = Parameter.turnier.getRunde(dargestellteRunde);
		//int nh = Parameter.turnier.getSpieler().size() / 2;
		int nh = runde.getMaxPartien();
		tpan.setLayout(new GridLayout(nh,4));
		labz = new JLabel[nh];
		labtw = new JLabel[nh];
		labts = new JLabel[nh];
		butte = new JButton[nh];
		for (int i=0;i<nh;i++)
		{
			Partie partie = runde.getPartie(i);
			Spieler weiss = partie.getWeiss();
			Spieler schwarz = partie.getSchwarz();
			String sweiss = weiss.getVorname() + " " + weiss.getName() + " " + weiss.getDWZ(); 
			String sschwarz = schwarz.getVorname() + " " + schwarz.getName() + " " + schwarz.getDWZ(); 
			int ergebnis = partie.getErgebnis();
			labz[i] = new JLabel("" + (i+1));
			labtw[i] = new JLabel(sweiss);
			labts[i] = new JLabel(sschwarz);
			butte[i] = new JButton(ErgebnisDarsteller.get(ergebnis));
			tpan.add(labz[i]);
			tpan.add(labtw[i]);
			tpan.add(labts[i]);
			tpan.add(butte[i]);
			butte[i].addActionListener(this);
		}
	}
	public void buildfpan()
	{
		fpan = new JPanel();
		Runde runde = Parameter.turnier.getRunde(dargestellteRunde);
		ArrayList<Spieler> freilos = runde.getFreilos();
		if (freilos.size()> 0)
		{
			fpan.setLayout(new GridLayout(freilos.size(),1));
			for (int i=0;i<freilos.size();i++)
			{
				String sfreilos = freilos.get(i).getVorname() + " " + freilos.get(i).getName() + " " + freilos.get(i).getDWZ();
				labf = new JLabel("Freilos " + sfreilos);
				fpan.add(labf);
			}
		}
	}
	public void buildrpan()
	{
		rpan = new JPanel();
		rpan.setLayout(new GridLayout(1,4));
		rpan.add(lab1);
		rpan.add(butt1);
		lab2 = new JLabel((dargestellteRunde + 1) + "");
		rpan.add(lab2);
		rpan.add(butt2);
		rpan.setPreferredSize(new Dimension(Parameter.scrwidth,25));
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
		if (source == m11) // Nächste Runde starten
		{
			Runde aktiveRunde = Parameter.turnier.getAktiveRunde();
			if (aktiveRunde.alleErgebnisEingetragen())
			{
				TurnierManager manager = Factory.getTurnierManager();
				Runde neueRunde = manager.starteNaechsteRunde(Parameter.turnier);
				if (neueRunde != null)
				{
					Parameter.turnier.setNextRunde(neueRunde);
					dargestellteRunde = Parameter.turnier.getNummerAktiveRunde();
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Es sind noch nicht alle Ergebnisse eingetragen","Fehler",JOptionPane.INFORMATION_MESSAGE);
			}
			init();
		}
		if (source == m12) // Auswertung
		{
			AuswertungDialog ad = Factory.getAuswertungDialog();
			ad.setRunde(dargestellteRunde);
			ad.anzeigen();
		}
		if (source == m13) // Turnier speichern
		{
			TurnierSaver saver = Factory.getTurnierSaver();
			saver.save(Parameter.turnier);
		}
		if (source == m14) // Turnier laden
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
		if (source == m15) // dargestellte Runde stornieren
		{
			int ok = JOptionPane.showConfirmDialog(null, "aktive Runde wirklich stornieren?");
			if (ok == JOptionPane.YES_OPTION)
			{
				boolean storniert = Parameter.turnier.storniereAktiveRunde();
				if (storniert)
				{
					TurnierMenu tm = Factory.getTurnierMenu();
					tm.anzeigen();
					dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Runde wurde nicht storniert","Fehler",JOptionPane.INFORMATION_MESSAGE);	
				}
			}
		}
		if (source == m16) // Turnier um eine Runde erweitern
		{
			Parameter.anzahlRunden++;
			Parameter.reichweite++;
			Parameter.turnier.setMaxRundenPlus();
		}
		if (source == m21) // Spieler anzeigen
		{
			SpielerAnzeigenDialog nsm = Factory.getSpielerAnzeigenDialog();
			nsm.setSpieler(Parameter.turnier.getSpieler());
			nsm.anzeigen();
		}
		if (source == m22) // speichern Spieler
		{
			SpielerSaver saver = Factory.getSpielerSaver();
			saver.save(Parameter.turnier.getSpieler());
		}
		if (source == m23) // Spieler löschen
		{
			SpielerStornierenDialog ssd = Factory.getSpielerStornierenDialog();
			ssd.setSpieler(Parameter.turnier.getSpieler());
			ssd.anzeigen();
			TurnierMenu tm = Factory.getTurnierMenu();
			tm.anzeigen();
			dispose();
		}
		if (source == m31) // Version anzeigen
		{
			JOptionPane.showMessageDialog(null,Parameter.version,"Version",JOptionPane.INFORMATION_MESSAGE);
		}
		if (source == butt1) // Runde zurück
		{
			if (dargestellteRunde > 0) 
			{
				dargestellteRunde--;
			}
			else
			{
				JOptionPane.showMessageDialog(null,"dargestellte Runde ist bereits die kleinste","Fehler",JOptionPane.INFORMATION_MESSAGE);
			}
			init();
		}
		if (source == butt2) // Runde vor
		{
			int aktiveRunde = Parameter.turnier.getNummerAktiveRunde();
			if (dargestellteRunde < aktiveRunde)
			{
				dargestellteRunde++;
			}
			else
			{
				JOptionPane.showMessageDialog(null,"dargestellte Runde ist bereits die groesste","Fehler",JOptionPane.INFORMATION_MESSAGE);
			}
			init();
		}
		for (int i=0;i<butte.length;i++)
		{
			if (source == butte[i]) // Ergebnis eintragen
			{
				Runde runde = Parameter.turnier.getRunde(dargestellteRunde);
				Partie partie = runde.getPartie(i);
				ErgebnisDialog ed = Factory.getErgebnisDialog();
				ed.setPartie(partie);
				ed.anzeigen();
				init();
			}
		}
	}
}
