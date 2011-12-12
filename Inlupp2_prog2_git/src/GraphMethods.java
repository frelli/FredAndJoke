import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphMethods {
	
	/**
	 * En metod som räknar ut den snabbaste vägen mellan
	 * två bestämda noder i en graph.
	 * 
	 * @param g Gaph-objektet som noder tillhör
	 * @param from T-objektet som färden börjar ifrån
	 * @param to T-objektet som är slutdestinationen
	 * 
	 * @return Om det finns en vägen mellan "to" och "from" så returneras
	 * en java.util.List med den edge-objekten till den snabbaste vägen.
	 * Om en väg inte existerar så returneras null.
	 * */
	public static <T> List<Edge<T>> shortestPath(Graph<T> g, T from, T to) {
		
		if (!pathExists(g, from, to)) { // Kontrollerar att det finns en väg mellan "to" och "from"
			return null;

		} else { // Om en väg existerar:
		
			Map<T, DjikHolder<T>> djikMap = new HashMap<T, DjikHolder<T>>(); // Skapar en ny Map som håller ett DjikHolder-objekt till varje T-objekt
			
			/*
			 * Hämtar ut alla noder från graphen g. Dessa läggs sedan in
			 * som nycklar i djikMap. Värdet för paren i djikMap blir
			 * ett nytt DjikHolder-objekt. Varje node får alltså sitt eget
			 * DjikHolder-objekt.
			 */
			for (T node : g.getNodes().keySet()) { 
				djikMap.put(node, new DjikHolder<T>());
				if (node == from) {
					djikMap.get(node).setFastestTime(0);
				}
			}
			Map<T, DjikHolder<T>> dM = shortestPathHelper(g, to, djikMap); //Skapar en map innehållande den snabbaste vägen
			
			List<Edge<T>> bestWay = new ArrayList<Edge<T>>(); //Skapar en tom ArrayList att skicka med till djikSorter
			
			return djikSorter(g, bestWay, dM, to); //returnerar en java.util.List med Edge-objekt med den snabbaste vägen
		}
	}
	
	/**
	 * En rekursiv metod som först och främst anropar sig själv tills getFrom()-metoden i DjikHolder är null, 
	 * och sedan hittar den kortaste vägen mellan aktuella noden och noden innan den aktuella noden och lägger till edge:n i en arraylista.
	 * Sedan gör den det för alla DjikHolders den har varit inne i allt eftersom så att listan går från första noden till
	 * sista istället för sista till första.
	 */
	private static <T> List<Edge<T>> djikSorter(Graph<T> g, List<Edge<T>> bestWay, Map<T, DjikHolder<T>> djikMap, T current){
		Edge<T> edgeTemp = null; // används som hållare åt edges för att hitta den edge med lägst vikt
		
		if(djikMap.get(current).getFrom() == null){
			return bestWay;
		}else{
			/*
			 * Anropar sig själv och använder getFrom() i DjikHolder så att den 
			 * hoppar ett steg tillbaka
			 */
			djikSorter(g, bestWay, djikMap, djikMap.get(current).getFrom());
			/*
			 * Loop som först sätter den första edgen för getEdgesBetween() till edgeTemp 
			 * och sedan skriver över edgeTemp till den som har lägst getVikt() av alla som finns
			 */
			for(Edge<T> e : g.getEdgesBetween(djikMap.get(current).getFrom(), current)){ 
				if(edgeTemp == null)
					edgeTemp = e;
				else if(e.getVikt() < edgeTemp.getVikt()){
					edgeTemp = e;
				}
				
			}
			
			bestWay.add(edgeTemp); // adderar edgen till en array
		}
		
		return bestWay; // skickar tillbaka arrayen med alla egdes i
	}

	/**
	 * En rekursiv metod som anropar sig själv ända tills slutnoden (djikholdern med staden vi ska till) 
	 * har blivit set till determinated(true)
	 */
	private static <T> Map<T, DjikHolder<T>> shortestPathHelper(Graph<T> g, T to,
			Map<T, DjikHolder<T>> djikMap) {
		
		// Om slutnoden är bestämd så returneras resultatet
		if (djikMap.get(to).isDeterminated()) {
			return djikMap;
		}
		
		// Söker igenom alla djikMap-objekt
		for (Map.Entry<T, DjikHolder<T>> me : djikMap.entrySet()) {
			
			//Plockar ut objektet med lägst getFastest-tid som inte är bestämt
			if (me.getValue().getFastestTime() == getSmallest(djikMap)
					&& !me.getValue().isDeterminated()) {

				me.getValue().setDeterminated(true); // Sätter objektet till bestämt
				 
				/* Går igenom alla "grannar" till det aktuella objektet och
				 * kollar om objektets fastestTime + bågen mellan objektet
				 * och grannen är mindra än grannens fastest time. Om det händer
				 * så sätts grannens fastestTime till aktuella nodens tid + bågens
				 * vikt. Samt sätter grannens "from" till det aktuella objektet
				 * 
				 */
				for (Edge<T> e : g.getEdgesFrom(me.getKey())){

					if (e.getVikt() + me.getValue().getFastestTime() < djikMap
							.get(e.getDest()).getFastestTime()) { //Kollar tiderna som nämns ovan
						
						djikMap.get(e.getDest()).setFastestTime(
								e.getVikt() + me.getValue().getFastestTime()); // Sätter den nya tiden

						djikMap.get(e.getDest()).setFrom(me.getKey()); // Sätter den nya "from"
					}
				}
			}
		}
		return shortestPathHelper(g, to, djikMap);
	}

	/**
	 * En rekursiv undermetod som lägger till igenomgångna noder i en lista (visited)
	 * för att sedan kunna avgöra om en nod har en connection till en annan nod
	 */
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

	/**
	 * En metod som hämtar den DjikHolder som har lägst vikt(tid) och som inte är determinated
	 */
	private static <T> int getSmallest(Map<T, DjikHolder<T>> djikMap) {
		
		int smallest = Integer.MAX_VALUE; //Initierar med ett stort värde
		
		/*
		 * Plockar går igenom alla DjikHolder-objekt och kollar
		 * vilket som har minst värde på sin fastestTime-variabel.
		 * Detta objekt får inte heller ha determinated == true
		 * om det ska räknas som minst.
		 */
		for (DjikHolder<T> d : djikMap.values()) {
			if (d.getFastestTime() < smallest && !d.isDeterminated())
				smallest = d.getFastestTime();
		}

		return smallest;
	}

}
