package aufgaben.concurrency;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import words.Words;

class GetMaxTask extends RecursiveTask<String> {
	
	public static final int THRESHOLD = 1024;  
	
	private List<String> values;

	public GetMaxTask(List<String> values) {
		this.values = values;
	}
	
	@Override
	protected String compute() {
		/*
		 *  FALLS 
		 *  	die Aufgabe ist einfach genug
		 *  DANN
		 *  	die Aufgabe lösen und das Ergebnis zurück liefern
		 *  SONST
		 *  	- die Aufgabe in zwei einfachere Aufgaben teilen
		 *      - die Unteraufgabe B in einem anderen Thread aktivieren (fork)
		 *      - die Unteraufgabe A in dem aktuellen Thread aktivieren
		 *      - das Ergebnis der Unteraufgabe B im aktuellen Thread abwarten (join)
		 *      - die Ergebnisse A und B kombinieren und zurück liefern
		 */
		if(values.size() <= THRESHOLD) {
			
			return values.stream()
					.max(Comparator.naturalOrder())
					.get();
			
		} else {
			
			int indexMitte = values.size() / 2;
			
			List<String> leftList = values.subList(0, indexMitte);
			List<String> rightList = values.subList(indexMitte, values.size());
			
			GetMaxTask leftTask = new GetMaxTask(leftList);
			GetMaxTask rightTask = new GetMaxTask(rightList);
			
			rightTask.fork();
			String leftString = leftTask.compute();
			String rightString = rightTask.join();
			
			return leftString.compareTo(rightString) > 0 ? leftString : rightString;
		}

	}
}

public class AufgabeThreadsForkJoinMitListA3 {

	public static void main(String[] args) {

		List<String> values = Words.englishWords();
		Collections.shuffle(values);

		ForkJoinPool pool = new ForkJoinPool();
		String max = pool.invoke(new GetMaxTask(values));
		System.out.println("max: " + max); // zyzzyvas
		
	}

}
