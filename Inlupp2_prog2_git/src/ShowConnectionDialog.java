import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Graphs.Edge;


public class ShowConnectionDialog extends JPanel {
	private JLabel lTitle;
	private JList list;
	
	public ShowConnectionDialog(List<Edge<Stad>> connections, Stad from, Stad to){
		setLayout (new BorderLayout());
		
		lTitle = new JLabel ("Förbindelser från "+from.getNamn()+" till "+to.getNamn()+":");
		add (lTitle, BorderLayout.NORTH);
		
		list = new JList(connections.toArray());
		
		add (new JScrollPane(list), BorderLayout.CENTER);
		
	}

}
