package concurrency;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class B13_ExecutorService_ErgebnisseAbwarten {
	
	public static void main(String[] args) throws Exception {
//		bsp1();
//		bsp2();
		bsp3();
	}
	
	/*
	 * Anhalten bis mehrere Tasks fertig sind
	 */
	static void bsp3() throws Exception {
		ExecutorService service = Executors.newCachedThreadPool();
		
		Callable<Integer> t1 = () -> {
			System.out.println("task 1");
			return 111;
		}; 
		Callable<Integer> t2 = () -> {
			System.out.println("task 2");
			return 222;
		}; 
		
		Collection<Callable<Integer>> tasks = Arrays.asList(t1, t2);
		
		List<Future<Integer>> futures = 
				service.invokeAll(tasks); // main anhalten bis die Tasks aus der 
								  		  // Collection fertig sind
		/*
		 * erst danach geht es weiter.
		 * Folgende Ausgaben sind garantiert:
		 * 
		 * true 111
		 * true 222
		 */
		for(Future<Integer> f : futures) {
			System.out.println(f.isDone() + // garantiert true
					" " + f.get());
		}
		
		service.shutdown();
	}

	/*
	 * Anhalten bis eine Task fertig ist
	 */
	static void bsp2() throws Exception {
		ExecutorService service = Executors.newCachedThreadPool();
		
		Callable<String> task = () -> {
			System.out.println("task");
			return "mo";
		};
		
		Future<String> future = service.submit(task);
		
		String result = future.get(); // anhalten, bis die Task fertig ist
		System.out.println(result); // mo
		
		service.shutdown();
	}
	
	/*
	 * Anhalten, bis alle Threads vom ExecutorService beendet sind
	 */
	static void bsp1() {

		ExecutorService service = Executors.newCachedThreadPool();
		
		service.execute(() -> System.out.println("task 1"));
		service.execute(() -> System.out.println("task 2"));
		//...
		
		service.shutdown(); // muss sein vor awaitTermination, sonst werden 
							// die Threads nicht beendet
		
		try {
			/*
			 * Den main-Thread maximal 1 Tag anhalten.
			 * Wenn die Threads vom Service beendet wurden, geht die main weiter. 
			 */
			service.awaitTermination(1, TimeUnit.DAYS);
			System.out.println("main nach awaitTermination");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
