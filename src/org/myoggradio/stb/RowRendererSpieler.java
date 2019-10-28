package org.myoggradio.stb;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
public class RowRendererSpieler extends DefaultTableCellRenderer 
{
	private static final long serialVersionUID = 1L;
	private ArrayList<Partie> partien = null;
	private Spieler spieler = null;
	public RowRendererSpieler(ArrayList<Partie> partien,Spieler spieler)
	{
		this.partien = partien;
		this.spieler = spieler;
	}
	public Component getTableCellRendererComponent(JTable table,Object value,boolean isSelected,boolean hasFocus,int row,int column)
	{
		Partie partie = partien.get(row);
		Spieler weiss = partie.getWeiss();
		Spieler schwarz = partie.getSchwarz();
		int ergebnis = partie.getErgebnis();
		if (weiss.getName().equals("FREILOS"))
		{
			this.setBackground(Color.GREEN);
		}
		else if (spieler == weiss)
		{
			if (ergebnis == 1) this.setBackground(Color.YELLOW);
			else if (ergebnis == 2) this.setBackground(Color.GREEN);
			else if (ergebnis == 3) this.setBackground(Color.RED);
			else this.setBackground(Color.GRAY);
		}
		else if (spieler == schwarz)
		{
			if (ergebnis == 1) this.setBackground(Color.YELLOW);
			else if (ergebnis == 2) this.setBackground(Color.RED);
			else if (ergebnis == 3) this.setBackground(Color.GREEN);
			else this.setBackground(Color.GRAY);
		}
		else
		{
			this.setBackground(Color.GRAY);
		}
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
}
