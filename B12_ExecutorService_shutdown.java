package concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class B12_ExecutorService_shutdown {
	/*
	 * Exam!
	 * 
	 * newSingleThreadExecutor und newFixedThreadPool beenden 
	 * ihre Threads nicht, wenn es keine shutdown/shutdownNow gibt.
	 * Die Anwendung wird nicht beendet, auch wenn alle registrierten 
	 * Tasks fertig sind.
	 */
	public static void main(String[] args) {
		test_shutdown();
//		test_shutdownNow();
	}

	static void test_shutdown() {
		System.out.println("*** test shutdown");
		
		ExecutorService service = Executors.newSingleThreadExecutor();
		
		Callable<Void> task = () -> {
			System.out.println("task VOR sleep");
			Thread.sleep(2000);
			System.out.println("task NACH sleep");
			return null;
		};
		
		System.out.println("* main registriert die Task");
		service.submit(task);
		service.submit(task);

		System.out.println("* main ruft shutdown auf");

		service.shutdown(); // - alle bereits registrierten Tasks werden garantiert 
							//   aktiviert (erden gestartet, auch wenn es noch nicht 
		                    //   geschehen ist)
						    // - keine weiteren Tasks können danach registriert werden
		
		try {
			service.execute(() -> {});
		} catch (Exception e) {
			System.out.println("* Exception in main. "
					+ "Keine weiteren Tasks können registriert werden");
		}
		
		
		System.out.println("* main ist vorbei");
	}
	
	
	static void test_shutdownNow() {
		System.out.println("*** test shutdownNow");
		
		ExecutorService service = Executors.newSingleThreadExecutor();
		
		Callable<Void> task = () -> {
			System.out.println("task VOR sleep");
			Thread.sleep(2000); // programmiert auf InterruptedException
			System.out.println("task NACH sleep");
			return null;
		};
		
		System.out.println("* main registriert die Task");
		service.submit(task);
		service.submit(task);
		service.submit(task);
		service.submit(task);
		
		System.out.println("* main ruft shutdown auf");

		service.shutdownNow(); // - alle laufenden (aktivierten) Tasks versucht das Service 
		                       //   abzubrechen (mit InterruptedException-Technik)
							   // - die registrierten aber noch nicht aktivierten Tasks 
		                       //   werden verworfen
						       // - keine weiteren Tasks können danach registriert werden
		
	}

}
