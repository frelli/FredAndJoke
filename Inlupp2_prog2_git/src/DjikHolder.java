/*
 * En klass som har till uppgift att hålla variabler till T-objekt när 
 * man vill räkna ut den snabbasta vägen mellan två noder i en graph.
 * */
public class DjikHolder<T> {
	/* 

	 */
	private int fastestTime; //	Snabbasta tiden från startnoden till noden som tillhör detta DjikHolder-objekt
	private boolean determinated; // Avgör om man har räknat färdigt på objektets tider 
	private T from; //Från vilket T-objekt som ligger före det aktuella objektet i listan av den snabbaste vägen 
	
	/*
	 * Sätter fastestTime till ett högt värde för att 
	 * alla nya värden som sätts skall vara mindre än det.
	 * Sätter även determinated till false så att man vet
	 * att det objektet inte är bearbetat
	 */
	public DjikHolder() {
		setFastestTime(Integer.MAX_VALUE);
		setDeterminated(false);
	}

	public int getFastestTime() {
		return fastestTime;
	}

	public void setFastestTime(int fastestTime) {
		this.fastestTime = fastestTime;
	}

	public boolean isDeterminated() {
		return determinated;
	}

	public void setDeterminated(boolean determinated) {
		this.determinated = determinated;
	}

	public T getFrom() {
		return from;
	}

	public void setFrom(T from) {
		this.from = from;
	}

	public String toString() {
		return "Tid: " + fastestTime + " det: " + determinated;
	}

}
