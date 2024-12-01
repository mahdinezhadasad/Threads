package aufgaben.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import words.Words;

public class AufgabeThreadsExecutorServiceEnglishWordsA5A6A7 {
	
	public static void main(String[] args) throws Exception {
//		a5(); // Anzahl der Wörter der Länge 5: 6832
		
		List<String> words = Words.germanWords();
		
		int parts = 50;
		int subListSize = words.size() / parts;
//		System.out.println("words.size(): " + words.size()); // 1908815
		
		/*
		 *  Tasks mit Sublists erzeugen
		 */
		Collection<Callable<Long>> tasks = new ArrayList<>();
		
		for (int indexFrom = 0, count=0; indexFrom < words.size(); indexFrom+=subListSize) {
			int indexTo = indexFrom + subListSize;
			if(indexTo > words.size()) {
				indexTo = words.size();
			}
			
//			System.out.printf("%02d. from: %d, to: %d %n", count++, indexFrom, indexTo);
			
			List<String> subList = words.subList(indexFrom, indexTo);
			Callable<Long> task = () -> {
				return subList.stream().filter(w -> w.length()==5).count();
			};
			tasks.add(task);
		}
		
		/*
		 * Tasks parallel starten
		 */
		ExecutorService service = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		List<Future<Long>> futures = service.invokeAll(tasks);
		service.shutdown();
		
		long summe = 0;
		for(Future<Long> f : futures) {
			summe += f.get();
		}
		
		System.out.println("Anzahl Wörter der Länge 5: " + summe);
		
	} // end of main

	/*
	 * Ergebnis: 6832
	 */
	static void a5() {
		List<String> words = Words.germanWords();
		
		ExecutorService service = Executors.newSingleThreadExecutor();
		Runnable task = () -> {
			long count = words.stream().filter(w -> w.length()==5).count();
			System.out.println("Wörter mit der Länge 5: " + count);
		};
		service.execute(task);
		service.shutdown();
	}

}
