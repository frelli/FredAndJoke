public class Edge {
	private Stad dest;
	private String namn;
	private int vikt;

	public Edge(Stad dest, String namn, int vikt) {
		this.dest = dest;
		this.namn = namn;
		this.vikt = vikt;
	}

	public Stad getDest() {
		return dest;
	}

	public String getNamn() {
		return namn;
	}

	public int getVikt() {
		return vikt;
	}

	public String toString() {
		return "Till " + dest + " med " + namn + ": " + vikt;
	}

}
