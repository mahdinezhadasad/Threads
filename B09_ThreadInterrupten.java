package Threads.src.threads;

class ThreadDerInterruptedWerdenKann extends Thread {
	@Override
	public void run() {
		while( !isInterrupted() ) {
			System.out.println(getName() + " läuft.. isInterrupted(): " + isInterrupted());
	
			/*
			 * Wenn der Thread sich im SLEEPING befindet,
			 * und ein anderer Thread diesen Thread interrupted,
			 * wirft die sleep die InterruptedException.
			 * 
			 * Achtung! Wenn sleep die InterruptedException wirft,
			 * wird der Zustand 'interrupted' in diesem Thread
			 * zurück auf false gesetzt.
			 * 
			 * Exam: in der Prüfung gibt es keine InterruptedException im Code
			 */
			try {
				Thread.sleep(2000);
				
			} catch (InterruptedException e) {
				System.out.println(getName() + " wurde interrupted");
				break; // Schleife beenden
			}
		} // end of while
		
		System.out.println(getName() + " wurde beendet");
	}
}

public class B09_ThreadInterrupten {

	public static void main(String[] args) {
		
		Thread th = new ThreadDerInterruptedWerdenKann();
		System.out.println("main-Thread startet den Thread " + th.getName());
		th.start();

		System.out.println("main-Thread macht Pause...");
		threads.MyThreadUtils.pause(6000);
		
		System.out.println("main-Thread ruft interrupt() mit dem Thread " + th.getName() + " auf");
		th.interrupt();
	}

}