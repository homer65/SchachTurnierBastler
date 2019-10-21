package org.myoggradio.stb.impl;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.myoggradio.stb.*;
public class SimpleAnzahlRundenDialog extends JDialog implements AnzahlRundenDialog, ActionListener
{
	private static final long serialVersionUID = 1L;
	private JLabel lab1 = new JLabel("Anzahl Runden");
	private JTextField tf1 = new JTextField(20);
	private JButton butt1 = new JButton("ok");
	private JButton butt2 = new JButton("cancel");
	private JPanel cpan = new JPanel();
	public SimpleAnzahlRundenDialog()
	{
		setModal(true);
		tf1.setText(Parameter.anzahlRunden + "");
		cpan.setLayout(new GridLayout(2,2));
		cpan.add(lab1);
		cpan.add(tf1);
		cpan.add(butt1);
		cpan.add(butt2);
		butt1.addActionListener(this);
		butt2.addActionListener(this);
		setContentPane(cpan);
		pack();
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
		if (source == butt1)
		{
			Parameter.anzahlRunden = Integer.parseInt(tf1.getText());
			Parameter.reichweite = Integer.parseInt(tf1.getText());
			dispose();
		}
		if (source == butt2)
		{
			dispose();
		}
	}
}
