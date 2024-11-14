package threads;

import java.util.ArrayList;
import java.util.List;

public class B12_Monitor_auswaehlen {

	public static void main(String[] args) throws InterruptedException {
		
		List<Integer> valuesA = new ArrayList<>();

//		Object monitor = new Object(); // korrekt, nicht empfohlen
//		Object monitor = "";           // korrekt, nicht empfohlen
		
		Object monitor = valuesA;
				
		Runnable taskA = () -> {
			for (int i = 0; i < 200_000; i++) {
				synchronized (monitor) {
					valuesA.add(i);
				}
			}
		};
		Runnable taskB = () -> {
			for (int i = 0; i < 300_000; i++) {
				synchronized(monitor) {
					valuesA.add(i);
				}
			}
		};

		/*
		 * tA und tB lesen und ändern die Liste valuesA.
		 * Die Liste kann auch als Monitor verwendet werden.
		 */
		Thread tA = new Thread(taskA);
		Thread tB = new Thread(taskB);
		
		tA.start();
		tB.start();
		
		List<Integer> listX = new ArrayList<>();
		
		Runnable task1 = () -> {
			synchronized (listX) {
				listX.add(12);
			}
		};
		
		/*
		 * t1 und t2 lesen und ändern die Liste listX.
		 * Die Liste kann auch als Monitor verwendet werden.
		 */
		Thread t1 = new Thread(task1);
		Thread t2 = new Thread(task1);
		
		t1.start();
		t2.start();
		
		tA.join();
		tB.join();
		
		System.out.println(valuesA.size()); // 200
		
	}

}
