import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class CenterPanel extends JPanel {
	private Image bg;
	private double width=1, height=1;

	public CenterPanel() {
		bg = null;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
	}

	public int getImgWidth() {
		return (int) width;
	}

	public int getImgHeight() {
		return (int) height;
	}

	public void addImage(String img) {
		setLayout(null);
		bg = Toolkit.getDefaultToolkit().getImage(img);
		width = bg.getWidth(this);
		height = bg.getHeight(this);
		
		double scale = width/height;
		width = 700;
		height = width/scale;

	}
}
