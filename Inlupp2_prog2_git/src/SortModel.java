import javax.swing.DefaultListModel;


public class SortModel extends DefaultListModel{
	public void addSorted(Stad ny){
		int plats = 0;
		while(plats < size() && ny.getNamn().compareTo(((Stad)get(plats)).getNamn()) > 0){
			plats++;
		}
		add(plats, ny);
		
	}

}
