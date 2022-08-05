package org.myoggradio.stb;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
public class Menu extends JFrame implements ActionListener
{
	static final long serialVersionUID = 1;
	public void anzeigen()
	{
		Frame rahmen = this;
		WindowListener wl = new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{
				we.getWindow().dispose();
			}
		};
		rahmen.addWindowListener(wl);
		rahmen.pack();
		rahmen.setVisible(true);
	}
	public void actionPerformed(ActionEvent ae)
	{

	}
}
