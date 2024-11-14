package threads;

public class B05_Thread_sleep {

	/*
	 *  static void sleep(long millis) throws InterruptedException 
	 *  
	 *  static void sleep(long millis, int nanos) throws InterruptedException 
	 *  
	 */
	public static void main(String[] args) {
		
		System.out.println("main 1");
		
		try {
			Thread.sleep(2000); 
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("main 2");
	}

}
