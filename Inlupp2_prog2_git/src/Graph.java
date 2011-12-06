
import java.util.List;

public interface Graph {
	public void add(Stad ny);
	public void connect(Stad from, Stad to, String n, int v);
	public List<Edge> getEdgesFrom(Stad std);
	public List<Edge> getEdgesBetween(Stad from, Stad to);
	
}
