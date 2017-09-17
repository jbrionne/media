package fr.next.media.array;

import java.io.Serializable;
import java.util.List;

public class CoordinatesXDByIndices implements Serializable {

	private List<Axe> axes;
	
	private int[] indices;

	public CoordinatesXDByIndices(List<Axe> axes, int[] indices) {
		super();
		this.axes = axes;
		this.indices = indices;
	}

	public int getIndex(int index) {
		return indices[index];
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		for(int index : indices) {
			result = prime * result + index;
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoordinatesXDByIndices other = (CoordinatesXDByIndices) obj;
		for(int index : indices) {
			if (index != other.indices[index])
				return false;
		}
		return true;
	}

	public Axe getAxe(int index) {
		return axes.get(index);
	}

}
