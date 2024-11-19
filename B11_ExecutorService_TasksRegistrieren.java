package concurrency;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class B11_ExecutorService_TasksRegistrieren {

	public static void main(String[] args) {
		
		ExecutorService service = Executors.newSingleThreadExecutor();

		/*
		 * 
		 * void execute(Runnable command)
		 * 
		 * 
		 */
		Runnable task1 = () -> {
			System.out.println("task 1 im Thread " + Thread.currentThread().getName());
		};
		service.execute(task1); // asynchron

		/*
		 * 
		 * <T> Future<T> submit(Callable<T> task)
		 * 
		 * weitere submit-Varianten werden überprüft in TestSubmit.java
		 */
		Callable<Void> task2 = () -> {
			System.out.println("task 2 im Thread " + Thread.currentThread().getName());
			return null;
		};
		Future<Void> future2 = service.submit(task2); // asynchron
		
		// Future fürs indirekte Kommunizieren mit dem Thread
		// des ExecutorServices verwenden:
		// Ein Future-Objekt bezieht sich auf seine Task! 
		Callable<Integer> task3 = () -> {
			System.out.println("task 3 erzeugt ein Integer im extra-Thread "
					+ "und liefert den Wert zurück...");
			return 42;
		};
		
		Future<Integer> future3 = service.submit(task3); // asynchron

		try {
			Integer resultOfCall = future3.get(); // synchron: main-Thread anhalten, 
												  // bis task3 fertig ist
			
			System.out.println("resultOfCall in dem main-Thread erhalten: " + 
					resultOfCall); // 42
			
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		
		/*
		 * 
		 * 
		 *  <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
                 throws InterruptedException
		 * 
		 * 
		 */
		Callable<Character> taskA = () -> {
			System.out.println("task A");
			return 'A';
		};
		Callable<Character> taskB = () -> {
			System.out.println("task B");
			return 'B';
		};
		
		Collection<Callable<Character>> tasks = Arrays.asList(taskA, taskB);
		
		try {
			List<Future<Character>> futures =
			   service.invokeAll(tasks); // synchron! main-Thread wird angehalten, bis
								  	  // alle Tasks aus der Collection fertig sind
			
			System.out.println("main: taskA und taskB sind bereits fertig");
			System.out.println("main: Ergebnisse aus den Futures: ");
			
			for(Future<Character> f : futures) {
				System.out.println(f.get());
			}
			
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		service.shutdown();
	}

}
