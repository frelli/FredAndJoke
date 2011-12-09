import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphMethods {

	public static <T> List<Edge<T>> shortestPath(Graph<T> g, T from, T to) {
		if (!pathExists(g, from, to)) {
			return null;
		} else {
			Map<T, DjikHolder<T>> djikMap = new HashMap<T, DjikHolder<T>>();
			return shortestPathHelper(g, from, to, djikMap);
		}
	}

	private static <T> List<Edge<T>> shortestPathHelper(Graph<T> g, T from,
			T to, Map<T, DjikHolder<T>> djikMap) {

		DjikHolder<T> djikTemp = new DjikHolder<T>();
		djikMap.put(from, djikTemp);
		djikTemp.setDeterminated(true);
		djikTemp.setFastestTime(0);

		return null;
	}
	private static <T> void depthFirstSearch(Graph<T> g, Set<T> visited,
			T current) {
		/*
		 * Varje gång metoden anropas så läggs staden "current" till i visited.
		 */
		visited.add(current);

		/*
		 * För varje stad "current" gås alla dess edges (kopplingar) igenom. För
		 * att få fram vilken stad kopplingen är kopplad till så anropas metoden
		 * getDest() från Stad-klassen.
		 * 
		 * Om staden inte finns med i visited så anropas dFs igen rekursivt och
		 * processen fortsätter
		 */
		for (Edge<T> e : g.getEdgesFrom(current)) {
			T to = e.getDest();

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
	public static <T> boolean pathExists(Graph<T> g, T from, T to) {
		/*
		 * Skapar ett hash-set som skickas med som arg till dFs Detta kommer att
		 * användas för att addera alla kopplingar som staden "from" har.
		 * 
		 * När dFs har fått jobba färdigt som kommer staden "to" antingen att ha
		 * funnits med bland de hittade städerna och true returneras, annars
		 * returneras false
		 */
		Set<T> visited = new HashSet<T>();
		depthFirstSearch(g, visited, from);

		return visited.contains(to);
	}

}
