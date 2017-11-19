package fr.next.media.array.impl;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeVal;
import fr.next.media.array.CoordinatesXDByIndices;

public abstract class AbstractArrayXDOrd<T, K, G extends Axe<? extends AxeVal<K>>>  implements ArrayXDOrd<T, K, G> {

	private List<CoordinatesXDByIndices<T, K, G>> coordinates = new ArrayList<>();
	private List<CoordinatesXDByIndices<T, K, G>> childCoordinates = new ArrayList<>();

	
	@Override
	public void merge() {
		for(CoordinatesXDByIndices<T, K, G> c : childCoordinates) {
			c.getAxes().merge();
			List<Pair<List<K>,T>> val = c.getAxes().getAllWithKey();
			for(Pair<List<K>,T> p : val) {
				K[] m = p.getKey().toArray((K[])Array.newInstance(p.getKey().get(0).getClass(), p.getKey().size()));
				setValue(p.getValue(), c.transformInv(m));
			}
		}
	}
	
	@Override
	public void mergeOnlyFirstLevel() {
		for(CoordinatesXDByIndices<T, K, G> c : childCoordinates) {
			List<Pair<List<K>,T>> val = c.getAxes().getAllWithKey();
			for(Pair<List<K>,T> p : val) {
				K[] m = p.getKey().toArray((K[])Array.newInstance(p.getKey().get(0).getClass(), p.getKey().size()));
				setValue(p.getValue(), c.transformInv(m));
			}
		}
	}
	
	@Override
	public void addCoordinate(CoordinatesXDByIndices<T, K, G> coordinates) {
		this.coordinates.add(coordinates);
		coordinates.getAxes().addChildCoordinate(new CoordinatesXDByIndices<>(this, coordinates.getTransform()));
	}

	@Override
	public List<CoordinatesXDByIndices<T, K, G>> getChildCoordinates() {
		return childCoordinates;
	}

	@Override
	public void addChildCoordinate(CoordinatesXDByIndices<T, K, G> coordinates) {
		this.childCoordinates.add(coordinates);
	}
	
	@Override
	public CoordinatesXDByIndices getCoordinates() {
		if(coordinates.size() == 1) {
			return coordinates.get(0);
		}
		throw new AssertionError("Multiple upper coordinates, use getCoordinates(axes)");
	}

	@Override
	public CoordinatesXDByIndices<T, K, G>  getCoordinates(ArrayXDOrd<T, K, G> axes) {
		for(CoordinatesXDByIndices<T, K, G>  c : coordinates) {
			boolean found = false;
			for(int i = 0; i < c.getAxesSize(); i++) {
				for(G a : axes.getAxes()) {
					if(c.getAxe(i).equals(a)) {
						found = true;
						break;
					}
				}
			}
			if(found) {
				return c;
			}
		}
		throw new AssertionError("No coordinates were found");
	}
	
	@Override
	public void setValueByIndices(T value, CoordinatesXDByIndices<T, K, G> coord) {
		if(!coord.getAxes().equals(this)) {
			throw new IllegalArgumentException("coordinate must be for this axe");
		}
		int[] newIndicesValue = new int[coord.getPositionList().size()];
		for(int i = 0; i < coord.getPositionList().size(); i++) {
			newIndicesValue[i] = coord.getPositionList().get(i).intValue();
		}
		this.setValueByIndices(value, newIndicesValue);
	}

	@Override
	public T getValueByIndices(CoordinatesXDByIndices<T, K, G> coord) {
		if(!coord.getAxes().equals(this)) {
			throw new IllegalArgumentException("coordinate must be for this axe else use getValueFromUpperAxeCoord ");
		}
		int[] newIndicesValue = new int[coord.getPositionList().size()];
		for(int i = 0;  i < coord.getPositionList().size(); i++) {
			newIndicesValue[i] = (int) coord.getPositionList().get(i).intValue();
		}
		return this.getValueByIndices(newIndicesValue);
	}
	
	@Override
	public T getValueWithInclusionOfArrayChilds(K... indices) {
		T val = getValue(indices);
		if(val == null) {//val empty !
			for(CoordinatesXDByIndices<T, K, G> coord : this.getChildCoordinates()) {
				
			}
		}
		return null; //TODO
	}

	@Override
	public T getValueWithInclusionOfArrayChildsByIndices(int... indices) {
		return null; //TODO
	}
	
	@Override
	public void setTranslation(Class<T> clazzT, T... values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setRotationQuaternion(Class<T> clazzT, T w, T... values) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setScale(Class<T> clazzT, T... values) {
		throw new UnsupportedOperationException();
	}

}
