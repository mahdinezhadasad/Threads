package Threads.src.concurrency;

import java.util.*;

public class B03_Collections_normal {

	public static void main(String[] args) {

		/*
		 * Race Condition bei allen:
		 */
//		test(new ArrayList<>());
//		test(new LinkedList<>());
//		test(new HashSet<>());
//		test(new LinkedHashSet<>());
//		test(new TreeSet<>());
//		test(new PriorityQueue<>());
//		test(new ArrayDeque<>());

		/*
		 * Vector ist threadsicher
		 */
		test(new Vector<>());
	}
	
	static void test(Collection<Integer> coll) {
		
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