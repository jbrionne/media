package fr.next.media.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicLong;

import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.human.AxeMemoryUtils;

public class Media implements Runnable {

	public static final String WAIT_INPUT = "WAIT_INPUT";
	public static final String WAIT_INPUT_ADD_TO_ARGS = "WAIT_INPUT_ADD_TO_ARGS";
	public static final String EXEC_CREER = "EXEC_CREER";
	public static final String EXEC_ADD = "EXEC_ADD";
	public static final String EXEC_GET = "EXEC_GET";
	public static final String EXEC_OUTPUT = "EXEC_OUTPUT";
	public static final String EXEC_REC = "EXEC_TEST";
	
	private AxeMemoryUtils axeMemoryUtils = new AxeMemoryUtils();

	private Queue<String> pendingInputArgs = new ConcurrentLinkedQueue<>();
	private Queue<Object> out = new ConcurrentLinkedQueue<>();
	private Queue<String> pendingActions = new ConcurrentLinkedQueue<>();

	private List<Object> argsOutputMemory = new ArrayList<>();

	private boolean isFinish = false;
	
	private AtomicLong countArgs = new AtomicLong();

	public Media() {
		pendingActions.add(WAIT_INPUT);
	};

	public void in(String action) {
		pendingInputArgs.add(action);
	}

	public Object nextOut() {
		Object response = null;
		try {
			while ((response = out.poll()) == null) {
				Thread.sleep(1000);
				System.out.println("out");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public void run() {
		// inter.doCmd("CREER current_memory");
		String action = null;
		long lastExec = System.currentTimeMillis();
		while (System.currentTimeMillis() - lastExec < 3000) {
			try {
				while ((action = pendingActions.poll()) != null) {
					System.out.println(action);
					lastExec = System.currentTimeMillis();
					String input = null;
					String[] args = action.split(" ");
					if (args[0].equals(WAIT_INPUT)) {
						while (input == null) {
							input = pendingInputArgs.poll();
							if (input == null) {
								Thread.sleep(1000);
								if (System.currentTimeMillis() - lastExec < 3000) {
									isFinish = true;
									return;
								}
							}
						}
						if (input.equals("CREER")) {
							pendingActions.add(WAIT_INPUT_ADD_TO_ARGS);
							pendingActions.add(EXEC_CREER);
							pendingActions.add(WAIT_INPUT);
						} else if (input.equals("ADD")) {
							pendingActions.add(WAIT_INPUT_ADD_TO_ARGS);
							pendingActions.add(WAIT_INPUT_ADD_TO_ARGS);
							pendingActions.add(EXEC_ADD);
							pendingActions.add(WAIT_INPUT);
						} else if (input.equals("GET")) {
							pendingActions.add(WAIT_INPUT_ADD_TO_ARGS);
							pendingActions.add(EXEC_GET);
							pendingActions.add(EXEC_OUTPUT);
							pendingActions.add(WAIT_INPUT);
						}
						input = null;
					} else if (args[0].equals(WAIT_INPUT_ADD_TO_ARGS)) {
						while (input == null) {
							input = pendingInputArgs.poll();
							if (input == null) {
								Thread.sleep(1000);
							}
						}
						AxeOrd<AxeValue> arg = axeMemoryUtils.getAxeById("_" + String.valueOf(countArgs.incrementAndGet()));
						axeMemoryUtils.addElementToIdAxe(arg, input);
						input = null;
					} else if (args[0].equals(EXEC_CREER)) {
						axeMemoryUtils.getAxeById("_" + String.valueOf(countArgs.get()));
					} else if (args[0].equals(EXEC_ADD)) {
						axeMemoryUtils.addElementToIdAxe(axeMemoryUtils.getAxeById("_" + String.valueOf(countArgs.get() - 1)),
								axeMemoryUtils.getAxeById("_" + String.valueOf(countArgs.get())));
					} else if (args[0].equals(EXEC_GET)) {
						argsOutputMemory.add(axeMemoryUtils.getAxeById("_" + String.valueOf(countArgs.get())));
					} else if (args[0].equals(EXEC_OUTPUT)) {
						System.out.println("out add " + args[0]);
						out.add(argsOutputMemory.get(0));
						argsOutputMemory.clear();
					} else if (args[0].equals(EXEC_REC)) {
//						System.out.println("args" + argsMemory.get(0));
//						pendingActions.add(Media.WAIT_INPUT_ADD_TO_ARGS);
//						pendingActions.add("" + (Integer.valueOf(argsMemory.get(0)) + 1));
//						pendingActions.add(EXEC_REC);
//						argsMemory.clear();
					} 
				}

				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		isFinish = true;
	}
	
	private int count = 0;

	public boolean isFinish() {
		return isFinish;
	}
}
