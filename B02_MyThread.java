package threads;

class MyThread extends Thread {
	@Override
	public void run() {
		System.out.println("run");
	}
}

public class B02_MyThread {

	public static void main(String[] args) {

		// 1. Reservieren (NEW)
		Thread th = new MyThread();
		
		// 2. Als RUNNABLE registrieren
		th.start(); // ab hier existierten zwei Threads (main und der extra-Thread)
		
		System.out.println("main");
	}

}
