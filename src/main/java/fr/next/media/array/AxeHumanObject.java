package fr.next.media.array;

import static fr.next.media.array.impl.ArrayFactory.mot;

import java.util.Map;

public class AxeHumanObject extends AxeOrd<AxeValue> {
	
	private Map<String, Integer> attribut;

	public AxeHumanObject(String name) {
		super(name);
	}

	public void add(String attributName, AxeValue attributValue) {
		AxeOrd<AxeValue> att = new AxeOrd<AxeValue>(name + "_" + attributName);
		att.add(new AxeValue<AxeOrd<AxeValue<AxeValue<Character>>>>(mot(attributName)));
		att.add(attributValue);
		
		attribut.put(attributName, this.elements.size());
		this.add(new AxeValue(att));
	}
	
	public AxeOrd<AxeValue> getAttribut(String name) {
		Integer index = attribut.get(name);
		return (AxeOrd<AxeValue>) this.getElements().get(index).getValue();
	}
	
	public AxeValue getAttributName(String name) {
		Integer index = attribut.get(name);
		return ((AxeOrd<AxeValue>) this.getElements().get(index).getValue()).getElements().get(0);
	}

	public AxeValue getAttributValue(String name) {
		Integer index = attribut.get(name);
		return ((AxeOrd<AxeValue>) this.getElements().get(index).getValue()).getElements().get(1);
	}
}
