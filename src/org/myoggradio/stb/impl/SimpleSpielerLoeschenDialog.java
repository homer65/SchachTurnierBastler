package org.myoggradio.stb.impl;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.myoggradio.stb.*;
public class SimpleSpielerLoeschenDialog extends JDialog implements SpielerLoeschenDialog, ActionListener
{
	private static final long serialVersionUID = 1L;
	private ArrayList<Spieler> spieler = null;
	private JButton[] butt = null;
	private JPanel cpan = new JPanel();
	public SimpleSpielerLoeschenDialog()
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
		JScrollPane scrpan=new JScrollPane(cpan);
		scrpan.setPreferredSize(Parameter.scrdim);
		setContentPane(scrpan);
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
				spieler.remove(i);
				init();
			}
		}
	}
}
