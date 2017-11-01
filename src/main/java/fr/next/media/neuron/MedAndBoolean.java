package fr.next.media.neuron;

import fr.next.media.array.Axe;
import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.array.CoordinatesXDByIndices;
import fr.next.media.array.impl.MapXDWithEmptyValueGenericImpl;

public class MedAndBoolean extends MapXDWithEmptyValueGenericImpl {

	public MedAndBoolean(Class clazz, CoordinatesXDByIndices coordinates, Object emptyVal, Axe... domains) {
		super(clazz, coordinates, emptyVal, domains);
		this.setValue(0.6f, "3", "5");
		this.setValue(0.6f, "4", "5");
	}
	
	

}
