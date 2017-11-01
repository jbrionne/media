package fr.next.media.neuron;

import fr.next.media.array.Axe;
import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.array.CoordinatesXDByIndices;
import fr.next.media.array.impl.MapXDWithEmptyValueGenericImpl;

public class MedXorExcludeBoolean extends MapXDWithEmptyValueGenericImpl {

	public MedXorExcludeBoolean(Class clazz, CoordinatesXDByIndices coordinates, Object emptyVal, Axe... domains) {
		super(clazz, coordinates, emptyVal, domains);
		this.setValue(-2f, "6", "8");
		this.setValue(1.1f, "7", "8");
	}

}
