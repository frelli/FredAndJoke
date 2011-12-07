//hej
//hej2 FRÅN JOAKIM!!!!!!!!!!!
public class Main
{
	public static void main(String[] args)
	{
		ListGraph karta = new ListGraph();

		Stad s1 = new Stad("Kista");
		karta.add(s1);
		Stad s2 = new Stad("Järfälla");
		karta.add(s2);
		Stad s3 = new Stad("Viksjö");
		karta.add(s3);
		
		Stad s4 = new Stad("Akalla");
		karta.add(s4);

		karta.connect(s1, s2, "Bil", 12);
		karta.connect(s1, s2, "Per fot", 67);
		karta.connect(s1, s3, "Bil", 22);
		karta.connect(s1, s4, "Bil", 8);
		karta.connect(s2, s4, "Bil", 4);
		
		System.out.println(karta.getEdgesBetween(s1, s2));
//		System.out.println(karta);
//		System.out.println(karta.getEdgesFrom(s1));
//		System.out.println(GraphMethods.pathExists(karta, s2, s3));
	}
}
