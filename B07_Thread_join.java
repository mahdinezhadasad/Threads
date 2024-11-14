package threads;

public class B07_Thread_join {

	/*
	 * volatile ist nicht in der Prüfung.
	 * 
	 * volatile erklärt: https://jenkov.com/tutorials/java-concurrency/volatile.html
	 */
	volatile static int count;
	
	public static void main(String[] args) {

		Runnable task = () -> {
			for (int i = 0; i < 1_000_000; i++) {
				count++;
			}
		};
		
		Thread th = new Thread(task);
		th.start();
		
		/*
		 * void join() throws InterruptedException
		 * 
		 * 
		 * Der Thread 1, in dem die join() aufgerufen wird, wird 
		 * angehalten, bis der Thread 2, zu dem die join() aufgerufen wurde,
		 * beendet wurde.
		 *  - Wenn der Thread 2 NEW ist, wird Thread 1 nicht angehalten
		 *  - Wenn der Thread 2 bereits DEAD ist, wird Thread 1 nicht angehalten
		 * 
		 * Hier: Der main-Thread wird angehalten bis der Thread th fertig ist. 
		 * 
		 * 
		 * Weitere überladene Varianten:
		 * 
		 * 		th.join(millis)
		 * 		th.join(millis, nanos)
		 * 
		 */
		try {
			th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("count: " + count); // 1.000.000
	}

}
