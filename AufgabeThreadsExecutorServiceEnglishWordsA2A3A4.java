package aufgaben.concurrency;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import words.Words;

public class AufgabeThreadsExecutorServiceEnglishWordsA2A3A4 {

	public static void main(String[] args) throws Exception {
		
		List<String> words = Words.germanWords();

		AtomicLong targetTask1 = new AtomicLong();
		
		Runnable task1 = () -> {
			long count = words.stream()
					.filter(w -> w.indexOf('t')!=-1).count();
			// System.out.println("Wörter mit t: " + count);
			targetTask1.set(count);
		};
		
		Callable<Long> task2 = () -> {
			return words.stream()
					.filter(w -> w.contains("oo"))
					.count();
		};
		
		ExecutorService service = Executors.newCachedThreadPool();
		
		Future<?> future1 = service.submit(task1);
		Future<Long> future2 = service.submit(task2);

		future1.get(); // auf task1 warten
		System.out.println("Wörter mit 't': " + targetTask1.get());
		
		long count2 = future2.get();
		System.out.println("Wörter mit 'oo': " + count2);
		
		service.shutdown();
	}

}
