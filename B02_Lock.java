package Threads.src.concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class MitDeadlock implements Runnable {
	
	static Object m1 = new Object();
	static Object m2 = new Object();
	
	@Override
	public void run() {
		synchronized (m1) {
			System.out.println("m1");
			synchronized(m2) {
				System.out.println("m1.m2");
			}
		}
		
		synchronized (m2) {
			System.out.println("m2");
			synchronized(m1) {
				System.out.println("m2.m1");
			}
		}
	}
}

class ImmerNochMitDeadlock implements Runnable {
	
	static Lock lock1 = new ReentrantLock();
	static Lock lock2 = new ReentrantLock();
	
	@Override
	public void run() {
		lock1.lock();
		System.out.println("m1");
		
		lock2.lock();
		System.out.println("m1.m2");
		lock2.unlock();

		lock1.unlock();
		
		//-----------------------------
		
		lock2.lock();
		System.out.println("m2");
		
		lock1.lock();
		System.out.println("m2.m1");
		lock1.unlock();
			
		lock2.unlock();
	}
}

class OhneDeadlock implements Runnable {
	
	static Lock lock1 = new ReentrantLock();
	static Lock lock2 = new ReentrantLock();
	
	@Override
	public void run() {
		lock1.lock();
		System.out.println("m1");
		
		if(lock2.tryLock()) {
			System.out.println("m1.m2");
			lock2.unlock();
		} else {
			System.out.println("m1.m2 übersprungen");
		}
		
		lock1.unlock();
		
		lock2.lock();
		System.out.println("m2");
		
		if(lock1.tryLock()) {
			System.out.println("m2.m1");
			lock1.unlock();
		} else {
			System.out.println("m2.m1 übersprungen");
		}
		
		lock2.unlock();
	}
}


public class B02_Lock {

	public static void main(String[] args) {
		
//		Runnable task = new MitDeadlock();
//		Runnable task = new ImmerNochMitDeadlock();
		Runnable task = new OhneDeadlock();
		
		new Thread(task).start();
		new Thread(task).start();

	}

}