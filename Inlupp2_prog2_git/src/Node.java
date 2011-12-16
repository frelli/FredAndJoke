import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;


public class Node extends JComponent{
	private Stad s;

	public Node(int x, int y){
		setBounds(x, y, 10, 10);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.RED);
		g.fillOval(0, 0, getWidth(), getHeight());
	}
	
	public Stad getS() {
		return s;
	}
	
	public void setS(Stad s) {
		this.s = s;
	}

}
