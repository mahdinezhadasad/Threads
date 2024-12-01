package aufgaben.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class RaceCar implements Runnable {
	private String name;
	private List<RaceCar> finish;
	private CyclicBarrier barrier;
	
	public RaceCar(String name, List<RaceCar> finish, CyclicBarrier barrier) {
		this.name = name;
		this.finish = finish;
		this.barrier = barrier;
	}

	public void run() {
		System.out.println(name + " started.");
		
		synchronized (finish) {
			finish.add(this);
			System.out.println(name + " finished.");
		}
		
		try {
			barrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	public String toString() {
		return name;
	}
}

public class AufgabeThreadsCyclicBarrier {

	public static void main(String[] args) {
		
		int anzahlAutos = 4;

		List<RaceCar> finish = new ArrayList<>();
		
		Runnable barrierAction = () -> {
			System.out.println("\n*** Das Rennen ist vorbei");
			for (int i = 0; i < finish.size(); i++) {
				RaceCar rc = finish.get(i);
				System.out.println(i+1 + ". " + rc);
			}
		};
		
		CyclicBarrier barrier = new CyclicBarrier(anzahlAutos, barrierAction);
		
		RaceCar[] cars = {
			new RaceCar("VW", finish, barrier),	
			new RaceCar("Opel", finish, barrier),	
			new RaceCar("Mercedes", finish, barrier),	
			new RaceCar("Mazda", finish, barrier),	
		};

		ExecutorService service = Executors.newFixedThreadPool(anzahlAutos);
		
		for(RaceCar rc : cars) {
			service.execute(rc);
		}
		
		service.shutdown();
	}

}
