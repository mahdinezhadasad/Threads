package threads;

class ValueHolder {
	private int value;
	
	/*
	 * Die Lösung ist gut:
	 */
//	void inc() {
//		synchronized (this) {
//			value++;
//		}
//	}
	
	/*
	 * Dieselbe Lösung kompakter:
	 * 
	 * Wenn eine Methode synchronized ist, wird der Rumpf der 
	 * Methode mit this synchroniziert
	 */
	synchronized void inc() {
		value++;
	}
	
	public int getValue() {
		return value;
	}
}

public class B14_Instanzmethode_synchronizieren {

	public static void main(String[] args) throws InterruptedException {
		
		ValueHolder vh1 = new ValueHolder();
		ValueHolder vh2 = new ValueHolder();
		
		Runnable taskA = () -> {
			for (int i = 0; i < 1_000_000; i++) {
				vh1.inc();
			}
		};
		
		/*
		 * t1 und t2 ändern value aus vh1
		 */
		Thread t1 = new Thread(taskA);
		Thread t2 = new Thread(taskA);
		
		Runnable taskB = () -> {
			for (int i = 0; i < 1_000_000; i++) {
				vh2.inc();
			}
		};
		
		/*
		 * t3 ändert value aus vh2
		 */
		Thread t3 = new Thread(taskB);

		t1.start();
		t2.start();
		t3.start();
		
		t1.join();
		t2.join();
		t3.join();
		
		System.out.println("vh1.getValue(): " + vh1.getValue()); 
		System.out.println("vh2.getValue(): " + vh2.getValue()); 
	}

}
