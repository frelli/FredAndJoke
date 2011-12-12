/*
 * En klass som har till uppgift att h�lla variabler till T-objekt n�r 
 * man vill r�kna ut den snabbasta v�gen mellan tv� noder i en graph.
 * */
public class DjikHolder<T> {
	/* 

	 */
	private int fastestTime; //	Snabbasta tiden fr�n startnoden till noden som tillh�r detta DjikHolder-objekt
	private boolean determinated; // Avg�r om man har r�knat f�rdigt p� objektets tider 
	private T from; //Fr�n vilket T-objekt som ligger f�re det aktuella objektet i listan av den snabbaste v�gen 
	
	/*
	 * S�tter fastestTime till ett h�gt v�rde f�r att 
	 * alla nya v�rden som s�tts skall vara mindre �n det.
	 * S�tter �ven determinated till false s� att man vet
	 * att det objektet inte �r bearbetat
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
