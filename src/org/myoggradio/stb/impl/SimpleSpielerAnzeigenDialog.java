package org.myoggradio.stb.impl;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import org.myoggradio.stb.*;
public class SimpleSpielerAnzeigenDialog extends JDialog implements SpielerAnzeigenDialog, ActionListener
{
	private static final long serialVersionUID = 1L;
	private ArrayList<Spieler> spieler = null;
	private JPanel cpan = new JPanel();
	public SimpleSpielerAnzeigenDialog()
	{
		setModal(true);
	}
	public void init()
	{
		cpan = new JPanel();
		cpan.setLayout(new FlowLayout());
		String[] columnNames = new String[4];
		columnNames[0] = "Nummer";
		columnNames[1] = "Vorname";
		columnNames[2] = "Name";
		columnNames[3] = "DWZ";
		String[][] rowData = new String[spieler.size()][4];
		for (int i=0;i<spieler.size();i++)
		{
			Spieler einSpieler = spieler.get(i);
			rowData[i][0] = (i+1) + "";
			rowData[i][1] = einSpieler.getVorname();
			rowData[i][2] = einSpieler.getName();
			rowData[i][3] = einSpieler.getDWZ() + "";
		}
		JTable table = new JTable(rowData,columnNames);
		cpan.add(new JScrollPane(table));
		setContentPane(cpan);
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
		// Mache nichts
	}
}
