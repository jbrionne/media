package fr.next.media.neuron;

@FunctionalInterface
public interface Agregation {
	float sum(float currentVal, float previousNeuronVal, float incrementVal);
}
