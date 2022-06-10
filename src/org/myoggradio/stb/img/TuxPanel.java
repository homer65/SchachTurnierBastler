package org.myoggradio.stb.img;
import javax.swing.*;
import java.awt.*;
public class TuxPanel extends JPanel
{
	public static final long serialVersionUID = 1;
	private Image img = null;
	public TuxPanel(Image img)
	{
		this.img = img;
		Dimension dim = new Dimension(207,244);
		setSize(dim);
		setPreferredSize(dim);
		setMinimumSize(dim);
		setMaximumSize(dim);
		setLayout(null);
	}
	public void paintComponent(Graphics g)
	{
		g.drawImage(img,0,0,this);
	}
}
