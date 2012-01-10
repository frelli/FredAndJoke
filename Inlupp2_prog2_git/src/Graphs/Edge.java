package Graphs;
public class Edge<T> {
	private T dest;
	private String namn;
	private int vikt;
	private Edge<T> edgeBack; //Så att man vet vilken det motsvarande edge-objektet är hos dest

	public Edge(T dest, String namn, int vikt) {
		this.dest = dest;
		this.namn = namn;
		this.vikt = vikt;
	}

	public Edge<T> getEdgeBack() {
		return edgeBack;
	}

	public void setEdgeBack(Edge<T> edgeBack) {
		this.edgeBack = edgeBack;
	}

	public T getDest() {
		return dest;
	}
	
	public String getNamn() {
		return namn;
	}

	public void setNamn(String namn) {
		this.namn = namn;
	}
	
	public int getVikt() {
		return vikt;
	}

	public void setVikt(int vikt) {
		this.vikt = vikt;
	}

	public String toString() {
		return "Till " + dest + " med " + namn + ": " + vikt;
	}

}
