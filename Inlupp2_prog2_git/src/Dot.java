import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;


public class Dot extends JComponent{
	
	public Dot(int x, int y){
		setBounds(x, y, 10, 10);
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.RED);
		g.fillOval(0, 0, getWidth(), getHeight());
	}
	

}
