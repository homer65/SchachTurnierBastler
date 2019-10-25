package org.myoggradio.stb.impl;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.myoggradio.stb.*;
public class SimpleAuswertungDialog extends JDialog implements AuswertungDialog
{
	private static final long serialVersionUID = 1L;
	private int rundeNummer = 0;
	private ArrayList<Auswertung> ausw = null;
	public SimpleAuswertungDialog()
	{
		setModal(true);
	}
	@Override
	public void setRunde(int n) 
	{
		this.rundeNummer = n;
		TurnierManager manager = Factory.getTurnierManager();
		ausw = manager.getAuswertung(rundeNummer);
		Collections.sort(ausw,new AuswertungComparator());
	}
	@Override
	public void anzeigen()
	{
		JPanel cpan = new JPanel();
		cpan.setLayout(new GridLayout(ausw.size()+1,7));
		cpan.add(new JLabel("Tisch Nummer"));
		cpan.add(new JLabel("Spieler"));
		cpan.add(new JLabel("Punkte"));
		cpan.add(new JLabel("Buchholz"));
		cpan.add(new JLabel("DWZ"));
		cpan.add(new JLabel("Anzahl Weiss"));
		cpan.add(new JLabel("Anzahl Schwarz"));
		for (int i=0;i<ausw.size();i++)
		{
			Auswertung auswertung = ausw.get(i);
			Spieler spieler = auswertung.getSpieler();
			double punkte = auswertung.getPunkte();
			double buchholz = auswertung.getBuchholz();
			int dwz = spieler.getDWZ();
			int anzahlWeiss = auswertung.getAnzahlWeiss();
			int anzahlSchwarz = auswertung.getAnzahlSchwarz();
			JLabel lab1 = new JLabel("   " + (i+1));
			JLabel labs = new JLabel(spieler.getVorname() + " " + spieler.getName());
			JLabel labp = new JLabel("   " + punkte);
			JLabel labb = new JLabel("   " + buchholz);
			JLabel labd = new JLabel("   " + dwz);
			JLabel labaw = new JLabel("  " + anzahlWeiss);
			JLabel labas = new JLabel("  " + anzahlSchwarz);
			cpan.add(lab1);
			cpan.add(labs);
			cpan.add(labp);
			cpan.add(labb);
			cpan.add(labd);
			cpan.add(labaw);
			cpan.add(labas);
		}
		JScrollPane scrpan=new JScrollPane(cpan);
		scrpan.setPreferredSize(new Dimension(Parameter.scrwidth,Parameter.scrheight));
		setContentPane(scrpan);
		pack();
		setVisible(true);
	}
}
