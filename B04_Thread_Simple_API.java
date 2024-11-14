package threads;

public class B04_Thread_Simple_API {

	public static void main(String[] args) {
		
		Runnable task = () -> {};
		String name = "My Thread";
		
		/*
		 * Konstruktoren
		 */
		new Thread();
		new Thread(task);
		new Thread(name);
		new Thread(task, name);
		
		/*
		 * getName/setName
		 */
		Thread t1 = new Thread();
		System.out.println("t1.getName(): " + t1.getName());
		
		t1.setName("Tom");
		System.out.println("t1.getName(): " + t1.getName()); // Tom
		
		Thread t2 = new Thread("Jerry");
		System.out.println("t2.getName(): " + t2.getName()); // Jerry
		
		/*
		 * Java 8: 
		 * 
		 * long getId()
		 * 
		 *   Ab Java 10: threadId(); 
		 *  
		 */
		long id = t1.getId();
		System.out.println("t1.getId(): " + id);
		
		/*
		 * getPriority/setPriority
		 * 
		 * Es gibt keine Garantie, dass Prioritäten eine Wirkung zeigen
		 */
		int p1 = t1.getPriority();
//		t1.setPriority(Thread.MIN_PRIORITY);  // 1
		t1.setPriority(Thread.NORM_PRIORITY); // 5
//		t1.setPriority(Thread.MAX_PRIORITY);  // 10
		
		/*
		 * static Thread currentThread()
		 */
		Thread currentThread = Thread.currentThread();
		System.out.println("currentThread: " + currentThread.getName()); // main
		
		/*
		 * static void yield()
		 * 
		 * A hint to the scheduler that the current thread is willing 
		 * to yield its current use of a processor. 
		 * The scheduler is free to ignore this hint. 
		 */
		Thread.yield();
		
	}

}
