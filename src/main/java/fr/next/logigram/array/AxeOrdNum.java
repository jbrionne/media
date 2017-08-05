package fr.next.logigram.array;

import java.util.List;

public class AxeOrdNum<T extends AxeVal> extends AxeOrd<AxeValue<String>> {

	private int unit;

	public AxeOrdNum(String name, int unit) {
		super(name);
		this.unit = unit;
	}
	
	public static int findIndex(String elementVal, AxeOrdNum<? extends AxeVal> domainLine) {
		int index = 0;
		for (AxeVal s : domainLine.getElements()) {
			if (s.getValue().equals(elementVal)) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public int getUnit() {
		return unit;
	}
	
	@Override
	public List<AxeValue<String>> getElements() {
		return elements;
	}

}
