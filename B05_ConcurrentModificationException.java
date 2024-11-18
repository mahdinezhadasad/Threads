package concurrency;

import java.util.List;
import java.util.Vector;

public class B05_ConcurrentModificationException {

	/*
	 * ConcurrentModificationException ist nicht in der Prüfung.
	 *  
	 */
	public static void main(String[] args) {
		
		List<Integer> list = new Vector<>();
		
		list.add(12);
		list.add(13);
		list.add(14);

		/*
		 * ConcurrentModificationException kann geworfen werden,
		 * wenn man mit einem Iterator die Collection
		 * interiert, die währen dessen modifiziert wird.
		 */
		for(Integer x : list) {
			list.remove(0);
			System.out.println(x);
		}
		
	}

}
