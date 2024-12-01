package aufgaben.concurrency;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.DoubleSupplier;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class AufgabeThreadsExecutorServiceSimple {

	public static void main(String[] args) throws Exception {
//		a1();
//		a2();
		a3v1();
		a3v2();
	}
	
	static void a3v2() throws Exception {
		ExecutorService service = Executors.newSingleThreadExecutor();
		
		Callable<Integer> task = () -> {
			return ThreadLocalRandom.current().nextInt();
//			return 1;
		};
		
		Collection<Callable<Integer>> tasks = Stream.generate(() -> task)
				.limit(100)
				.collect(Collectors.toList());
		
		List<Future<Integer>> futures = service.invokeAll(tasks);
		
		int summe = 0;
		for(Future<Integer> f : futures) {
			summe += f.get();
		}
		System.out.println("summe = " + summe);
		
		service.shutdown();
	}
	
	static void a3v1() throws Exception {
		ExecutorService service = Executors.newSingleThreadExecutor();
		
		Callable<Integer> task = () -> {
			return ThreadLocalRandom.current().nextInt();
//			return 1;
		};
		
		List<Future<Integer>> futures =
				Stream.generate(() -> service.submit(task)) // Stream<Future<Integer>>
					.limit(100)
					.collect(Collectors.toList());
		
//		futures.stream()
//			.map(Future::get); // cf: get deklariert checked Exceptions
		
		int summe = 0;
		for(Future<Integer> f : futures) {
			summe += f.get();
		}
		System.out.println("summe = " + summe);
		
		service.shutdown();
	}

	static void a2() {
		ExecutorService service = Executors.newSingleThreadExecutor();
		
		Callable<Double> task = () -> {
			return ThreadLocalRandom.current()
					.doubles(10) // DoubleStream mit limit(10)
					.sum();
		};
		
		Future<Double> future = service.submit(task);
		
		service.shutdown();
		
		try {
			Double summe = future.get();
			System.out.println("summe: " + summe);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	static void a1() {
		ExecutorService service = Executors.newSingleThreadExecutor();
		
		Runnable task = () -> {
			ThreadLocalRandom rnd = ThreadLocalRandom.current();
			
			DoubleSupplier supp = () -> rnd.nextDouble();
			
			DoubleStream.generate(supp)
				.limit(10)
				.forEach(System.out::println);
		};
		
		service.execute(task);
		
		service.shutdown();
	}

}
