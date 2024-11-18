package concurrency;

import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class B08_Collections_Blocking {

	/*
	 * Blocking:
	 * 
	 * 	- threadsicher
	 *  - keine ConcurrentModificationException
	 *  
	 */
	public static void main(String[] args) {

		int fixedSize = 3;
		
		/*
		 * Konstruktoren
		 */
		new LinkedBlockingQueue<>(); 			// max size: unbegrenzt
		new LinkedBlockingQueue<>(fixedSize);	// max size: 3
		
		// new ArrayBlockingQueue<>();			// cf
		new ArrayBlockingQueue<>(fixedSize);	// max size: 3
		
		/*
		 * Methoden
		 */
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(fixedSize);		

		/*
		 * add, offer, put
		 */
		queue.add(12);
		queue.offer(13);
		try {
			queue.put(14);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("1. queue: " + queue); // [12, 13, 14]
		
		try {
			queue.add(15);
		} catch (IllegalStateException e) {
			System.out.println("2. Exception! Schlange ist voll");
		}
		
		boolean result = queue.offer(16);
		System.out.println("3. result: " + result); // false
		
		System.out.println("4. queue: " + queue); // [12, 13, 14]
		
		/*
		 * void put(E e) throws InterruptedException
		 */
		new Thread(() -> {
			kurzePause(3000);
			System.out.println("6. extra-Thread entfernt ein Element: " + 
					queue.poll()); // 12
		}).start();
		
		try {
			System.out.println("5. main-Thread ruft queue.put(17) auf...");
			queue.put(17); // main wird angehalten, bis es Platz gibt
			System.out.println("7. queue: " + queue); // [13, 14, 17]
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		/*
		 * remove, poll, take
		 */
		
		Integer x = queue.remove();
		System.out.println("remove(): " + x); // 13
		
		x = queue.poll();
		System.out.println("poll(): " + x); // 14

		try {
			x = queue.take();
			System.out.println("take(): " + x); // 17
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("8. queue: " + queue); // []
		
		try {
			queue.remove();
		} catch (NoSuchElementException e) {
			System.out.println("9. Exception! Die Schlange ist leer");
		}
		
		System.out.println("10. peek(): " + queue.peek()); // null
		
		/*
		 * E take() throws InterruptedException
		 */
		new Thread(() -> {
			kurzePause(3000);
			System.out.println("12. extra-Thread speichert 18 in der Schlange");
			queue.offer(18);
			
		}).start();
		
		try {
			System.out.println("11. main-Thread ruft queue.take() auf...");
			x = queue.take();
			System.out.println("13. main bekommt das Element aus take: " + x); // 18
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("14. queue: " + queue); // []
		
		/*
		 * boolean offer(E e,
              long timeout,
              TimeUnit unit)
       		throws InterruptedException
       		
       		- wenn es in der Queue Platz gibt, funktioniert die Methode 
       		  wie die 'normale' offer
       		  
       		- wenn die Queue voll ist, wird die Methode (wie put)
       		  den Thread anhalten, bis es den Platz gibt, 
       		  aber nicht länger als timeout.
		 */
		
		
		/*
		 * E poll(long timeout,
       			  TimeUnit unit)
              throws InterruptedException
              
            - wenn die Queue nicht leer ist, funktioniert die Methode
              wie die 'normale' poll.
              
            - wenn die Queue leer ist, wird die Methode (wie take)
              den Thread anhalten, bis in der Queue ein Element erscheint,
              aber nicht länger als timeout.
		 */
		
	} // end of main
	
	static void kurzePause(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
