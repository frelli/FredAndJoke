package Graphs;
import java.util.*;

public class ListGraph<T> implements Graph<T> {
	/*
	 * Själva mappen som håller koll på alla noder
	 */
	private  Map<T, List<Edge<T>>> nodes = new HashMap<T, List<Edge<T>>>();

	/**
	 * Denna metod lägger till en ny stad i grafen. Om staden redan är tillagd
	 * så kommer ingenting att adderas.
	 * 
	 * @param ny
	 *            Staden som skall adderas
	 * */
	public void add(T ny) {
		/*
		 * Kontroll så att man inte lägger in en stad som redan finns
		 */
		if (!nodes.containsKey(ny)) {
			/*
			 * Skapar nytt set av en stad med en arraylist för dess edges
			 */
			nodes.put(ny, new ArrayList<Edge<T>>());
		}
	}

	/**
	 * Skapar en koppling mellan två städer. Detta används för
	 * 
	 * @param from
	 *            Staden man vill koppla ifrån
	 * @param to
	 *            Staden man vill koppla till
	 * @param n
	 *            Namnet på förbindelsen, t ex buss eller bil
	 * @param v
	 *            Vikten på förbindelsen, i detta fall resetiden som
	 *            förbindelsen kräver
	 * 
	 * */
	public void connect(T from, T to, String n, int v) {
		/*
		 * Hämtar ArrayList<Edge> från mappen till objektet
		 */
		List<Edge<T>> toList = nodes.get(to);
		List<Edge<T>> fromList = nodes.get(from);

		/*
		 * Kontrollerar att städerna finns inlagda i ListGraph
		 */
		if (fromList == null || toList == null) {
			throw new NoSuchElementException("Ingen stad adderad vid connect.");
		}

		/*
		 * Skapar edge-objekt för både till- och frånstaden
		 */
		Edge<T> eFrom = new Edge<T>(to, n, v);
		Edge<T> eTo = new Edge<T>(from, n, v);

		/*
		 * Här läggs en edge för vardera stad till i dess arrayList. Anledningen
		 * till att man adderar båda två är att grafen är oriktad, men kan skall
		 * alltså alltid kunna resa i båda riktningarna med en och samma
		 * koppling
		 */
		fromList.add(eFrom);
		toList.add(eTo);
	}

	/**
	 * En metod för att se vilka kopplingar en viss stad har.
	 * 
	 * @param std
	 *            Staden som man vill kontrollera kopplingar ifrån och till.
	 * @return Returnerar en ArrayList med stadens kopplingar
	 * */
	public List<Edge<T>> getEdgesFrom(T std) {
		return new ArrayList<Edge<T>>(nodes.get(std));
	}

	/**
	 * En metod för att ta reda på vilka direkta kopplingar som finns mellan två
	 * stycken städer. OBS att denna metdod endast returnerar edge-objekten från
	 * staden som anges som det första argumentet. Detta pga att det andra
	 * argumentets edge-objekt är likadana fast med motsatt riktining. Vill man
	 * ha dessa så får man anropa metoden igen fast med omvänd ordning på
	 * argumenten.
	 * 
	 * @param from
	 *            Den första av städerna man vill kolla kopplingar till
	 * @param to
	 *            Den andra staden som man vill kolla kopplingar till
	 * 
	 * @return Returnerar en Lista med alla edge-objekt som existerar mellan
	 *         stad "from" och staden "to". Obs att listan from returneras
	 *         endast fylls med edge-objekten från staden som skickas med som
	 *         det första argumentet.
	 * */
	public List<Edge<T>> getEdgesBetween(T from, T to) {
		/*
		 * Listan som kommer att returneras och fyllas med edge-objekten från
		 * staden "from" där destination är staden "to"
		 */
		List<Edge<T>> edgesBetween = new ArrayList<Edge<T>>();

		/*
		 * Kontrollerar att städerna som skickas med i argumenten finns inlagda
		 * i grafen
		 */
		if (nodes.get(from) == null || nodes.get(to) == null)
			throw new NoSuchElementException(
					"Kontrollera att städerna är adderade till grafen.");

		/*
		 * Hämtar staden "from"'s lista med noder.
		 * 
		 * Dessa gås sedan igenom var och en och dess destination matchas mot
		 * staden "to", om en träff uppstår så adderas edgen till edgesBetween.
		 */
		List<Edge<T>> fromToEdges = nodes.get(from);
		for (Edge<T> e : fromToEdges) {
			if (e.getDest() == to)
				edgesBetween.add(e);
		}

		return edgesBetween;
	}

	public Map<T, List<Edge<T>>> getNodes() {
		
		return new HashMap<T, List<Edge<T>>>(nodes);
	}
	
	
	public String toString() {
		String str = "";

		/*
		 * entry = hela setet (key+map), loopar alltså igenom hela nodes, och me
		 * blir en tempvariabel för varje set
		 */
		for (Map.Entry<T, List<Edge<T>>> me : nodes.entrySet()) {
			str += me.getKey() + ": " + me.getValue() + "\n";
		}

		return str;

	}


}
