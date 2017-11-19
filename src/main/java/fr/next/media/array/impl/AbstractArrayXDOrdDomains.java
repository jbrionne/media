package fr.next.media.array.impl;

import java.util.Arrays;
import java.util.List;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeVal;
import fr.next.media.array.CoordinatesXDByIndices;

public abstract class AbstractArrayXDOrdDomains<T, K, G extends Axe<? extends AxeVal<K>>> extends AbstractArrayXDOrd<T, K, G>  implements ArrayXDOrd<T, K, G>  {

	protected G[] domains;
	
	@Override
	public int[] valuesToIndices(K... values) {
		int[] indices = new int[values.length];
		int i = 0;
		for (G d : domains) {
			indices[i] = Axe.findIndex(values[i], d);
			i++;
		}
		return indices;
	}
	
	@Override
	public T getValueFromUpperAxeCoord(ArrayXDOrd<T, K, G> axes, K... upperAxeIndices) {
		CoordinatesXDByIndices<T, K, G> coordinates = getCoordinates(axes);
		if(coordinates.getAxesSize() < domains.length) {
			throw new AssertionError("Not compatible axes : upper reference should have at least the same number of axes");
		}
		
		for(int i = 0; i < coordinates.getAxesSize(); i++) {
			boolean found = false;
			int j = 0;
			for (G d : domains) {
				if(d.getName().equals(coordinates.getAxe(i).getName())) {
					found = true;
					break;
				}
				j++;
			}
			if(!found) {
				throw new AssertionError("Not compatible axes : unable to find " + coordinates.getAxe(i).getName());
			}
		}
		return getValue(coordinates.transform(upperAxeIndices));
	}
	
	@Override
	public G getAxe(int index) {
		return domains[index];
	}
	
	@Override
	public List<G> getAxes() {
		return Arrays.asList(domains);
	}
}
