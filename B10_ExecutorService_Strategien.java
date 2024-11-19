package concurrency;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class B10_ExecutorService_Strategien {

	public static void main(String[] args) {

		ExecutorService service;

		/*
		 * newSingleThreadExecutor:
		 * 
		 * 		- hat einen extra-Thread
		 * 		- wenn der Thread gerade beschäftigt ist, wird die
		 *        weitere registrierte Task in einer Warteschlange 
		 *        gespeichert.
		 */
		service = Executors.newSingleThreadExecutor();
		// tasksRegistrieren(service);
		
		/*
		 * newFixedThreadPool:
		 * 
		 * 		- hat n extra-Threads
		 * 		- wenn alle Threads gerade beschäftigt sind, wird die
		 *        weitere registrierte Task in einer Warteschlange 
		 *        gespeichert.
		 * 
		 */
		int nThreads = Runtime.getRuntime().availableProcessors(); // Bsp: Anzahl der CPUs
		service = Executors.newFixedThreadPool(nThreads);
		// tasksRegistrieren(service);

		/*
		 *  newCachedThreadPool
		 *  
		 *  	- erzeugt bei Bedarf einen neuen Thread,
		 *       wenn eine neue Task registriert wird und es 
		 *       keinen freien Thread im Thread-Pool gibt,
		 *       ansonsten wird für die neue registrierte Task
		 *       ein freier Thread aus dem Thread-Ool wiederverwendet.
		 *              
		 *  	- wenn ein Thread mit seiner Task fertig ist,
		 *        wird der Thread für eine bestimmte Zeil (60 Sek)
		 *        in dem Thread-Pool noch erhalten.
		 *        
		 *    aus Doku: "Threads that have not been used for sixty seconds 
		 *               are terminated and removed from the cache"
		 */
		service = Executors.newCachedThreadPool();
		// tasksRegistrieren(service);
		
	} // end of main
	
	static void tasksRegistrieren(ExecutorService service) {
		
		Set<String> setThreadNames = new ConcurrentSkipListSet<>();
		
		/*
		 * 100 Tasks dem ExecutorService übergeben,
		 * damit sie in den Threads des ExecutorServices
		 * ausgeführet werden
		 */
		for (int i = 0; i < 100; i++) {
			int taskNr = i;
			
			Runnable task = () -> {
				String threadName = Thread.currentThread().getName();
				System.out.println("Task " + taskNr + " in Thread " + threadName);
				
				setThreadNames.add(threadName); // später will ich wissen, welche Threads es gab
			};
			
			service.execute(task);
		}
		
		/*
		 * Das Serviceherunterfahren
		 */
		service.shutdown();
		
		try {
			// abwarten, dass die registreirten Tasks erledigt sind
			service.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("aktive Threads: " + setThreadNames.size());
	}

}
