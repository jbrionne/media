package fr.next.media.array.impl;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeVal;
import fr.next.media.array.CoordinatesXDByIndices;

public abstract class AbstractArrayXDOrdDomains<T, K, G extends Axe<? extends AxeVal<K>>> extends AbstractArrayXDOrd<T, K, G>  implements ArrayXDOrd<T, K, G>  {

	protected G[] domains;
	
	@Override
	public float[] valuesToIndices(K... values) {
		float[] indices = new float[values.length];
		int i = 0;
		for (G d : domains) {
			indices[i] = Axe.findIndex(values[i], d);
			i++;
		}
		return indices;
	}
	
	
	@Override
	public T getValueFromUpperAxeCoordByIndices(ArrayXDOrd<T, K, G> axes, float... upperAxeIndices) {
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
		float[] newCoord = coordinates.transformByIndices(upperAxeIndices);
		if(isInBoundariesByIndices(newCoord)) {
			return getValueByIndices(newCoord);
		}	
		return null;
	}
	
	@Override
	public T getValueFromChildAxeCoordByIndices(ArrayXDOrd<T, K, G> axes, float... childAxeIndices) {
		CoordinatesXDByIndices<T, K, G> coordinates = getChildCoordinates(axes);
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
		float[] newCoord = coordinates.transformInvByIndices(childAxeIndices);
		if(isInBoundariesByIndices(newCoord)) {
			return getValueByIndices(newCoord);
		}	
		return null;
	}
	
	@Override
	public G getAxe(int index) {
		return domains[index];
	}
	
	@Override
	public List<G> getAxes() {
		return Arrays.asList(domains);
	}
	
	@Override
	public boolean isInBoundaries(K... axeValues) {
		int index = 0;
		for(K axeValue : axeValues) {
			int indexLine = Axe.findIndex(axeValue, domains[index]);
			if(indexLine == Axe.NOT_FOUND_IN_AXE) {
				return false;
			}
			index++;
		}
		return true;
	}

	@Override
	public boolean isInBoundariesByIndices(float... indices) {
		int index = 0;
		for(float indice : indices) {
			if(indice < 0 || indice >= domains[index].size()) {
				return false;
			}
			index++;
		}
		return true;
	}
	
	protected int convertFloatToInt(float myFloat) {
		//check if real int ?
		//return Math.round(myFloat);
		return Math.round(myFloat);
	}
}
