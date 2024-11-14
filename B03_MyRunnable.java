package threads;

class MyRunnable implements Runnable {
	@Override
	public void run() {
		System.out.println("run");
	}
}

public class B03_MyRunnable {

	public static void main(String[] args) {
		
		Runnable task = new MyRunnable();

		// 1. Reservieren (NEW)
		Thread th = new Thread(task);
		
		// 2. Als RUNNABLE registrieren
		th.start();
		
		/*
		 * Wenn der Scheduler den extra-Thread in den Zustand RUNNABLE 
		 * versetzt, wird die Methode run() in diesem Thread ausgeführt:
		 * 
		 * public void run() {
		 *   Runnable task = hier die Task suchen...
		 *   if (task != null) {
         *      hier die task aktivieren: task.run()
		 *   }
		 * }
		 * 
		 */
		
		System.out.println("main");
	}

}
