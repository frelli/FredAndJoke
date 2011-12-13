import Graphs.Graph;
import Graphs.GraphMethods;
import Graphs.ListGraph;

//hej
//hej2 FRÅN JOAKIM!!!!!!!!!!!
public class Main
{
	public static void main(String[] args)
	{
		Graph<Stad> karta = new ListGraph<Stad>();

		Stad A = new Stad("A");
		karta.add(A);
		Stad B = new Stad("B");
		karta.add(B);
		Stad C = new Stad("C");
		karta.add(C);
		Stad D = new Stad("D");
		karta.add(D);
		Stad E = new Stad("E");
		karta.add(E);
		Stad F = new Stad("F");
		karta.add(F);
		Stad G = new Stad("G");
		karta.add(G);
		
		karta.connect(A, B, "Bil", 3);
		karta.connect(A, C, "Bil", 7);
		karta.connect(A, D, "Bil", 12);
		
		karta.connect(B, C, "Bil", 11);
		
		karta.connect(B, F, "Bil", 5);
		
		karta.connect(C, D, "Bil", 12);
		karta.connect(C, G, "Bil", 2);
		
		karta.connect(D, E, "Bil", 1);
		karta.connect(D, G, "Bil", 6);
		
		karta.connect(E, G, "Bil", 15);
		
		karta.connect(F, G, "Bil", 2);

		
		
//		System.out.println(karta.getEdgesBetween(husby, täby));
//		System.out.println(karta);
//		System.out.println(karta.getEdgesFrom(täby));
//		System.out.println(karta.getNodes());
		System.out.println(GraphMethods.shortestPath(karta, B, E));
//		System.out.println(GraphMethods.pathExists(karta, järfälla, husby));
	}
}
