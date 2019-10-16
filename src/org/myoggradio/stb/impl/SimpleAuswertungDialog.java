package org.myoggradio.stb.impl;
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
		cpan.setLayout(new GridLayout(ausw.size(),3));
		for (int i=0;i<ausw.size();i++)
		{
			Auswertung auswertung = ausw.get(i);
			Spieler spieler = auswertung.getSpieler();
			double punkte = auswertung.getPunkte();
			double buchholz = auswertung.getBuchholz();
			JLabel labs = new JLabel(spieler.getVorname() + " " + spieler.getName() + " " +spieler.getDWZ());
			JLabel labp = new JLabel("   " + punkte);
			JLabel labb = new JLabel("   " + buchholz);
			cpan.add(labs);
			cpan.add(labp);
			cpan.add(labb);
		}
		setContentPane(new JScrollPane(cpan));
		pack();
		setVisible(true);
	}
}
