package org.myoggradio.stbko.impl;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.myoggradio.stb.ColumnResizer;
import org.myoggradio.stb.ErgebnisDarsteller;
import org.myoggradio.stb.Partie;
import org.myoggradio.stb.Spieler;
import org.myoggradio.stbko.*;
public class SimpleKOAuswertungDialog extends JDialog implements KOAuswertungDialog
{
	private static final long serialVersionUID = 1L;
	private JPanel tpanWeiss = null;
	private JPanel tpanSchwarz = null;
	private JPanel cpan = null;
	private Spieler weiss = null;
	private Spieler schwarz = null;
	private ArrayList<Partie> partienWeiss = new ArrayList<Partie>();
	private ArrayList<Partie> partienSchwarz = new ArrayList<Partie>();
	@Override
	public void setPartie(Partie partie) 
	{
		setModal(true);
		weiss = partie.getWeiss();
		schwarz = partie.getSchwarz();
		KOTurnier turnier = KOParameter.turnier;
		int aktiveRunde = turnier.getNummerAktiveRunde();
		for (int i=0;i<=aktiveRunde;i++)
		{
			KORunde runde = turnier.getRunde(i);
			for (int a=0;a<runde.getMaxPartien();a++)
			{
				Partie test = runde.getPartie(a);
				if (weiss == test.getWeiss() || weiss == test.getSchwarz())
				{
					if (!istFreilos(weiss)) partienWeiss.add(test);
				}
				if (schwarz == test.getWeiss() || schwarz == test.getSchwarz())
				{
					if (!istFreilos(schwarz)) partienSchwarz.add(test);
				}
			}
		}
	}
	@Override
	public void anzeigen()
	{
		buildtpanWeiss();
		buildtpanSchwarz();
		buildcpan();
		setContentPane(cpan);
		pack();
		setVisible(true);
	}
	public void buildcpan()
	{
		cpan = new JPanel();
		cpan.setLayout(new BorderLayout());
		cpan.add(tpanWeiss,BorderLayout.WEST);
		cpan.add(tpanSchwarz,BorderLayout.EAST);
	}
	public void buildtpanWeiss()
	{
		tpanWeiss = new JPanel();
		tpanWeiss.setLayout(new FlowLayout());
		String[] columnNames = new String[3];
		columnNames[0] = "Weiss";
		columnNames[1] = "Schwarz";
		columnNames[2] = "Ergebnis";
		int n = partienWeiss.size();
		String[][] rowData = new String[n][3];
		for (int i=0;i<n;i++)
		{
			Partie partie = partienWeiss.get(i);
			Spieler weiss = partie.getWeiss();
			Spieler schwarz = partie.getSchwarz();
			String sweiss = weiss.getVorname() + " " + weiss.getName() + " " + weiss.getDWZ();
			String sschwarz = schwarz.getVorname() + " " + schwarz.getName() + " " + schwarz.getDWZ();
			int ergebnis = partie.getErgebnis();
			rowData[i][0] = sweiss;
			rowData[i][1] = sschwarz;
			rowData[i][2] = ErgebnisDarsteller.get(ergebnis);
		}
		JTable table = new JTable(rowData,columnNames);
		ColumnResizer.resize(table);
		JScrollPane scrpane = new JScrollPane(table);
		tpanWeiss.add(scrpane);
	}
	public void buildtpanSchwarz()
	{
		tpanSchwarz = new JPanel();
		tpanSchwarz.setLayout(new FlowLayout());
		String[] columnNames = new String[3];
		columnNames[0] = "Weiss";
		columnNames[1] = "Schwarz";
		columnNames[2] = "Ergebnis";
		int n = partienSchwarz.size();
		String[][] rowData = new String[n][3];
		for (int i=0;i<n;i++)
		{
			Partie partie = partienSchwarz.get(i);
			Spieler weiss = partie.getWeiss();
			Spieler schwarz = partie.getSchwarz();
			String sweiss = weiss.getVorname() + " " + weiss.getName() + " " + weiss.getDWZ();
			String sschwarz = schwarz.getVorname() + " " + schwarz.getName() + " " + schwarz.getDWZ();
			int ergebnis = partie.getErgebnis();
			rowData[i][0] = sweiss;
			rowData[i][1] = sschwarz;
			rowData[i][2] = ErgebnisDarsteller.get(ergebnis);
		}
		JTable table = new JTable(rowData,columnNames);
		ColumnResizer.resize(table);
		JScrollPane scrpane = new JScrollPane(table);
		tpanSchwarz.add(scrpane);
	}
	private boolean istFreilos(Spieler spieler)
	{
		boolean erg = false;
		if (spieler.getName().equals("FREILOS")) erg = true;
		return erg;
	}
}
