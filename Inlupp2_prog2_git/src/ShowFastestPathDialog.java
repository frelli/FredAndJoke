import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Graphs.Edge;


public class ShowFastestPathDialog extends JPanel {
	private JTextArea textArea;
	private JLabel label;
	private int total = 0;
	
	public ShowFastestPathDialog(List<Edge<Stad>> edges, Stad from, Stad to){
		setLayout (new BorderLayout());
		label = new JLabel("Väg från "+from.getNamn()+" till " +to.getNamn());
		textArea = new JTextArea();
		
		for (Edge<Stad> e : edges){
			textArea.append(e.toString() + "\n");
			total += e.getVikt();
		}
		textArea.append("Total: " + total);
		
		add(new JScrollPane(textArea), BorderLayout.CENTER);
		add(label, BorderLayout.NORTH);
		
	}

}
