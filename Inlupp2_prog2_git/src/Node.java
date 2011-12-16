import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;


public class Node extends JComponent{
	private Stad s;

	public Node(String name, int x, int y){
		setBounds(x-8, y-8, 16, 16);
		s = new Stad(name);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.BLUE);
		g.fillOval(0, 0, getWidth(), getHeight());
	}
	
	public Stad getS() {
		return s;
	}
	
	public void setS(Stad s) {
		this.s = s;
	}

}
