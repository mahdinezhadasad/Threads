package concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

class Producer implements Runnable {
	
	private BlockingQueue<Integer> data;
	
	public Producer(BlockingQueue<Integer> data) {
		this.data = data;
	}
	
	@Override
	public void run() {

		ThreadLocalRandom rnd = ThreadLocalRandom.current();
		
		while(true) {
			
			try {
				int value = rnd.nextInt();
				Thread.sleep(1000);
				
//				data.put(value);
//				System.out.println("Daten produziert und übergeben: " + value);
				
				long timeout = 1000;
				TimeUnit unit = TimeUnit.MILLISECONDS;
				boolean result = data.offer(value, timeout, unit);
				
				if(result) {
					System.out.println("Daten produziert und übergeben");
				} else {
					System.out.println("Daten produziert  aber verworfen");
				}

				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class Consumer implements Runnable {
	
	private BlockingQueue<Integer> data;
	
	public Consumer(BlockingQueue<Integer> data) {
		this.data = data;
	}

	@Override
	public void run() {
		while(true) {
			try {
//				Integer e = data.take();
//				System.out.println("konsumiert: " + e);
				
				long timeout = 500;
				TimeUnit unit = TimeUnit.MILLISECONDS;
				Integer e = data.poll(timeout, unit);
				
				if(e!=null) {
					System.out.println("konsumiert: " + e);
				} else {
					System.out.println("keine neuen Daten in der Zeit erschienen");
				}
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

public class TestBlockingQueue {

	public static void main(String[] args) {
		
		BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1000);

		Runnable producerTask = new Producer(queue);
		new Thread(producerTask).start();
		
		Consumer consumerTask1 = new Consumer(queue);
		new Thread(consumerTask1).start();
		
		Consumer consumerTask2 = new Consumer(queue);
		new Thread(consumerTask2).start();

	}

}
