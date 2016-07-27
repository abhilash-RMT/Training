package ThreadSynchronizationExample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThreadSyncAssignment {
	public static List<Integer> list = new ArrayList<>();

	public synchronized void addElement() {

		int num = new Random().nextInt(10);
		System.out.println("Adding " + num);
		list.add(num);
		notify();

	}

	public synchronized void removeElement() {

		if (list.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			int value = list.remove(list.size() - 1);
			System.out.println("Removing " + value);
		}

	}

	public static void main(String args[]) {
		ThreadSyncAssignment threadSyncAssignment = new ThreadSyncAssignment();

		Thread adder = new Thread(new AdderThread(threadSyncAssignment));
		Thread remover = new Thread(new RemoverThread(threadSyncAssignment));

		adder.start();
		remover.start();

	}

}

class AdderThread implements Runnable {

	private ThreadSyncAssignment threadSyncAssignment;

	public AdderThread(ThreadSyncAssignment threadSyncAssignment) {
		this.threadSyncAssignment = threadSyncAssignment;
	}

	@Override
	public void run() {
		for (int count = 0; count < 10; count++) {
			threadSyncAssignment.addElement();
		}
	}

}

class RemoverThread implements Runnable {

	private ThreadSyncAssignment threadSyncAssignment;

	public RemoverThread(ThreadSyncAssignment threadSyncAssignment) {
		this.threadSyncAssignment = threadSyncAssignment;
	}

	@Override
	public void run() {
		for (int count = 0; count < 10; count++) {
			threadSyncAssignment.removeElement();
		}
	}

}
