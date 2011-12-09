
import java.util.List;
import java.util.Map;

public interface Graph <T>{
	public void add(T ny);
	public void connect(T from, T to, String n, int v);
	public Map<T, Edge<T>> getNodes(T obj);
	public List<Edge<T>> getEdgesFrom(T obj);
	public List<Edge<T>> getEdgesBetween(T from, T to);
//	public toString();
}
