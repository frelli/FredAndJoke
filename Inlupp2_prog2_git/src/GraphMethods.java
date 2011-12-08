import java.util.HashSet;
import java.util.Set;

public class GraphMethods<T>{

	private static void depthFirstSearch(Graph g, Set<Stad> visited,
			Stad current) {
		/*
		 * Varje g�ng metoden anropas s� l�ggs staden "current" till i visited.
		 */
		visited.add(current);

		/*
		 * F�r varje stad "current" g�s alla dess edges (kopplingar) igenom. F�r
		 * att f� fram vilken stad kopplingen �r kopplad till s� anropas
		 * metoden getDest() fr�n Stad-klassen.
		 * 
		 * Om staden inte finns med i visited s� anropas dFs igen rekursivt och
		 * processen forts�tter
		 */
		for (Edge<T> e : g.getEdgesFrom(current)) {
			Stad to = e.getDest();

			if (!visited.contains(to))
				depthFirstSearch(g, visited, to);
		}
	}

	/**
	 * Denna metod kontrollerar om en viss stad har en koppling till en annan
	 * stad.
	 * 
	 * @param g
	 *            Vilket graph-objekt som anv�ndaren vill s�ka i
	 * @param from
	 *            Staden som du vill starta s�kande ifr�n
	 * @param to
	 *            Staden som du vill kontrollera koppling till
	 * 
	 * @return Om en koppling finns s� returneras true, annars returneras false.
	 * */
	public static boolean pathExists(Graph g, Stad from, Stad to) {
		/*
		 * Skapar ett hash-set som skickas med som arg till dFs Detta kommer att
		 * anv�ndas f�r att addera alla kopplingar som staden "from" har.
		 * 
		 * N�r dFs har f�tt jobba f�rdigt som kommer staden "to" antingen att ha
		 * funnits med bland de hittade st�derna och true returneras, annars
		 * returneras false
		 */
		Set<Stad> visited = new HashSet<Stad>();
		depthFirstSearch(g, visited, from);

		return visited.contains(to);
	}

}
