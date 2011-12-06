import java.util.*;

public class ListGraph implements Graph {
	/*
	 * Själva mappen som håller koll på alla noder
	 */
	private Map<Stad, List<Edge>> nodes = new HashMap<Stad, List<Edge>>();

	/**
	 * Denna metod lägger till en ny stad i grafen. Om staden redan är tillagd
	 * så kommer ingenting att adderas.
	 * 
	 * @param ny
	 *            Staden som skall adderas
	 * */
	public void add(Stad ny) {
		/*
		 * Kontroll så att man inte lägger in en stad som redan finns
		 */
		if (!nodes.containsKey(ny)) {
			/*
			 * Skapar nytt set av en stad med en arraylist för dess edges
			 */
			nodes.put(ny, new ArrayList<Edge>());
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
	public void connect(Stad from, Stad to, String n, int v) {
		/*
		 * Hämtar ArrayList<Edge> från mappen till objektet
		 */
		List<Edge> toList = nodes.get(to);
		List<Edge> fromList = nodes.get(from);

		/*
		 * Kontrollerar att städerna finns inlagda i ListGraph
		 */
		if (fromList == null || toList == null) {
			throw new NoSuchElementException("Ingen stad adderad vid connect.");
		}

		/*
		 * Skapar edge-objekt för både till- och frånstaden
		 */
		Edge eFrom = new Edge(to, n, v);
		Edge eTo = new Edge(from, n, v);

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
	public List<Edge> getEdgesFrom(Stad std) {
		return new ArrayList<Edge>(nodes.get(std));
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
	public List<Edge> getEdgesBetween(Stad from, Stad to) {
		/*
		 * Listan som kommer att returneras och fyllas med edge-objekten från
		 * staden "from" där destination är staden "to"
		 */
		List<Edge> edgesBetween = new ArrayList<Edge>();

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
		List<Edge> fromToEdges = nodes.get(from);
		for (Edge e : fromToEdges) {
			if (e.getDest() == to)
				edgesBetween.add(e);
		}

		return edgesBetween;
	}

	public String toString() {
		String str = "";

		/*
		 * entry = hela setet (key+map), loopar alltså igenom hela nodes, och me
		 * blir en tempvariabel för varje set
		 */
		for (Map.Entry<Stad, List<Edge>> me : nodes.entrySet()) {
			str += me.getKey() + ": " + me.getValue() + "\n";
		}

		return str;

	}
}
