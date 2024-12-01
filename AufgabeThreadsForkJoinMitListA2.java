package aufgaben.concurrency;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicInteger;

import words.Words;

class ToUpperCaseAction extends RecursiveAction {
	
	public static final int THRESHOLD = 1024;
	private List<String> values;
	
	public ToUpperCaseAction(List<String> values) {
		this.values = values;
	}

	public void compute() {
		/*
		 *  FALLS 
		 *  	die Aufgabe ist einfach genug
		 *  DANN
		 *  	die Aufgabe lösen
		 *  SONST
		 *  	- die Aufgabe in zwei (oder mehr) einfachere Aufgaben teilen
		 *      - die einfache Teilaufgaben an die Threads des ForkJoin-Pools
		 *        zum Ausführen übergeben  
		 */
		if(values.size() <= THRESHOLD) {
			for (int i = 0; i < values.size(); i++) {
				String s = values.get(i).toUpperCase();
				values.set(i, s);
			}
			
		} else {
			int indexMitte = values.size() / 2;
			
			List<String> leftList = values.subList(0, indexMitte);
			List<String> leftRight = values.subList(indexMitte, values.size());
			
			ToUpperCaseAction leftAction = new ToUpperCaseAction(leftList);
			ToUpperCaseAction rightAction = new ToUpperCaseAction(leftRight);
			
			invokeAll(leftAction, rightAction);
		}
	}
}

public class AufgabeThreadsForkJoinMitListA2 {

	public static void main(String[] args) {

		List<String> words = Words.englishWords();

		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(new ToUpperCaseAction(words));
		
//		words.stream()
//			.skip(150000)
//			.limit(1)
//			.forEach(System.out::println);

		AtomicInteger count = new AtomicInteger();
		words.stream()
			.filter(s -> count.incrementAndGet()%10000==0)
			.forEach(System.out::println);

	}

}
