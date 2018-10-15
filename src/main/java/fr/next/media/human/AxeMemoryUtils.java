package fr.next.media.human;

import fr.next.media.array.Axe;
import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.memory.Memory;

public class AxeMemoryUtils {

	private Memory memory = Memory.getInstance();

	public AxeOrd<AxeValue> addElementToIdAxe(String id, Object element) {
		Object a = memory.findAndGetContent(id);
		if (a == null) {
			a = new AxeOrd<AxeValue>(id);
			((AxeOrd) a).getElements().add(new AxeValue(element));
		} else {
			AxeOrd<AxeValue> alpha = (AxeOrd<AxeValue>) a;
			boolean found = false;
			for (AxeValue el : alpha.getElements()) {
				if (el.getValue().equals(element)) {
					found = true;
					break;
				}
			}
			if (!found) {
				((AxeOrd) a).getElements().add(new AxeValue(element));
			}
		}
		memory.save(id, a);
		return (AxeOrd<AxeValue>) a;
	}

	public void addElementToIdAxe(Axe<AxeValue> alpha, Object element) {
		boolean found = false;
		for (AxeValue el : alpha.getElements()) {
			if (el.getValue().equals(element)) {
				found = true;
				break;
			}
		}
		if (!found) {
			alpha.getElements().add(new AxeValue(element));
		}
		memory.save(alpha.getName(), alpha);
	}

	public AxeOrd<AxeValue> getAxeById(String id) {
		Object el = memory.findAndGetContent(id);
		if (el == null) {
			el = new AxeOrd<AxeValue>(id);
			memory.save(id, el);
		}
		return (AxeOrd<AxeValue>) el;
	}
}
