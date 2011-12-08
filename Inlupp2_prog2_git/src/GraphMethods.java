import java.util.HashSet;
import java.util.Set;

public class GraphMethods<T>{

	private static void depthFirstSearch(Graph g, Set<Stad> visited,
			Stad current) {
		/*
		 * Varje gång metoden anropas så läggs staden "current" till i visited.
		 */
		visited.add(current);

		/*
		 * För varje stad "current" gås alla dess edges (kopplingar) igenom. För
		 * att få fram vilken stad kopplingen är kopplad till så anropas
		 * metoden getDest() från Stad-klassen.
		 * 
		 * Om staden inte finns med i visited så anropas dFs igen rekursivt och
		 * processen fortsätter
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
	 *            Vilket graph-objekt som användaren vill söka i
	 * @param from
	 *            Staden som du vill starta sökande ifrån
	 * @param to
	 *            Staden som du vill kontrollera koppling till
	 * 
	 * @return Om en koppling finns så returneras true, annars returneras false.
	 * */
	public static boolean pathExists(Graph g, Stad from, Stad to) {
		/*
		 * Skapar ett hash-set som skickas med som arg till dFs Detta kommer att
		 * användas för att addera alla kopplingar som staden "from" har.
		 * 
		 * När dFs har fått jobba färdigt som kommer staden "to" antingen att ha
		 * funnits med bland de hittade städerna och true returneras, annars
		 * returneras false
		 */
		Set<Stad> visited = new HashSet<Stad>();
		depthFirstSearch(g, visited, from);

		return visited.contains(to);
	}

}
