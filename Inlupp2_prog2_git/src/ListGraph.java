import java.util.*;

public class ListGraph implements Graph {
	/*
	 * Sj�lva mappen som h�ller koll p� alla noder
	 */
	private Map<Stad, List<Edge>> nodes = new HashMap<Stad, List<Edge>>();

	/**
	 * Denna metod l�gger till en ny stad i grafen. Om staden redan �r tillagd
	 * s� kommer ingenting att adderas.
	 * 
	 * @param ny
	 *            Staden som skall adderas
	 * */
	public void add(Stad ny) {
		/*
		 * Kontroll s� att man inte l�gger in en stad som redan finns
		 */
		if (!nodes.containsKey(ny)) {
			/*
			 * Skapar nytt set av en stad med en arraylist f�r dess edges
			 */
			nodes.put(ny, new ArrayList<Edge>());
		}
	}

	/**
	 * Skapar en koppling mellan tv� st�der. Detta anv�nds f�r
	 * 
	 * @param from
	 *            Staden man vill koppla ifr�n
	 * @param to
	 *            Staden man vill koppla till
	 * @param n
	 *            Namnet p� f�rbindelsen, t ex buss eller bil
	 * @param v
	 *            Vikten p� f�rbindelsen, i detta fall resetiden som
	 *            f�rbindelsen kr�ver
	 * 
	 * */
	public void connect(Stad from, Stad to, String n, int v) {
		/*
		 * H�mtar ArrayList<Edge> fr�n mappen till objektet
		 */
		List<Edge> toList = nodes.get(to);
		List<Edge> fromList = nodes.get(from);

		/*
		 * Kontrollerar att st�derna finns inlagda i ListGraph
		 */
		if (fromList == null || toList == null) {
			throw new NoSuchElementException("Ingen stad adderad vid connect.");
		}

		/*
		 * Skapar edge-objekt f�r b�de till- och fr�nstaden
		 */
		Edge eFrom = new Edge(to, n, v);
		Edge eTo = new Edge(from, n, v);

		/*
		 * H�r l�ggs en edge f�r vardera stad till i dess arrayList. Anledningen
		 * till att man adderar b�da tv� �r att grafen �r oriktad, men kan skall
		 * allts� alltid kunna resa i b�da riktningarna med en och samma
		 * koppling
		 */
		fromList.add(eFrom);
		toList.add(eTo);
	}

	/**
	 * En metod f�r att se vilka kopplingar en viss stad har.
	 * 
	 * @param std
	 *            Staden som man vill kontrollera kopplingar ifr�n och till.
	 * @return Returnerar en ArrayList med stadens kopplingar
	 * */
	public List<Edge> getEdgesFrom(Stad std) {
		return new ArrayList<Edge>(nodes.get(std));
	}

	/**
	 * En metod f�r att ta reda p� vilka direkta kopplingar som finns mellan tv�
	 * stycken st�der. OBS att denna metdod endast returnerar edge-objekten fr�n
	 * staden som anges som det f�rsta argumentet. Detta pga att det andra
	 * argumentets edge-objekt �r likadana fast med motsatt riktining. Vill man
	 * ha dessa s� f�r man anropa metoden igen fast med omv�nd ordning p�
	 * argumenten.
	 * 
	 * @param from
	 *            Den f�rsta av st�derna man vill kolla kopplingar till
	 * @param to
	 *            Den andra staden som man vill kolla kopplingar till
	 * 
	 * @return Returnerar en Lista med alla edge-objekt som existerar mellan
	 *         stad "from" och staden "to". Obs att listan from returneras
	 *         endast fylls med edge-objekten fr�n staden som skickas med som
	 *         det f�rsta argumentet.
	 * */
	public List<Edge> getEdgesBetween(Stad from, Stad to) {
		/*
		 * Listan som kommer att returneras och fyllas med edge-objekten fr�n
		 * staden "from" d�r destination �r staden "to"
		 */
		List<Edge> edgesBetween = new ArrayList<Edge>();

		/*
		 * Kontrollerar att st�derna som skickas med i argumenten finns inlagda
		 * i grafen
		 */
		if (nodes.get(from) == null || nodes.get(to) == null)
			throw new NoSuchElementException(
					"Kontrollera att st�derna �r adderade till grafen.");

		/*
		 * H�mtar staden "from"'s lista med noder.
		 * 
		 * Dessa g�s sedan igenom var och en och dess destination matchas mot
		 * staden "to", om en tr�ff uppst�r s� adderas edgen till edgesBetween.
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
		 * entry = hela setet (key+map), loopar allts� igenom hela nodes, och me
		 * blir en tempvariabel f�r varje set
		 */
		for (Map.Entry<Stad, List<Edge>> me : nodes.entrySet()) {
			str += me.getKey() + ": " + me.getValue() + "\n";
		}

		return str;

	}
}
