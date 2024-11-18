package concurrency;

import java.util.concurrent.ConcurrentSkipListSet;

public class B07_Collections_Concurrent {

	/*
	 * Concurrent:
	 * 
	 * 	- threadsicher
	 *  - keine ConcurrentModificationException
	 */
	public static void main(String[] args) {
		
		
		/*
		 * ConcurrentSkipListSet
		 * 
		 * 		- es ist ein Set
		 * 		- threadsicher
		 * 		- sortiert
		 */
		new ConcurrentSkipListSet<>();

	}

}
