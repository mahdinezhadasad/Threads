package Threads.src.threads;

class ThreadDerBeendetWerdenKann extends Thread {
	
	private volatile boolean laufen = true;
	
	@Override
	public void run() {
		while(laufen) {
			System.out.println(getName() + " läuft...");
			threads.MyThreadUtils.pause(2000);
		}
		System.out.println(getName() + " wurde beendet");
	}
	
	public void setLaufen(boolean laufen) {
		this.laufen = laufen;
	}
}

public class B08_ThreadBeenden {

	public static void main(String[] args) {

		ThreadDerBeendetWerdenKann th = new ThreadDerBeendetWerdenKann();
		System.out.println("main-Thread startet den Thread " + th.getName());
		th.start();
		
		System.out.println("main-Thread wartet...");
		threads.MyThreadUtils.pause(6000);
		
		System.out.println("main-Thread beendet den Thread " + th.getName());
		th.setLaufen(false);
		
	}

}