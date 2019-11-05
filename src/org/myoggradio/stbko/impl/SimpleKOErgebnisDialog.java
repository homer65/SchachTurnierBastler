package org.myoggradio.stbko.impl;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.myoggradio.stb.*;
import org.myoggradio.stbko.*;
public class SimpleKOErgebnisDialog extends JDialog implements KOErgebnisDialog, ActionListener
{
	private static final long serialVersionUID = 1L;
	private JPanel epan = null;
	private JPanel bpan = null;
	private JPanel cpan = null;
	private JPanel chpan = null;
	private JButton buttng = null;
	private JButton buttsw = null;
	private JButton buttss = null;
	private Partie partie = null;
	private JButton buttcw = null;
	private JButton buttcs = null;
	public SimpleKOErgebnisDialog()
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
		buttcw.addActionListener(this);
		buttcs.addActionListener(this);
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
		chpan.setLayout(new GridLayout(2,1));
		buttcw = new JButton("change white");
		buttcs = new JButton("change black");
		chpan.add(buttcw);
		chpan.add(buttcs);
	}
	public void buildbpan()
	{
		bpan = new JPanel();
		bpan.setLayout(new GridLayout(3,1));
		buttng = new JButton("0 : 0");
		buttsw = new JButton("1 : 0");
		buttss = new JButton("0 : 1");
		bpan.add(buttng);
		bpan.add(buttsw);
		bpan.add(buttss);
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
			spieler.setVorname(weiss.getVorname());
			spieler.setName(weiss.getName());
			spieler.setDWZ(weiss.getDWZ());
			NeuerSpielerDialog nsd = Factory.getNeuerSpielerDialog();
			nsd.setSpieler(spieler);
			nsd.anzeigen();
			if (spieler.getName() != null)
			{
				partie.setWeiss(spieler);
			}
		}
		if (source == buttcs)
		{
			Spieler spieler = Factory.getSpieler();
			Spieler schwarz = partie.getSchwarz();
			spieler.setVorname(schwarz.getVorname());
			spieler.setName(schwarz.getName());
			spieler.setDWZ(schwarz.getDWZ());
			NeuerSpielerDialog nsd = Factory.getNeuerSpielerDialog();
			nsd.setSpieler(spieler);
			nsd.anzeigen();
			if (spieler.getName() != null)
			{
				partie.setSchwarz(spieler);
			}
		}
		anzeigen();
	}
}
