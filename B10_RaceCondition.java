package threads;

public class B10_RaceCondition {
	
	static volatile int count;

	/*
	 * Race Condition: Zwei (oder mehr) Threads zugreifen parallel
	 * 				   auf dieselbe Speicherstelle lesend und schreibend.
	 *                 
	 * Das ist ein Problem. Das Problem muss gelöst werden!
	 * Lösung: Synchronisieren (wird in weiteren Beispielen gezeigt)
	 * 
	 * Was hier passiert.
	 * 
	 * Inkrementierung:    
	 *          1. Lesen vom aktuellen Wert (count kopieren in CPU)
	 *          2. Kopierten Wert um 1 in der CPU erhöhen
	 *          3. Den berechneten Wert aus CPU in count kopieren
	 * 
	 * *** Eine Möglichkeit aus vielen:
	 * 
	 * Thread A						    Thread B
	 * 
	 *               count(Heap) = 0
	 *               
	 *  cpuA = 0
	 *  cpuA + 1 = 1
	 *  count = cpuA
	 *   
	 *               count(Heap) = 1
	 * 
	 *                                  cpuB = 1
	 *                                  cpuB + 1 = 2
	 *                                  count = cpuB
	 *                                  
	 *               count(Heap) = 2
	 *               
	 *               
	 * *** Eine andere Möglichkeit aus vielen:
	 * 
	 * Thread A						    Thread B
	 * 
	 *               count(Heap) = 0
	 *               
	 *  cpuA = 0                       cpuB = 0
	 *  cpuA + 1 = 1                   cpuB + 1 = 1
	 *  count = cpuA
	 *                                 count = cpuB
	 *   
	 *               count(Heap) = 1
	 * 
	 * *** Eine andere Möglichkeit aus vielen:
	 * 
	 * Thread A						    Thread B
	 * 
	 *               count(Heap) = 0
	 *               
	 *                                 cpuB = 0
	 *  cpuA = 0                       
	 *  							   cpuB + 1 = 1
	 *  cpuA + 1 = 1                   
	 *  count = cpuA
	 *                                 count = cpuB
	 *   
	 *               count(Heap) = 1
	 * 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		Runnable taskInc = () -> {
			for (int i = 0; i < 1_000_000; i++) {
				// more code here...
				count++;
			}
			System.out.println(Thread.currentThread().getName() + " ist fertig");
		};

		Thread tA = new Thread(taskInc);
		Thread tB = new Thread(taskInc);
		
		tA.start();
		tB.start();
		
		tA.join();
		tB.join();
		
		System.out.println("count: " + count); // ???
	}

}
