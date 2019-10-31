package org.myoggradio.stb.impl;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.myoggradio.stb.*;
public class SimpleSpielerLoeschenDialog extends JDialog implements SpielerLoeschenDialog,ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	private ArrayList<Spieler> spieler = null;
	private JPanel cpan = new JPanel();
	private JTable table = null;
	public SimpleSpielerLoeschenDialog()
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
		table = new JTable(rowData,columnNames);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(this);
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>();
		sorter.setModel(table.getModel());
		sorter.setComparator(0, new NumberComparator());
		sorter.setComparator(3, new NumberComparator());
		table.setRowSorter(sorter);
		ColumnResizer.resize(table);
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
	public void valueChanged(ListSelectionEvent lse) 
	{
		boolean isAdjusting = lse.getValueIsAdjusting();
		if (!isAdjusting)
		{
			int x = table.getSelectedRow();
			x = table.getRowSorter().convertRowIndexToModel(x);
			Spieler s = spieler.get(x);
			String msg = "Spieler " + s.getVorname() + " " + s.getName() + " " + s.getDWZ() + " wirklich loeschen?";
			int ok = JOptionPane.showConfirmDialog(null,msg);
			if (ok == JOptionPane.YES_OPTION)
			{
				spieler.remove(s);
				//SpielerLoeschenDialog nsm = Factory.getSpielerLoeschenDialog();
				//nsm.setSpieler(Parameter.spieler);
				//nsm.anzeigen();
				//dispose();
				init();
			}
		}
	}
}
