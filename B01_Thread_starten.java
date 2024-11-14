package threads;

public class B01_Thread_starten {

	public static void main(String[] args) {

		/*
		 * Einen neuen Thread (Zustand: NEW) reservieren.
		 */
		Thread th = new Thread();
		
		/*
		 * Den Thread beim Scheduler als RUNNABLE registrieren:
		 */
		th.start();

		/*
		 * Wenn der Scheduler den extra-Thread in den Zustand RUNNABLE 
		 * versetzt, wird die Methode run() in diesem Thread ausgeführt:
		 * 
		 * public void run() {
		 *   Runnable task = hier die Task suchen...
		 *   if (task != null) {
         *      hier die task aktivieren...
		 *   }
		 * }
		 * 
		 */
	}

}
