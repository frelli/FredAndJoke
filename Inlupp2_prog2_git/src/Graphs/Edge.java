package Graphs;
public class Edge<T> {
	private T dest;
	private String namn;
	private int vikt;

	public Edge(T dest, String namn, int vikt) {
		this.dest = dest;
		this.namn = namn;
		this.vikt = vikt;
	}

	public T getDest() {
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
