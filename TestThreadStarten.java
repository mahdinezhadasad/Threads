package threads;

public class TestThreadStarten {

	public static void main(String[] args) {
	f1();
//		f2();
	}
	
	/*
	 * Ergebnis: a b c
	 */
	static void f2() {
		
		System.out.println("a");
		
		Runnable task = () -> System.out.println("b");
		Thread t1 = new Thread(task);
		t1.run(); // <- in dem main-Thread aufgerufen (blöd)
		
		System.out.println("c");
	}
	
	/*
	 * Ergebnis: a c
	 */
	static void f1() {
		
		System.out.println("a");
		
		Runnable task = () -> System.out.println("b");
		Thread t1 = new Thread(task);
		
		System.out.println("c");
		
	}

}