
public class DjikHolder<T> {
	private int fastestTime;
	private boolean determinated;
	private T from;
	
	public DjikHolder(){
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
	

}
