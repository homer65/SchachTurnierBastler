package org.myoggradio.stbko.impl;
import org.myoggradio.stbko.*;
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
public class SimpleKOTurnierMenu2 extends JFrame implements ActionListener, KOTurnierMenu2, ListSelectionListener
{
	private static final long serialVersionUID = 1;
	private int dargestellteRunde = 0;
	private JMenuBar menu = new JMenuBar();
	private JMenu m1 = new JMenu("Turnier");
	private JMenu m3 = new JMenu("Info");
	private JMenuItem m11 = new JMenuItem("Naechste Runde");
	private JMenuItem m13 = new JMenuItem("speichern");
	private JMenuItem m14 = new JMenuItem("laden");
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
	public SimpleKOTurnierMenu2()
	{
		this.setName("SchachTurnierBastler");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		m1.add(m11);
		m1.add(m13);
		m1.add(m14);
		m3.add(m31);
		m3.add(m32);
		menu.add(m1);
		menu.add(m3);
		this.setJMenuBar(menu);
		m11.addActionListener(this);
		m13.addActionListener(this);
		m14.addActionListener(this);
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
		scrpan.setPreferredSize(new Dimension(Parameter.scrwidth,Parameter.scrheight));
		cpan.add(scrpan,BorderLayout.CENTER);
	}
	public void buildtpan()
	{
		KORunde runde = KOParameter.turnier.getRunde(dargestellteRunde);
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
		if (source == m11) // Naechste Runde starten
		{
			KORunde aktiveRunde = KOParameter.turnier.getAktiveRunde();
			if (aktiveRunde.alleErgebnisEingetragen())
			{
				KOTurnierManager manager = KOFactory.getKOTurnierManager();
				KORunde neueRunde = manager.starteNaechsteRunde(KOParameter.turnier);
				if (neueRunde != null)
				{
					KOParameter.turnier.setNextRunde(neueRunde);
					dargestellteRunde = KOParameter.turnier.getNummerAktiveRunde();
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Es sind noch nicht alle Ergebnisse eingetragen","Fehler",JOptionPane.INFORMATION_MESSAGE);
			}
			init();
		}
		if (source == m13) // Turnier speichern
		{
			KOTurnierSaver saver = KOFactory.getKOTurnierSaver();
			saver.save(KOParameter.turnier);
		}
		if (source == m14) // Turnier laden
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
		if (source == m31) // Version anzeigen
		{
			JOptionPane.showMessageDialog(null,Parameter.version,"Version",JOptionPane.INFORMATION_MESSAGE);
		}
		if (source == m32) // Autosave Directory
		{
			File aus = new File(".");
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
			int aktiveRunde = KOParameter.turnier.getNummerAktiveRunde();
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
			KORunde runde = KOParameter.turnier.getRunde(dargestellteRunde);
			Partie partie = runde.getPartie(x);
			KOErgebnisDialog ed = KOFactory.getKOErgebnisDialog();
			ed.setPartie(partie);
			ed.anzeigen();
			init();
		}
	}
}
