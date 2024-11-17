package Threads.src.threads;

import java.time.LocalTime;

class Producer implements Runnable {
	
	private String data;
	
	@Override
	public void run() {
		while(true) {
			System.out.println("Producer produziert...");
			threads.MyThreadUtils.pause(2000);
			
			synchronized (this) {
				data = "Neue Daten von " + LocalTime.now();
				this.notifyAll();
			}
		
		}
	}
	
	boolean hasData() {
		return data!=null;
	}
	
	String getData() {
		String tmp = data;
		data = null;
		return tmp;
	}
}

class Consumer implements Runnable {
	private Producer producer;
	
	public Consumer(Producer producer) {
		this.producer = producer;
	}

	public void run() {
		while(true) {
			
			synchronized (producer) {
				if(producer.hasData()) {
					String data = producer.getData();
					System.out.println("Consumer hat NEUE DATEN erhalten: " + data);
				} else {
					System.out.println("Consumer hat nichts neues gefunden");
					try {
						producer.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}

public class B15_wait_notify {

	/*
	 * wait/notify-Technik ist unwahrscheinlich in der Prüfung
	 * 
	 * Regeln:
	 * 
	 *   - wait, notify und notifyAll sind Methoden der Klasse Object
	 *   - wait, notify und notifyAll müssen aus einem synchronized-Block
	 *     aufgerufen werden.
	 *   - in der wait gibt der Thread den Lock ab 
	 */
	public static void main(String[] args) {

		Producer pA = new Producer();
		Consumer c1 = new Consumer(pA);
		Consumer c2 = new Consumer(pA);
		
		new Thread(pA).start();
		new Thread(c1).start();
		new Thread(c2).start();

	}

}