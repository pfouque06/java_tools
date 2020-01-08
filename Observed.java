package javaTools;

public interface Observed {
	public void addObserver(Observer obs);

	public void updateObserver();

	public void delObserver();
}
