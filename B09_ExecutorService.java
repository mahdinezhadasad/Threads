package concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class B09_ExecutorService {

	/*
	 * ExecutorService: Thread-Manager. Verwaltet die Threads, 
	 * die unsere Tasks ausführen.  
	 */
	public static void main(String[] args) {

		/*
		 * 1. Strategie der Threadverwaltung auswählen
		 */
		ExecutorService service = Executors.newSingleThreadExecutor();
		
		/*
		 * 2. Mit dem ExecutorService arbeiten: die Tasks zum 
		 *    Ausführen in den Threads des ExecutorServices 
		 *    übergeben (registrieren)
		 */
		
		Runnable task = () -> System.out.println("my task");
		service.execute(task);
		//weitere Tasks registrieren...
		
		/*
		 * 3. ExecutorService herunterfahren
		 */
		service.shutdown();
		
		System.out.println("end of main");
	}

}
