package concurrency;

import java.util.concurrent.atomic.AtomicInteger;

class ValueHolder implements Runnable {
//	private int value;
	private AtomicInteger value = new AtomicInteger(); // threadsicher
	
	public void run() {
		for (int i = 0; i < 1_000_000; i++) {
//			value++; // Race Condition
			value.incrementAndGet(); // threadsicher
		}
	}
	
	public int getValue() {
		return value.get();
	}
}

public class B01_Atomic {

	public static void main(String[] args) throws InterruptedException {
		
		ValueHolder vh = new ValueHolder();
		
		Thread t1 = new Thread(vh);
		Thread t2 = new Thread(vh);
		
		t1.start();
		t2.start();
		
		t1.join();
		t2.join();
		
		System.out.println("vh.getValue(): " + vh.getValue()); // 2 Mio
		
		/*
		 * Exam: AtomicInteger oder AtomicLong
		 */
		
		/*
		 * AtomicInteger hat kein Autoboxing/Autounboxing 
		 */
//		AtomicInteger i1 = 12; // cf
		
		/*
		 * API
		 */
		System.out.println("\n*** API");
		
		AtomicInteger i2 = new AtomicInteger(); // value = 0
		AtomicInteger i3 = new AtomicInteger(42); // value = 42
		
		/*
		 * int get()
		 */
		AtomicInteger i4 = new AtomicInteger(10);
		
		System.out.println("1. i4.get(): " + i4.get()); // 10
		
		/*
		 * int incrementAndGet()
		 * int getAndIncrement()
		 * 
		 * int decrementAndGet()
		 * int getAndDecrement()
		 */
		int x = i4.incrementAndGet();
		System.out.println("2. i4: " + i4); // 11
		System.out.println("    x: " + x);  // 11
		
		x = i4.getAndIncrement();
		System.out.println("3. i4: " + i4); // 12
		System.out.println("    x: " + x); // 11
		
		/*
		 * int addAndGet(int delta)
		 * int getAndAdd(int delta)
		 */
		x = i4.addAndGet(5);
		System.out.println("4. i4: " + i4); // 17
		System.out.println("    x: " + x); // 17
		
		x = i4.getAndAdd(-3);
		System.out.println("5. i4: " + i4); // 14
		System.out.println("    x: " + x);  // 17
	
	}
}
