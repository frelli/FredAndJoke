//hej
//hej2 FR�N JOAKIM!!!!!!!!!!!
public class Main
{
	public static void main(String[] args)
	{
		Graph<Stad> karta = new ListGraph<Stad>();

		Stad kista = new Stad("Kista");
		karta.add(kista);
		Stad j�rf�lla = new Stad("J�rf�lla");
		karta.add(j�rf�lla);
		Stad husby = new Stad("Husby");
		karta.add(husby);
		Stad akalla = new Stad("Akalla");
		karta.add(akalla);
		Stad hallonskogen = new Stad("Hallonskogen");
		karta.add(hallonskogen);
		Stad t�by = new Stad("T�by");
		karta.add(t�by);
		

		karta.connect(kista, t�by, "Bil", 8);
		karta.connect(kista, hallonskogen, "Per fot", 15);
		
		karta.connect(j�rf�lla, akalla, "Bil", 7);
		karta.connect(j�rf�lla, husby, "Bil", 15);
		karta.connect(j�rf�lla, t�by, "Bil", 10);

		karta.connect(husby, t�by, "Bil", 2);
		
		karta.connect(akalla, hallonskogen, "Bil", 1);

		karta.connect(hallonskogen, t�by, "Bil", 19);
		
//		System.out.println(karta.getEdgesBetween(husby, t�by));
//		System.out.println(karta);
		System.out.println(karta.getEdgesFrom(t�by));
//		System.out.println(GraphMethods.pathExists(karta, j�rf�lla, husby));
	}
}
