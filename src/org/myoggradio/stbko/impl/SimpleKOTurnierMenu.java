package org.myoggradio.stbko.impl;
import org.myoggradio.stbko.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.myoggradio.stb.*;
public class SimpleKOTurnierMenu extends JDialog implements KOTurnierMenu,ListSelectionListener, ActionListener
{
	private static final long serialVersionUID = 1L;
	private JPanel cpan = new JPanel();
	private JPanel bpan = new JPanel();
	private JButton butt1 = new JButton("ok");
	private JButton butt2 = new JButton("cancel");
	private JButton butt3 = new JButton("abort");
	private JTable table = null;
	private JTable tableGesetzte = null;
	private ArrayList<Spieler> spieler = null;
	private ArrayList<Spieler> gesetzteSpieler = null;
	public SimpleKOTurnierMenu()
	{
		setModal(true);
	}
	public void init()
	{
		cpan = new JPanel();
		cpan.setLayout(new BorderLayout());
		String[] columnNames = new String[4];
		columnNames[0] = "Nummer";
		columnNames[1] = "Vorname";
		columnNames[2] = "Name";
		columnNames[3] = "DWZ";
		String[] columnNamesGesetzte = new String[4];
		columnNamesGesetzte[0] = "Nummer";
		columnNamesGesetzte[1] = "Vorname";
		columnNamesGesetzte[2] = "Name";
		columnNamesGesetzte[3] = "DWZ";
		String[][] rowData = new String[spieler.size()][4];
		String[][] rowDataGesetzte = new String[gesetzteSpieler.size()][4];
		for (int i=0;i<spieler.size();i++)
		{
			Spieler einSpieler = spieler.get(i);
			rowData[i][0] = (i+1) + "";
			rowData[i][1] = einSpieler.getVorname();
			rowData[i][2] = einSpieler.getName();
			rowData[i][3] = einSpieler.getDWZ() + "";
		}
		for (int i=0;i<gesetzteSpieler.size();i++)
		{
			Spieler einSpieler = gesetzteSpieler.get(i);
			rowDataGesetzte[i][0] = (i+1) + "";
			rowDataGesetzte[i][1] = einSpieler.getVorname();
			rowDataGesetzte[i][2] = einSpieler.getName();
			rowDataGesetzte[i][3] = einSpieler.getDWZ() + "";
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
		tableGesetzte = new JTable(rowDataGesetzte,columnNamesGesetzte);
		tableGesetzte.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableGesetzte.getSelectionModel().addListSelectionListener(this);
		ColumnResizer.resize(table);		
		cpan.add(new JScrollPane(table),BorderLayout.CENTER);
		cpan.add(new JScrollPane(tableGesetzte),BorderLayout.EAST);
		buildbpan();
		cpan.add(bpan,BorderLayout.SOUTH);
		setContentPane(cpan);
		pack();
	}
	private void buildbpan()
	{
		JPanel bpan1 = new JPanel();
		JPanel bpan2 = new JPanel();
		bpan = new JPanel();
		bpan1.setLayout(new GridLayout(1,3));
		bpan.setLayout(new BorderLayout());
		bpan1.add(butt1);
		bpan1.add(butt2);
		bpan1.add(butt3);
		bpan.add(bpan1,BorderLayout.WEST);
		bpan.add(bpan2,BorderLayout.CENTER);
	}
	@Override
	public void anzeigen() 
	{
		butt1.addActionListener(this);
		butt2.addActionListener(this);
		butt3.addActionListener(this);
		spieler = KOParameter.spieler;
		gesetzteSpieler = KOParameter.gesetzteSpieler;
		init();
		setVisible(true);
	}
	@Override
	public void valueChanged(ListSelectionEvent lse) 
	{
		System.out.println("SimpleKOTurnierMenu:Event");
		boolean isAdjusting = lse.getValueIsAdjusting();
		if (!isAdjusting)
		{
			Object quelle = lse.getSource();
			if (quelle == table.getSelectionModel())
			{
				System.out.println("SimpleKOTurnierMenu:Event:table");
				int x = table.getSelectedRow();
				x = table.getRowSorter().convertRowIndexToModel(x);
				Spieler s = spieler.get(x);
				spieler.remove(s);
				gesetzteSpieler.add(s);
				init();
			}
			if (quelle == tableGesetzte.getSelectionModel())
			{
				System.out.println("SimpleKOTurnierMenu:Event:gesetzteTable");
				int x = tableGesetzte.getSelectedRow();
				Spieler s = gesetzteSpieler.get(x);
				gesetzteSpieler.remove(s);
				spieler.add(s);
				init();	
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent ae) 
	{
		Object quelle = ae.getSource();
		if (quelle == butt1) // ok
		{
			int n = spieler.size();
			for (int i=0;i<n;i++)
			{
				double dr = Math.random();
				double di = (double) spieler.size();
				double dj = di * dr;
				int j = (int) dj;
				Spieler s = spieler.get(j);
				gesetzteSpieler.add(s);
				spieler.remove(s);
			}
			KOParameter.turnier.setSpieler(gesetzteSpieler);
			KOTurnierManager manager = KOFactory.getKOTurnierManager();
			KORunde runde = manager.starteErsteRunde(KOParameter.turnier);
			KOParameter.turnier.setNextRunde(runde);
			KOTurnierMenu2 ktm2 = KOFactory.getKOTurnierMenu2();
			ktm2.anzeigen();
			dispose();
		}
		if (quelle == butt2) // cancel
		{
			int n = gesetzteSpieler.size();
			for (int i=0;i<n;i++)
			{
				Spieler s = gesetzteSpieler.get(0);
				spieler.add(s);
				gesetzteSpieler.remove(s);
			}
			init();
		}
		if (quelle == butt3) // abort
		{
			dispose();
		}
	}
}
