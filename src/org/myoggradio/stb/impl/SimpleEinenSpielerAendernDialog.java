package org.myoggradio.stb.impl;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.myoggradio.stb.EinenSpielerAendernDialog;
import org.myoggradio.stb.Spieler;
public class SimpleEinenSpielerAendernDialog extends JDialog implements ActionListener, EinenSpielerAendernDialog
{
	private static final long serialVersionUID = 1L;
	private Spieler spieler = null;
	private JLabel lab1 = new JLabel("Vorname");
	private JTextField tf1 = new JTextField(20);
	private JLabel lab2 = new JLabel("Name");
	private JTextField tf2 = new JTextField(20);
	private JLabel lab3 = new JLabel("DWZ");
	private JTextField tf3 = new JTextField(20);
	private JButton butt1 = new JButton("ok");
	private JButton butt2 = new JButton("cancel");
	private JPanel cpan = new JPanel();
	@Override
	public void setSpieler(Spieler spieler) 
	{
		this.spieler = spieler;
	}
	@Override
	public void anzeigen() 
	{
		setModal(true);
		tf1.setText(spieler.getVorname());
		tf2.setText(spieler.getName());
		tf3.setText(spieler.getDWZ() + "");
		cpan.setLayout(new GridLayout(4,2));
		cpan.add(lab1);
		cpan.add(tf1);
		cpan.add(lab2);
		cpan.add(tf2);
		cpan.add(lab3);
		cpan.add(tf3);
		cpan.add(butt1);
		cpan.add(butt2);
		butt1.addActionListener(this);
		butt2.addActionListener(this);
		setContentPane(cpan);
		pack();	
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		Object source = ae.getSource();
		if (source == butt1)
		{
			spieler.setVorname(tf1.getText());
			spieler.setName(tf2.getText());
			spieler.setDWZ(Integer.parseInt(tf3.getText()));
			dispose();
		}
		if (source == butt2)
		{
			dispose();
		}
	}
}
