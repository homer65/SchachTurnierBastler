package org.myoggradio.stb.impl;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import org.myoggradio.stb.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
public class SimpleTurnierMenu extends JFrame implements ActionListener, TurnierMenu, ListSelectionListener
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
	private JMenuItem m14 = new JMenuItem("laden autodetect");
	private JMenuItem m15 = new JMenuItem("aktive Runde stornieren");
	private JMenuItem m16 = new JMenuItem("Turnier um eine Runde erweitern");
	private JMenuItem m17 = new JMenuItem("Print Runde");
	private JMenuItem m18 = new JMenuItem("Runde manuell vorgeben");
	private JMenuItem m21 = new JMenuItem("anzeigen");
	private JMenuItem m22 = new JMenuItem("speichern");
	private JMenuItem m23 = new JMenuItem("loeschen");
	private JMenuItem m24 = new JMenuItem("ergaenzen");
	private JMenuItem m25 = new JMenuItem("aendern");
	private JMenuItem m31 = new JMenuItem("Version");
	private JMenuItem m32 = new JMenuItem("Autosave Directory");
	private JPanel cpan = new JPanel();
	private JPanel rpan = new JPanel();
	private JLabel lab1 = new JLabel("Runde");
	private JButton butt1 = new JButton("-");
	private JLabel lab2 = new JLabel("0");
	private JButton butt2 = new JButton("+");
	private JPanel tpan = new JPanel();
	private JPanel fpan = new JPanel();
	private JLabel labf = new JLabel("Freilos ");
	private JTable table = null;
	public SimpleTurnierMenu()
	{
		super("SchachTurnierBastler SimpleTurnierMenu");
		this.setName("SchachTurnierBastler SimpleTurnierMenu");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Runtime runtime = Runtime.getRuntime();
		if (Parameter.shutdown != null) runtime.removeShutdownHook(Parameter.shutdown);
		runtime.addShutdownHook(new Shutdown());
		m1.add(m11);
		m1.add(m12);
		m1.add(m13);
		m1.add(m14);
		m1.add(m15);
		m1.add(m16);
		m1.add(m17);
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
		m13.addActionListener(this);
		m14.addActionListener(this);
		m15.addActionListener(this);
		m16.addActionListener(this);
		m17.addActionListener(this);
		m18.addActionListener(this);
		m21.addActionListener(this);
		m22.addActionListener(this);
		m23.addActionListener(this);
		m24.addActionListener(this);
		m25.addActionListener(this);
		m31.addActionListener(this);
		m32.addActionListener(this);
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
		Runde runde = Parameter.turnier.getRunde(dargestellteRunde);
		int nh = runde.getMaxPartien();
		tpan = new JPanel();
		tpan.setLayout(new FlowLayout());
		String[] columnNames = new String[4];
		columnNames[0] = "Tisch Nummer";
		columnNames[1] = "Weiss";
		columnNames[2] = "Schwarz";
		columnNames[3] = "Ergebnis";
		String[][] rowData = new String[nh][4];
		ArrayList<Partie> partien = new ArrayList<Partie>();
		for (int i=0;i<nh;i++)
		{
			Partie partie = runde.getPartie(i);
			partien.add(partie);
			Spieler weiss = partie.getWeiss();
			Spieler schwarz = partie.getSchwarz();
			String sweiss = weiss.getVorname() + " " + weiss.getName() + " " + weiss.getDWZ() + " (" + getPunkte(weiss,dargestellteRunde) + ")"; 
			String sschwarz = schwarz.getVorname() + " " + schwarz.getName() + " " + schwarz.getDWZ() + " (" + getPunkte(schwarz,dargestellteRunde) + ")"; 
			int ergebnis = partie.getErgebnis();
			rowData[i][0] = (i+1) + "";
			rowData[i][1] = sweiss;
			rowData[i][2] = sschwarz;
			rowData[i][3] = ErgebnisDarsteller.get(ergebnis);
		}
		table = new JTable(rowData,columnNames);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(this);
		TableColumn column = table.getColumn(table.getColumnName(3));
		column.setCellRenderer(new RowRendererPartie(partien));
		ColumnResizer.resize(table);
		JScrollPane scrpane = new JScrollPane(table);
		scrpane.setPreferredSize(new Dimension(Parameter.scrwidth,Parameter.scrheight));
		tpan.add(scrpane);
	}
	public void buildfpan()
	{
		fpan = new JPanel();
		Runde runde = Parameter.turnier.getRunde(dargestellteRunde);
		if (runde == null) Protokol.write("SimpleTurnierMenu:buildfpan:" + dargestellteRunde);
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
		rpan.setLayout(new BorderLayout());
		JPanel rpan1 = new JPanel();
		JPanel rpan2 = new JPanel();
		rpan1.setLayout(new GridLayout(1,4));
		rpan1.add(lab1);
		rpan1.add(butt1);
		lab2 = new JLabel((dargestellteRunde + 1) + "");
		rpan1.add(lab2);
		rpan1.add(butt2);
		rpan.add(rpan1,BorderLayout.WEST);
		rpan.add(rpan2,BorderLayout.CENTER);
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
		if (source == m14) // Turnier laden autodetect
		{
			AutoLoader loader = new AutoLoader();
			boolean ok = loader.load();
			if (ok) dispose();
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
			Parameter.turnier.setMaxRundenPlus();
		}
		if (source == m17) // Print Runde
		{
			Runde runde = Parameter.turnier.getRunde(dargestellteRunde);
			PrintToHtml print = Factory.getPrintToHtml();
			print.print(runde,dargestellteRunde);
		}
		if (source == m18) // Runde manuell vorgeben
		{
			Runde runde = Parameter.turnier.getRunde(dargestellteRunde);
			RundeManuellMenu rmm = Factory.getRundeManuellMenu();
			rmm.setRunde(runde);
			rmm.anzeigen();
			init();
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
		if (source == m23) // Spieler loeschen
		{
			SpielerStornierenDialog ssd = Factory.getSpielerStornierenDialog();
			ssd.setSpieler(Parameter.turnier.getSpieler());
			ssd.anzeigen();
			TurnierMenu tm = Factory.getTurnierMenu();
			tm.anzeigen();
			dispose();
		}
		if (source == m24) // Spieler ergaenzen
		{
			SpielerErgaenzenDialog sed = Factory.getSpielerErgaenzenDialog();
			sed.anzeigen();
			TurnierMenu tm = Factory.getTurnierMenu();
			tm.anzeigen();
			dispose();
		}
		if (source == m25) // Spieler aendern
		{
			SpielerChangeDialog scd = Factory.getSpielerChangeDialog();
			scd.setSpieler(Parameter.turnier.getSpieler());
			scd.anzeigen();
			TurnierMenu tm = Factory.getTurnierMenu();
			tm.anzeigen();
			dispose();
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
		if (source == butt1) // Runde zurueck
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
	}
	@Override
	public void valueChanged(ListSelectionEvent lse)
	{
		boolean isAdjusting = lse.getValueIsAdjusting();
		if (!isAdjusting)
		{
			int x = table.getSelectedRow();
			Runde runde = Parameter.turnier.getRunde(dargestellteRunde);
			Partie partie = runde.getPartie(x);
			ErgebnisDialog ed = Factory.getErgebnisDialog();
			ed.setPartie(partie);
			ed.anzeigen();
			init();
		}
	}
	private double getPunkte(Spieler spieler,int runde)
	{
		double erg = 0.0;
		if (runde > 0)
		{
			TurnierManager manager = Factory.getTurnierManager();
			ArrayList<Auswertung> auswertungen = manager.getAuswertung(runde - 1);
			for (int i=0;i<auswertungen.size();i++)
			{
				Spieler test = auswertungen.get(i).getSpieler();
				if (test.istGleich(spieler))
				{
					erg += auswertungen.get(i).getPunkte();
				}
			}
		}
		return erg;
	}
}
