package threads;


class Deadlock implements Runnable {
	
	static Object m1 = new Object();
	static Object m2 = new Object();
	
	@Override
	public void run() {
		
		synchronized (m1) {
			System.out.println("m1");
			
			// Thread 2 kommt hier an und hat den Lock auf m1
			synchronized (m2) {
				System.out.println("m1.m2");
			}
		}

		synchronized (m2) {
			System.out.println("m2");
			
			// Thread 1 kommt hier an und hat den Lock auf m2
			synchronized (m1) {
				System.out.println("m2.m1");
			}
		}
	}
}


public class B16_Deadlock {

	/*
	 * Achtung! Deadlock darf die Anwendung nicht haben.
	 * Wenn Deadlock ptentiell möglich ist,
	 * soll der Code der Anwendung geändert werden! 
	 */
	public static void main(String[] args) {

		Runnable task = new Deadlock();
		
		new Thread(task).start();
		new Thread(task).start();

	}

}
