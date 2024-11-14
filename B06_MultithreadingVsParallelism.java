package threads;

public class B06_MultithreadingVsParallelism {

	static class LogikA implements Runnable {
		int a;
		
		public void run() {
			a = 12;
		}
	}
	
	static class LogikB implements Runnable {
		int b;

		public void run() {
			b = 42;
		}
	}
	
	public static void main(String[] args) {
		
		LogikA vA = new LogikA();
		LogikB vB = new LogikB();
		
		/*
		 * Aufgaben sequentiell lösen
		 * 
		 *  	a = 12
		 *  	b = 42
		 */
//		vA.run();
//		vB.run();
		
		/*
		 * Aufgaben mit Threads lösen.
		 * 
		 * Variante 1. 
		 *  - Rechner hat nur eine CPU
		 * 
		 *     a = 12 
		 *     b = 42
		 *     
		 * Variante 2. 
		 *  - Rechner hat nur eine CPU
		 * 
		 *     b = 42
		 *     a = 12 
		 * 
		 * Variante 3. 
		 *  - Rechner hat mehrere CPUs
		 *     
		 *     CPU A           CPU B
		 *     a = 12
		 *     b = 42
		 *    
		 * Variante 4. 
		 *  - Rechner hat mehrere CPUs
		 *     
		 *     CPU A           CPU B
		 *     b = 42
		 *     a = 12
		 *    
		 * Variante 5. 
		 *  - Rechner hat mehrere CPUs
		 *     
		 *     CPU A           CPU B
		 *     b = 42
		 *                     a = 12
		 *    
		 * Variante 6. 
		 *  - Rechner hat mehrere CPUs
		 *     
		 *     CPU A           CPU B
		 *     a = 12
		 *                     b = 42
		 *    
		 * Variante 7. 						<- wirklich parallel
		 *  - Rechner hat mehrere CPUs
		 *     
		 *     CPU A           CPU B
		 *     a = 12          b = 42
		 *    
		 */
		new Thread(vA).start(); // Thread 1 
		new Thread(vB).start(); // Thread 2
		
	}
}
