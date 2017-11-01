package fr.next.media.neuron;

@FunctionalInterface
public interface Activation {
	boolean threshold(float val);
}
