package org.myoggradio.stb.impl;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.myoggradio.stb.*;
public class SimpleSpielerStornierenDialog extends JDialog implements SpielerStornierenDialog, ActionListener
{
	/*
	 * Spieler wird aus laufendem Turnier entfernt
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Spieler> spieler = null;
	private JButton[] butt = null;
	private JPanel cpan = new JPanel();
	public SimpleSpielerStornierenDialog()
	{
		setModal(true);
	}
	public void init()
	{
		cpan = new JPanel();
		cpan.setLayout(new GridLayout(spieler.size(),1));
		butt = new JButton[spieler.size()];
		for (int i=0;i<spieler.size();i++)
		{
			butt[i] = new JButton(spieler.get(i).toString());
			cpan.add(butt[i]);
			butt[i].addActionListener(this);
		}
		setContentPane(new JScrollPane(cpan));
		pack();
	}
	@Override
	public void setSpieler(ArrayList<Spieler> spieler) 
	{
		this.spieler = spieler;
		init();
	}
	@Override
	public void anzeigen() 
	{
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		Object source = ae.getSource();
		for (int i=0;i<spieler.size();i++)
		{
			if (source == butt[i])
			{
				int ok = JOptionPane.showConfirmDialog(null, "Spieler wirklich stornieren?");
				if (ok == JOptionPane.YES_OPTION)
				{
					Parameter.turnier.storniereSpieler(spieler.get(i));
					dispose();
				}
			}
		}
	}
}
