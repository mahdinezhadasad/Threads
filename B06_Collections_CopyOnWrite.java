package concurrency;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class B06_Collections_CopyOnWrite {

	/*
	 * CopyOnWrite-Collections:
	 * 
	 * 	- threadsicher
	 *  - keine ConcurrentModificationException
	 *  - machen Sinn, wenn in der Anwendung mehrere Threads
	 *    auf die  Collection zugreifen und dabei sehr selten
	 *    die Collection ändern (geht langsam) und sehr häufig 
	 *    lesen (geht schnell).  
	 *  
	 */
	public static void main(String[] args) {

		Set<Integer> set = new CopyOnWriteArraySet<>(); // unwahrscheinlich in der Prüfung
		
		/*
		 * Achtung! 
		 * Bei jede Änderung wird intern ein neues Array erzeugt!
		 */
		List<Integer> list =  new CopyOnWriteArrayList<>();
		
		list.add(12); // intern neues Array: [12] 
		list.add(13); // intern neues Array: [12, 13] 
		list.add(14); // intern neues Array: [12, 13, 14] 
		
		list.remove(1); // intern neues Array: [12, 14]

		/*
		 * Achtung! 
		 * CopyOnWriteArrayList hat den Snapshot-Iterator.
		 */
		
		// Wenn die forEach startet, hat die CopyOnWriteArrayList
		// das Array: [12, 14]. Die forEach wird nur die Elemente finden.
		// Ausgaben: 12 14
		for(Integer x : list) {
			list.add(42);
			System.out.println(x);
		}
		
		System.out.println("Nach der forEach-Schliefe");
		System.out.println("list: " + list); // [12, 14, 42, 42]
		
	}

}
