package ch.hearc.meteo.imp.afficheur.simulateur.vue;

import java.util.Observable;

public class ObservableValue extends Observable {
	private String n = null;

	public ObservableValue(String n) {
		this.n = n;
	}

	public void setValue(String n) {
		this.n = n;
		setChanged();
		notifyObservers();
	}

	public String getValue() {
		return n;
	}
}