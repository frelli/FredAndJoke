//hej
//hej2 FRÅN JOAKIM!!!!!!!!!!!
public class Main
{
	public static void main(String[] args)
	{
		Graph<Stad> karta = new ListGraph<Stad>();

		Stad kista = new Stad("Kista");
		karta.add(kista);
		Stad järfälla = new Stad("Järfälla");
		karta.add(järfälla);
		Stad husby = new Stad("Husby");
		karta.add(husby);
		Stad akalla = new Stad("Akalla");
		karta.add(akalla);
		Stad hallonskogen = new Stad("Hallonskogen");
		karta.add(hallonskogen);
		Stad täby = new Stad("Täby");
		karta.add(täby);
		

		karta.connect(kista, täby, "Bil", 8);
		karta.connect(kista, hallonskogen, "Per fot", 15);
		
		karta.connect(järfälla, akalla, "Bil", 7);
		karta.connect(järfälla, husby, "Bil", 15);
		karta.connect(järfälla, täby, "Bil", 10);

		karta.connect(husby, täby, "Bil", 2);
		
		karta.connect(akalla, hallonskogen, "Bil", 1);

		karta.connect(hallonskogen, täby, "Bil", 19);
		
//		System.out.println(karta.getEdgesBetween(husby, täby));
//		System.out.println(karta);
		System.out.println(karta.getEdgesFrom(täby));
//		System.out.println(GraphMethods.pathExists(karta, järfälla, husby));
	}
}
