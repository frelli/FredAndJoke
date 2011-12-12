import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphMethods {
	
	/**
	 * En metod som r�knar ut den snabbaste v�gen mellan
	 * tv� best�mda noder i en graph.
	 * 
	 * @param g Gaph-objektet som noder tillh�r
	 * @param from T-objektet som f�rden b�rjar ifr�n
	 * @param to T-objektet som �r slutdestinationen
	 * 
	 * @return Om det finns en v�gen mellan "to" och "from" s� returneras
	 * en java.util.List med den edge-objekten till den snabbaste v�gen.
	 * Om en v�g inte existerar s� returneras null.
	 * */
	public static <T> List<Edge<T>> shortestPath(Graph<T> g, T from, T to) {
		
		if (!pathExists(g, from, to)) { // Kontrollerar att det finns en v�g mellan "to" och "from"
			return null;

		} else { // Om en v�g existerar:
		
			Map<T, DjikHolder<T>> djikMap = new HashMap<T, DjikHolder<T>>(); // Skapar en ny Map som h�ller ett DjikHolder-objekt till varje T-objekt
			
			/*
			 * H�mtar ut alla noder fr�n graphen g. Dessa l�ggs sedan in
			 * som nycklar i djikMap. V�rdet f�r paren i djikMap blir
			 * ett nytt DjikHolder-objekt. Varje node f�r allts� sitt eget
			 * DjikHolder-objekt.
			 */
			for (T node : g.getNodes().keySet()) { 
				djikMap.put(node, new DjikHolder<T>());
				if (node == from) {
					djikMap.get(node).setFastestTime(0);
				}
			}
			Map<T, DjikHolder<T>> dM = shortestPathHelper(g, to, djikMap); //Skapar en map inneh�llande den snabbaste v�gen
			
			List<Edge<T>> bestWay = new ArrayList<Edge<T>>(); //Skapar en tom ArrayList att skicka med till djikSorter
			
			return djikSorter(g, bestWay, dM, to); //returnerar en java.util.List med Edge-objekt med den snabbaste v�gen
		}
	}
	
	/**
	 * En rekursiv metod som f�rst och fr�mst anropar sig sj�lv tills getFrom()-metoden i DjikHolder �r null, 
	 * och sedan hittar den kortaste v�gen mellan aktuella noden och noden innan den aktuella noden och l�gger till edge:n i en arraylista.
	 * Sedan g�r den det f�r alla DjikHolders den har varit inne i allt eftersom s� att listan g�r fr�n f�rsta noden till
	 * sista ist�llet f�r sista till f�rsta.
	 */
	private static <T> List<Edge<T>> djikSorter(Graph<T> g, List<Edge<T>> bestWay, Map<T, DjikHolder<T>> djikMap, T current){
		Edge<T> edgeTemp = null; // anv�nds som h�llare �t edges f�r att hitta den edge med l�gst vikt
		
		if(djikMap.get(current).getFrom() == null){
			return bestWay;
		}else{
			/*
			 * Anropar sig sj�lv och anv�nder getFrom() i DjikHolder s� att den 
			 * hoppar ett steg tillbaka
			 */
			djikSorter(g, bestWay, djikMap, djikMap.get(current).getFrom());
			/*
			 * Loop som f�rst s�tter den f�rsta edgen f�r getEdgesBetween() till edgeTemp 
			 * och sedan skriver �ver edgeTemp till den som har l�gst getVikt() av alla som finns
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
	 * En rekursiv metod som anropar sig sj�lv �nda tills slutnoden (djikholdern med staden vi ska till) 
	 * har blivit set till determinated(true)
	 */
	private static <T> Map<T, DjikHolder<T>> shortestPathHelper(Graph<T> g, T to,
			Map<T, DjikHolder<T>> djikMap) {
		
		// Om slutnoden �r best�md s� returneras resultatet
		if (djikMap.get(to).isDeterminated()) {
			return djikMap;
		}
		
		// S�ker igenom alla djikMap-objekt
		for (Map.Entry<T, DjikHolder<T>> me : djikMap.entrySet()) {
			
			//Plockar ut objektet med l�gst getFastest-tid som inte �r best�mt
			if (me.getValue().getFastestTime() == getSmallest(djikMap)
					&& !me.getValue().isDeterminated()) {

				me.getValue().setDeterminated(true); // S�tter objektet till best�mt
				 
				/* G�r igenom alla "grannar" till det aktuella objektet och
				 * kollar om objektets fastestTime + b�gen mellan objektet
				 * och grannen �r mindra �n grannens fastest time. Om det h�nder
				 * s� s�tts grannens fastestTime till aktuella nodens tid + b�gens
				 * vikt. Samt s�tter grannens "from" till det aktuella objektet
				 * 
				 */
				for (Edge<T> e : g.getEdgesFrom(me.getKey())){

					if (e.getVikt() + me.getValue().getFastestTime() < djikMap
							.get(e.getDest()).getFastestTime()) { //Kollar tiderna som n�mns ovan
						
						djikMap.get(e.getDest()).setFastestTime(
								e.getVikt() + me.getValue().getFastestTime()); // S�tter den nya tiden

						djikMap.get(e.getDest()).setFrom(me.getKey()); // S�tter den nya "from"
					}
				}
			}
		}
		return shortestPathHelper(g, to, djikMap);
	}

	/**
	 * En rekursiv undermetod som l�gger till igenomg�ngna noder i en lista (visited)
	 * f�r att sedan kunna avg�ra om en nod har en connection till en annan nod
	 */
	private static <T> void depthFirstSearch(Graph<T> g, Set<T> visited,
			T current) {
		/*
		 * Varje g�ng metoden anropas s� l�ggs staden "current" till i visited.
		 */
		visited.add(current);

		/*
		 * F�r varje stad "current" g�s alla dess edges (kopplingar) igenom. F�r
		 * att f� fram vilken stad kopplingen �r kopplad till s� anropas metoden
		 * getDest() fr�n Stad-klassen.
		 * 
		 * Om staden inte finns med i visited s� anropas dFs igen rekursivt och
		 * processen forts�tter
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
	 *            Vilket graph-objekt som anv�ndaren vill s�ka i
	 * @param from
	 *            Staden som du vill starta s�kande ifr�n
	 * @param to
	 *            Staden som du vill kontrollera koppling till
	 * 
	 * @return Om en koppling finns s� returneras true, annars returneras false.
	 * */
	public static <T> boolean pathExists(Graph<T> g, T from, T to) {
		/*
		 * Skapar ett hash-set som skickas med som arg till dFs Detta kommer att
		 * anv�ndas f�r att addera alla kopplingar som staden "from" har.
		 * 
		 * N�r dFs har f�tt jobba f�rdigt som kommer staden "to" antingen att ha
		 * funnits med bland de hittade st�derna och true returneras, annars
		 * returneras false
		 */
		Set<T> visited = new HashSet<T>();
		depthFirstSearch(g, visited, from);

		return visited.contains(to);
	}

	/**
	 * En metod som h�mtar den DjikHolder som har l�gst vikt(tid) och som inte �r determinated
	 */
	private static <T> int getSmallest(Map<T, DjikHolder<T>> djikMap) {
		
		int smallest = Integer.MAX_VALUE; //Initierar med ett stort v�rde
		
		/*
		 * Plockar g�r igenom alla DjikHolder-objekt och kollar
		 * vilket som har minst v�rde p� sin fastestTime-variabel.
		 * Detta objekt f�r inte heller ha determinated == true
		 * om det ska r�knas som minst.
		 */
		for (DjikHolder<T> d : djikMap.values()) {
			if (d.getFastestTime() < smallest && !d.isDeterminated())
				smallest = d.getFastestTime();
		}

		return smallest;
	}

}
