package org.myoggradio.stb.impl;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.myoggradio.stb.*;
public class SimpleSpielerAuswertungDialog extends JDialog implements SpielerAuswertungDialog
{
	private static final long serialVersionUID = 1L;
	private int rundeNummer = 0;
	private Spieler spieler = null;
	private ArrayList<Auswertung> ausw = null;
	public SimpleSpielerAuswertungDialog()
	{
		setModal(true);
	}
	@Override
	public void setRunde(int n) 
	{
		this.rundeNummer = n+1;
		TurnierManager manager = Factory.getTurnierManager();
		ausw = manager.getAuswertung(n);
	}
	@Override
	public void setSpieler(Spieler spieler) 
	{
		this.spieler = spieler;		
	}
	@Override
	public void anzeigen()
	{
		JPanel cpan = new JPanel();
		cpan.setLayout(new BorderLayout());
		
		String[] columnNames = new String[7];
		columnNames[0] = "Platzierung";
		columnNames[1] = "Spieler";
		columnNames[2] = "Punkte";
		columnNames[3] = "Buchholz";
		columnNames[4] = "DWZ";
		columnNames[5] = "Anzahl Weiss";
		columnNames[6] = "Anzahl Schwarz";
		String[][] rowData = new String[1][7];
		for (int i=0;i<ausw.size();i++)
		{
			Auswertung auswertung = ausw.get(i);
			Spieler test = auswertung.getSpieler();
			if (test == spieler)
			{
				rowData[0][0] = (i+1) + "";
				rowData[0][1] = spieler.getVorname() + " " + spieler.getName();
				rowData[0][2] = auswertung.getPunkte() + "";
				rowData[0][3] = auswertung.getBuchholz() + "";
				rowData[0][4] = spieler.getDWZ() + "";
				rowData[0][5] = auswertung.getAnzahlWeiss() + "";
				rowData[0][6] = auswertung.getAnzahlSchwarz() + "";
			}
		}
		JTable table = new JTable(rowData,columnNames);
		JScrollPane scrpan = new JScrollPane(table);
		scrpan.setPreferredSize(new Dimension(Parameter.scrwidth,50));
		
		ArrayList<Partie> partien = new ArrayList<Partie>();
		Turnier turnier = Parameter.turnier;
		for (int i=0;i<rundeNummer;i++)
		{
			Runde runde = turnier.getRunde(i);
			for (int a=0;a<runde.getMaxPartien();a++)
			{
				Partie partie = runde.getPartie(a);
				if (partie.getWeiss() == spieler || partie.getSchwarz() == spieler)
				{
					partien.add(partie);
				}
			}
		}
		String[] columns = new String[3];
		columns[0] = "Weiss";
		columns[1] = "Schwarz";
		columns[2] = "Ergebnis";
		String[][] rows = new String[partien.size()][3];
		for (int i=0;i<partien.size();i++)
		{
			Partie partie = partien.get(i);
			Spieler weiss = partie.getWeiss();
			Spieler schwarz = partie.getSchwarz();
			int ergebnis = partie.getErgebnis();
			rows[i][0] = weiss.getVorname() + " " + weiss.getName() + " " + weiss.getDWZ();
			rows[i][1] = schwarz.getVorname() + " " + schwarz.getName() + " " + schwarz.getDWZ();
			rows[i][2] = ErgebnisDarsteller.get(ergebnis);
		}
		JTable tab = new JTable(rows,columns);
		JScrollPane span = new JScrollPane(tab);
		span.setPreferredSize(new Dimension(Parameter.scrwidth,Parameter.scrheight));
		
		cpan.add(scrpan,BorderLayout.NORTH);
		cpan.add(span,BorderLayout.CENTER);
		setContentPane(cpan);
		pack();
		setVisible(true);
	}
}
