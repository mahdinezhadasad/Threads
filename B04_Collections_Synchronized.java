package concurrency;

import java.util.*;

public class B04_Collections_Synchronized {

	public static void main(String[] args) {
		
		/*
		 * Vector ist wie ArrayList, aber threadsicher
		 */
		test(new Vector<>());

		/*
		 * synchronizedList, liefert threadsichere List zurück
		 */
		List<Integer> list1 = Collections.synchronizedList(new ArrayList<>());
		test(list1);
		
		List<Integer> list2 = Collections.synchronizedList(new LinkedList<>());
		test(list2);
		
		/*
		 * synchronizedSet, liefert threadsicheren Set zurück
		 */
		Set<Integer> set1 = Collections.synchronizedSet(new HashSet<>());
		test(set1);
		Set<Integer> set2 = Collections.synchronizedSet(new LinkedHashSet<>());
		test(set2);
		Set<Integer> set3 = Collections.synchronizedSet(new TreeSet<>());
		test(set3);
		
		/*
		 * synchronizedCollection, liefert threadsichere Collection zurück
		 */
		Collection<Integer> coll1 =
				Collections.synchronizedCollection(new PriorityQueue<>());
		test(coll1);
		
		Collection<Integer> coll2 =
				Collections.synchronizedCollection(new ArrayDeque<>());
		test(coll2);
		
	}

	static void test(Collection<Integer> coll) {
		System.out.println("*** " + coll.getClass().getSimpleName());
		
		Runnable task = () -> {
			for (int i = 0; i < 1_000; i++) {
				coll.add(i);
			}
		};
		
		Thread t0 = new Thread(task);
		Thread t1 = new Thread(task);
		
		t0.start();
		t1.start();
		
		try {
			t0.join();
			t1.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("size: " + coll.size());
	}
}
