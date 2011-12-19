import java.awt.Color;
import java.awt.Font;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;

public class Node extends JComponent {
	private Stad s;
	private String name;
	private boolean clicked = false;

	public Node(String name, int x, int y) {
		this.name = name;
		setBounds(x - 8, y - 8, 130, 50);
		s = new Stad(name);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (clicked)
			g.setColor(Color.RED);
		else
			g.setColor(Color.BLUE);
		g.fillOval(0, 0, 16, 16);
		g.setFont(new Font("Helvetica", Font.BOLD, 16));
		g.setColor(Color.BLACK);
		g.drawString(name, 0, 33);
	}

	public boolean contains(int x, int y) {
		return Math.sqrt(Math.pow(x - 8, 2) + Math.pow(y - 8, 2)) < 8;
	}

	public void clicked() {
		clicked = !clicked;
		repaint();
	}

	public Stad getStad() {
		return s;
	}

	public String toString() {
		return s.getNamn();
	}

}
