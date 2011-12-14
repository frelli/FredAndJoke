import java.awt.Graphics;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class CenterPanel extends JPanel {
	private Image bg;
	private double width=-1, height=1;

	public CenterPanel() {
		bg = null;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
	}

	public int getImgWidth() {
		System.out.println("Returnerat av getImgWidth: "+width);
		return (int) width;
	}

	public int getImgHeight() {
		System.out.println("Returnerat av getImgHeight: "+height);
		return (int) height;
	}

	public void addImage(String img) {
		setLayout(null);
		bg = Toolkit.getDefaultToolkit().getImage(img).getScaledInstance(700, -1, RenderingHints.); //skalar bilden till 700 i bredd, sista argumentet fattar jag inte, men blir nice med 20 :P
		width = -1;
		while(width == -1) //bilden laddar inte direkt, så måste vänta tills storleken inte är -1
			width = bg.getWidth(this);
		
		width = bg.getWidth(this);
		System.out.println("bg.getWidth: "+bg.getWidth(this));
		height = bg.getHeight(this);
		System.out.println("bg.getHeight: "+bg.getHeight(this));
		
//		double scale = width/height;
//		System.out.println("Scale: "+scale);
//		width = 700;
//		height = width/scale;

	}
}
