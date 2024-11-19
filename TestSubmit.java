package concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestSubmit {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		ExecutorService service = Executors.newFixedThreadPool(4);
		
		/*
		 * execute
		 */
		Runnable task1 = () -> System.out.println("task 1"); 
		service.execute(task1);

		/*
		 * <T> Future<T> submit(Callable<T> task)
		 */
		Callable<Integer> task2 = () -> {
			System.out.println("task 2");
			int summe = 0;
			for (int i = 1; i <= 100; i++) {
				summe+=i;
			}
			return summe;
		};
		
		Future<Integer> future2 = service.submit(task2);
		
		System.out.println("main. Ergebnis aus task2: " + future2.get());
		
		/*
		 * Future<?> submit(Runnable task)
		 * 
		 */
		StringBuilder targetForTask3 = new StringBuilder();
		
		Runnable task3 = () -> {
			System.out.println("task 3");
			
			for (char i = 'a'; i <= 'z'; i++) {
				targetForTask3.append(i);
			}
		};
		Future<?> future3 = service.submit(task3);
		future3.get(); // warten bis task3 fertig ist 
		System.out.println("main. task3 hat das Ergebnis im StringBuilder gespeichert: " + 
				targetForTask3);
		
		
		/*
		 * <T> Future<T> submit(Runnable task, T result)
		 */
		StringBuilder targetForTask4 = new StringBuilder();
		
		Runnable task4 = () -> {
			System.out.println("task 4");
			
			for (char i = 'A'; i <= 'Z'; i++) {
				targetForTask3.append(i);
			}
		}; 
		
		Future<StringBuilder> future4 = service.submit(task4, targetForTask4);
		System.out.println("main. Ergebnis aus task4: " + future4.get());
		
		service.shutdown();

	}

}
