package threads;

public class B11_Synchronisieren {
	
	static int count;

	/*
     * *** Eine Möglichkeit aus wenigen:
	 * 
	 * Thread A						    Thread B
	 * 
	 *               count(Heap) = 0
	 *               
	 *  erhält den Lock
	 *  
	 *  cpuA = 0
	 *  cpuA + 1 = 1
	 *  count = cpuA
	 *   
	 *  gibt den Lock frei
	 *  
	 *               count(Heap) = 1
	 * 
	 *                                  erhält den Lock
	 *                                  
	 *                                  cpuB = 1
	 *                                  cpuB + 1 = 2
	 *                                  count = cpuB
	 *                                  
	 *                                  gibt den Lock frei
	 *                                  
	 *               count(Heap) = 2
	 *               
	 *                
	 */
	public static void main(String[] args) throws InterruptedException {
		
		Object monitor = new Object();

		Runnable taskInc = () -> {
			for (int i = 0; i < 1_000_000; i++) {
				
				// Kritischer Block:
				synchronized (monitor) { 
					count++; 
				}				
			}
		};

		Thread tA = new Thread(taskInc);
		Thread tB = new Thread(taskInc);
		
		tA.start();
		tB.start();
		
		tA.join();
		tB.join();
		
		System.out.println("count: " + count); // 2000000
	}

}
