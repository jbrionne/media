package fr.next.media.neuron;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;

public class MedNode extends AxeOrd<AxeValue> {

	public MedNode(String name, Axe<AxeValue<Integer>> entries, Axe<AxeValue<Integer>>  outputs, ArrayXDOrd map) {
		super(name);
		this.add(new AxeValue<Axe<AxeValue<Integer>>>(entries));
		this.add(new AxeValue<Axe<AxeValue<Integer>>>(outputs));
		this.add(new AxeValue<ArrayXDOrd>(map));
	}

	public Axe<AxeValue<Integer>> getEntries() {
		return (Axe<AxeValue<Integer>>) this.getElements().get(0).getValue();
	}

	public Axe<AxeValue<Integer>> getOutputs() {
		return (Axe<AxeValue<Integer>>) this.getElements().get(1).getValue();
	}

	public ArrayXDOrd getMap() {
		return (ArrayXDOrd) this.getElements().get(2).getValue();
	}

}
