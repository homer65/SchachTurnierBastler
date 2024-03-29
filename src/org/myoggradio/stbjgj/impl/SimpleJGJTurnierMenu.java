package org.myoggradio.stbjgj.impl;
import org.myoggradio.stbko.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import org.myoggradio.stb.*;
import org.myoggradio.stb.impl.FileSpielerSaver;
import org.myoggradio.stbjgj.JGJAuswertungDialog;
import org.myoggradio.stbjgj.JGJErgebnisDialog;
import org.myoggradio.stbjgj.JGJFactory;
import org.myoggradio.stbjgj.JGJParameter;
import org.myoggradio.stbjgj.JGJRunde;
import org.myoggradio.stbjgj.JGJSpielerErgaenzenDialog;
import org.myoggradio.stbjgj.JGJTurnier;
import org.myoggradio.stbjgj.JGJTurnierMenu;
import org.myoggradio.stbjgj.JGJTurnierSaver;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
public class SimpleJGJTurnierMenu extends JFrame implements ActionListener, JGJTurnierMenu, ListSelectionListener
{
	private static final long serialVersionUID = 1;
	private int dargestellteRunde = 0;
	private JMenuBar menu = new JMenuBar();
	private JMenu m1 = new JMenu("Turnier");
	private JMenu m2 = new JMenu("Spieler");
	private JMenu m3 = new JMenu("Info");
	private JMenuItem m12 = new JMenuItem("auswerten");
	private JMenuItem m13 = new JMenuItem("speichern");
	private JMenuItem m14 = new JMenuItem("laden autodetect");
	private JMenuItem m15 = new JMenuItem("print");
	private JMenuItem m16 = new JMenuItem("print Tabelle");
	private JMenuItem m21 = new JMenuItem("ergaenzen");
	private JMenuItem m22 = new JMenuItem("speichern");
	private JMenuItem m31 = new JMenuItem("Version");
	private JMenuItem m32 = new JMenuItem("Autosave Directory");
	private JPanel cpan = new JPanel();
	private JPanel rpan = new JPanel();
	private JLabel lab1 = new JLabel("Runde");
	private JButton butt1 = new JButton("-");
	private JLabel lab2 = new JLabel("0");
	private JButton butt2 = new JButton("+");
	private JPanel tpan = new JPanel();
	private JTable table = null;
	public SimpleJGJTurnierMenu()
	{
		super("SchachTurnierBastler SimpleJGJTurnierMenu");
		this.setName("SchachTurnierBastler");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		Runtime runtime = Runtime.getRuntime();
		if (Parameter.shutdown != null) runtime.removeShutdownHook(Parameter.shutdown);
		runtime.addShutdownHook(new JGJShutdown());
		m1.add(m12);
		m1.add(m13);
		m1.add(m14);
		m1.add(m15);
		m1.add(m16);
		m2.add(m21);
		m2.add(m22);
		m3.add(m31);
		m3.add(m32);
		menu.add(m1);
		menu.add(m2);
		menu.add(m3);
		this.setJMenuBar(menu);
		m12.addActionListener(this);
		m13.addActionListener(this);
		m14.addActionListener(this);
		m15.addActionListener(this);
		m16.addActionListener(this);
		m21.addActionListener(this);
		m22.addActionListener(this);
		m31.addActionListener(this);
		m32.addActionListener(this);
		butt1.addActionListener(this);
		butt2.addActionListener(this);
		init();
	}
	public void init()
	{
		buildrpan();
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
		JScrollPane scrpan=new JScrollPane(tpan);
		scrpan.setPreferredSize(new Dimension(KOParameter.scrwidth,KOParameter.scrheight));
		cpan.add(scrpan,BorderLayout.CENTER);
	}
	public void buildtpan()
	{
		JGJRunde runde = JGJParameter.turnier.getRunde(dargestellteRunde);
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
			String sweiss = weiss.getVorname() + " " + weiss.getName() + " " + weiss.getDWZ(); 
			String sschwarz = schwarz.getVorname() + " " + schwarz.getName() + " " + schwarz.getDWZ(); 
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
		scrpane.setPreferredSize(new Dimension(KOParameter.scrwidth,KOParameter.scrheight));
		tpan.add(scrpane);
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
		if (source == m12) // Auswertung
		{
			JGJAuswertungDialog ad = JGJFactory.getJGJAuswertungsDialog();
			ad.setRunde(dargestellteRunde);
			ad.anzeigen();
		}
		if (source == m13) // Turnier speichern
		{
			JGJTurnierSaver saver = JGJFactory.getJGJTurnierSaver();
			saver.save(JGJParameter.turnier);
		}
		if (source == m14) // Turnier laden
		{			
			AutoLoader loader = new AutoLoader();
			boolean ok = loader.load();
			if (ok) dispose();
		}
		if (source == m15) // Print Runde
		{
			JGJRunde runde = JGJParameter.turnier.getRunde(dargestellteRunde);
			PrintToHtml print = Factory.getPrintToHtml();
			print.print(runde,dargestellteRunde);
		}
		if (source == m16) // Print Tabelle
		{
			PrintToHtml print = Factory.getPrintToHtml();
			print.print(JGJParameter.turnier,dargestellteRunde);
		}
		if (source == m21) // Spieler ergaenzen
		{
			boolean ok = pruefeAufErsteRunde();
			if (ok)
			{
				JGJSpielerErgaenzenDialog jgjsem = JGJFactory.getJGJSpielerErgaenzenDialog();
				jgjsem.anzeigen();
				JGJTurnierMenu menu = JGJFactory.getJGJTurnierMenu();
				menu.anzeigen();
				dispose();
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Spieler ergaenzen geht nur in erster Runde","Fehler",JOptionPane.INFORMATION_MESSAGE);
			}
		}
		if (source == m22) // Spieler speichern
		{
			FileSpielerSaver fss = new FileSpielerSaver();
			fss.save(JGJParameter.turnier.getSpieler());
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
			int aktiveRunde = JGJParameter.turnier.getMaxrunden();
			if (dargestellteRunde < aktiveRunde - 1)
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
			JGJRunde runde = JGJParameter.turnier.getRunde(dargestellteRunde);
			Partie partie = runde.getPartie(x);
			JGJErgebnisDialog ed = JGJFactory.getJGJErgebnisDialog();
			ed.setPartie(partie);
			ed.anzeigen();
			init();
		}
	}
	private boolean pruefeAufErsteRunde()
	{
		boolean erg = true;
		JGJTurnier turnier = JGJParameter.turnier;
		for (int i = 1;i < turnier.getMaxrunden();i++)
		{
			JGJRunde runde = turnier.getRunde(i);
			for (int j=0;j<runde.getMaxPartien();j++)
			{
				Partie partie = runde.getPartie(j);
				int ergebnis = partie.getErgebnis();
				if (ergebnis !=  0) 
				{
					Spieler weiss = partie.getWeiss();
					Spieler schwarz = partie.getSchwarz();
					boolean istFreilosPartie = false;
					if (weiss.getId() < 0) istFreilosPartie = true;
					if (schwarz.getId() < 0) istFreilosPartie = true;
					if (!istFreilosPartie)
					{
						erg = false;
					}
				}
			}
		}
		return erg;
	}
}
