package fr.next.logigram.process;

import java.util.ArrayList;
import java.util.List;

public class ResultStrategy {

	private boolean findNewCase = false;
	private List<Action> actions = new ArrayList<>();

	public boolean isFindNewCase() {
		return findNewCase;
	}

	public void setFindNewCase(boolean findNewCase) {
		this.findNewCase = findNewCase;
	}

	public List<Action> getActions() {
		return actions;
	}
}
