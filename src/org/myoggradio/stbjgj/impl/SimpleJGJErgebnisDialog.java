package org.myoggradio.stbjgj.impl;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.myoggradio.stb.*;
import org.myoggradio.stbjgj.JGJErgebnisDialog;
import org.myoggradio.stbjgj.JGJFactory;
import org.myoggradio.stbjgj.JGJParameter;
import org.myoggradio.stbjgj.JGJPartieAuswertungsDialog;
import org.myoggradio.stbjgj.JGJRunde;
import org.myoggradio.stbjgj.JGJTurnier;
public class SimpleJGJErgebnisDialog extends JDialog implements JGJErgebnisDialog, ActionListener
{
	private static final long serialVersionUID = 1L;
	private JPanel epan = null;
	private JPanel bpan = null;
	private JPanel cpan = null;
	private JPanel chpan = null;
	private JButton buttng = null;
	private JButton buttsw = null;
	private JButton buttss = null;
	private JButton buttr = null; 
	private Partie partie = null;
	private JButton buttcw = null;
	private JButton buttcs = null;
	private JButton buttausw = null;
	public SimpleJGJErgebnisDialog()
	{
		setModal(true);
	}
	@Override
	public void setPartie(Partie partie) 
	{
		this.partie = partie;
	}
	@Override
	public void anzeigen() 
	{
		buildepan();
		buildbpan();
		buildchpan();
		buildcpan();
		buttng.addActionListener(this);
		buttsw.addActionListener(this);
		buttss.addActionListener(this);
		buttr.addActionListener(this);
		buttcw.addActionListener(this);
		buttcs.addActionListener(this);
		buttausw.addActionListener(this);
		setContentPane(cpan);
		pack();
		setVisible(true);
	}
	public void buildcpan()
	{
		cpan = new JPanel();
		cpan.setLayout(new BorderLayout());
		cpan.add(chpan,BorderLayout.WEST);
		cpan.add(epan,BorderLayout.CENTER);
		cpan.add(bpan,BorderLayout.EAST);
	}
	public void buildchpan()
	{
		chpan = new JPanel();
		chpan.setLayout(new GridLayout(3,1));
		buttcw = new JButton("change white");
		buttcs = new JButton("change black");
		buttausw = new JButton("Auswertung");
		chpan.add(buttcw);
		chpan.add(buttcs);
		chpan.add(buttausw);
	}
	public void buildbpan()
	{
		bpan = new JPanel();
		bpan.setLayout(new GridLayout(4,1));
		buttng = new JButton("0 : 0");
		buttsw = new JButton("1 : 0");
		buttss = new JButton("0 : 1");
		buttr = new JButton("1/2 : 1/2");
		bpan.add(buttng);
		bpan.add(buttsw);
		bpan.add(buttss);
		bpan.add(buttr);
	}
	public void buildepan()
	{
		epan = new JPanel();
		Spieler weiss = partie.getWeiss();
		Spieler schwarz = partie.getSchwarz();
		int ergebnis = partie.getErgebnis();
		String sweiss = weiss.getVorname() + " " + weiss.getName() + " " + weiss.getDWZ();
		String sschwarz = schwarz.getVorname() + " " + schwarz.getName() + " " + schwarz.getDWZ();
		JLabel labw = new JLabel(sweiss);
		JLabel labs = new JLabel(sschwarz);
		JLabel labe = new JLabel(ErgebnisDarsteller.get(ergebnis));
		JLabel labd = new JLabel(" - ");
		JLabel labx = new JLabel("   ");
		epan.setLayout(new GridLayout(1,5));
		epan.add(labw);
		epan.add(labd);
		epan.add(labs);
		epan.add(labx);
		epan.add(labe);
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		Object source = ae.getSource();
		if (source == buttng)
		{
			partie.setErgebnis(0);
		}
		if (source == buttr)
		{
			partie.setErgebnis(1);
		}
		if (source == buttsw)
		{
			partie.setErgebnis(2);
		}
		if (source == buttss)
		{
			partie.setErgebnis(3);
		}
		if (source == buttcw)
		{
			Spieler spieler = Factory.getSpieler();
			Spieler weiss = partie.getWeiss();
			spieler.setId(weiss.getId());
			spieler.setVorname(weiss.getVorname());
			spieler.setName(weiss.getName());
			spieler.setDWZ(weiss.getDWZ());
			NeuerSpielerDialog nsd = Factory.getNeuerSpielerDialog();
			nsd.setSpieler(spieler);
			nsd.anzeigen();
			if (spieler.getName() != null)
			{
				changeSpieler(weiss,spieler);
			}
		}
		if (source == buttcs)
		{
			Spieler spieler = Factory.getSpieler();
			Spieler schwarz = partie.getSchwarz();
			spieler.setId(schwarz.getId());
			spieler.setVorname(schwarz.getVorname());
			spieler.setName(schwarz.getName());
			spieler.setDWZ(schwarz.getDWZ());
			NeuerSpielerDialog nsd = Factory.getNeuerSpielerDialog();
			nsd.setSpieler(spieler);
			nsd.anzeigen();
			if (spieler.getName() != null)
			{
				changeSpieler(schwarz,spieler);
			}
		}
		if (source == buttausw)
		{
			JGJPartieAuswertungsDialog ad = JGJFactory.getJGJPartieAuswertungsDialog();
			ad.setPartie(partie);;
			ad.anzeigen();
		}
		anzeigen();
	}
	public void changeSpieler(Spieler alt,Spieler neu)
	{
		ArrayList<Spieler> spieler = JGJParameter.spieler;
		for (int i=0;i<spieler.size();i++)
		{
			Spieler test = spieler.get(i);
			if (test.istGleich(alt))
			{
				test = neu;
			}
		}
		JGJTurnier turnier = JGJParameter.turnier;
		int maxrunden = turnier.getMaxrunden();
		for (int i=0;i<maxrunden;i++)
		{
			JGJRunde runde = turnier.getRunde(i);
			Spieler[] rspieler = runde.getSpieler();
			for (int j=0;j<rspieler.length;j++)
			{
				Spieler test = rspieler[j];
				if (test.istGleich(alt))
				{
					rspieler[j] = neu;
				}
			}
			int maxpartien = runde.getMaxPartien();
			for (int j=0;j<maxpartien;j++)
			{
				Partie partie = runde.getPartie(j);
				Spieler weiss = partie.getWeiss();
				Spieler schwarz = partie.getSchwarz();
				if (weiss.istGleich(alt)) partie.setWeiss(neu);
				if (schwarz.istGleich(alt)) partie.setSchwarz(neu);
			}
		}
	}
}
