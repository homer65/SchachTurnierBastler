package org.myoggradio.stb;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
public class RowRendererPartie extends DefaultTableCellRenderer 
{
	private static final long serialVersionUID = 1L;
	private ArrayList<Partie> partien = null;
	public RowRendererPartie(ArrayList<Partie> partien)
	{
		this.partien = partien;
	}
	public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column)
	{
		Partie partie = partien.get(row);
		int ergebnis = partie.getErgebnis();
		if (ergebnis == 0)
		{
			this.setBackground(Color.RED);
		}
		else 
		{
			this.setBackground(Color.GREEN);
		}
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
}
