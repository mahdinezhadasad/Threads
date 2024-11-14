package threads;

class StaticValueHolder {
	private static int value;
	
	/*
	 * Die Lösung ist gut.
	 * 
	 * Das Class-Objekt wird empfohlen fürs Synchronisieren der
	 * Zugriffe auf statische Attribute einer Klasse.
	 */
//	public static void inc() {
//		synchronized (StaticValueHolder.class) {
//			value++;
//		}
//	}
	
	/*
	 * Dieselbe Lösung kompakter:
	 * 
	 * Wenn statische Methode synchronized ist, wir der Rumpf de Methode
	 * mit dem Class-Objekt synchroniiert.
	 */
	public synchronized static void inc() {
		value++;
	}
	
	public static int getValue() {
		return value;
	}
}

public class B13_statische_Methoden_synchronisieren {

	public static void main(String[] args) throws InterruptedException {
		
		Runnable task = () -> {
			for (int i = 0; i < 1_000_000; i++) {
				StaticValueHolder.inc();
			}
		};
		
		Thread tA = new Thread(task);
		Thread tB = new Thread(task);

		tA.start();
		tB.start();
		
		tA.join();
		tB.join();
		
		System.out.println("value: " + StaticValueHolder.getValue());
	}

}
