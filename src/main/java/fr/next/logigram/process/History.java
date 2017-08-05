package fr.next.logigram.process;

import java.util.ArrayList;
import java.util.List;

public class History {

	
	List<String> actions = new ArrayList<>();
	
	public void addAction(String action) {
		actions.add(action);
	}
	
	public void draw() {
		for(String action : actions) {
			System.out.println(action);
		}
	}
}
