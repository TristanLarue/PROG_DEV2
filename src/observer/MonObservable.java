package observer;

/**
 * Classe abstraite du patron Observable
 * @author Fred Simard | ETS
 * @version H2025
 */

import java.util.ArrayList;

public abstract class MonObservable {

	// liste des observers
	ArrayList<MonObserver> observers = new ArrayList<MonObserver>();
	
	/**
	 * m�thode pour attacher un Observer
	 * @param observer
	 */
	public void attacherObserver(MonObserver observer){
		observers.add(observer);
	}
	
	/**
	 * m�thode pour avertir tous les observers
	 */
	public void avertirLesObservers(){
		for(MonObserver observer:observers){
			observer.avertir();
		}
	}
		
}
