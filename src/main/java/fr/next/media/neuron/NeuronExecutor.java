package fr.next.media.neuron;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeValue;

public class NeuronExecutor<T> {

	private final static Logger LOG = LoggerFactory.getLogger(NeuronExecutor.class);

	private Float excitationValue = 1f;
	private Float noExcitationValue = -1f;

	public NeuronExecutor() {
		this.excitationValue = 1f;
		this.noExcitationValue = -1f;
	}

	public NeuronExecutor(Float excitationValue, Float noExcitationValue) {
		super();
		this.excitationValue = excitationValue;
		this.noExcitationValue = noExcitationValue;
	}

	public void simul(List<Map<T, Float>> steps, ArrayXDOrd<Float, T, Axe<AxeValue<T>>> array,
			Map<T, Float> currentStep, Agregation c, Activation a, boolean negativeIfUnderThreshold) {
		System.out.println("input " + currentStep.toString());
		Map<T, Float> nextStep = new HashMap<>();
		steps.add(currentStep);
		for (Entry<T, Float> p : currentStep.entrySet()) {
			Float excitationVal = p.getValue();
			List<Pair<T, Float>> propagation = array.getPairForAnAxe(0,
					Axe.findIndex(p.getKey(), array.getAxe(0)));
			System.out.println("prog " + propagation.toString());
			for (Pair<T, Float> entry : propagation) {
				T i = entry.getKey();
				Float val = entry.getValue();
				if (nextStep.containsKey(i)) {
					nextStep.put(i, c.sum(val, excitationVal, nextStep.get(i)));
					System.out.println(val + " " + excitationVal + " " + i + " " + nextStep.get(i));
				} else {
					nextStep.put(i, c.sum(val, excitationVal, 0));
					System.out.println("new " + i + " " + nextStep.get(i));
				}
			}
		}
		if (!nextStep.isEmpty()) {
			Map<T, Float> filteredNextStep = new HashMap<>();
			for (Entry<T, Float> p : nextStep.entrySet()) {
				Float excitationVal = excitationValue;
				if (!a.threshold(p.getValue())) {
					if (!negativeIfUnderThreshold) {
						continue;
					} else {
						excitationVal = noExcitationValue;
					}
				}
				filteredNextStep.put(p.getKey(), excitationVal);
			}
			if (!filteredNextStep.isEmpty()) {
				simul(steps, array, filteredNextStep, c, a, negativeIfUnderThreshold);
			}
		}
	}

}
