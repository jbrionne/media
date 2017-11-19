package fr.next.media.array;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.List;

public class CoordinatesXDByIndices<T, K, G> implements Serializable {

	private ArrayXDOrd axes;
	
	private ArrayXDOrd transform;
	
	public CoordinatesXDByIndices(ArrayXDOrd axes, ArrayXDOrd transformIndices) {
		super();
		this.axes = axes;
		this.transform = transformIndices;
	}
	
	
	public T getValueByIndices() {
		int[] newIndicesValue = new int[this.getPositionList().size()];
		for(int i = 0; i < this.getPositionList().size(); i++) {
			newIndicesValue[i] = this.getPositionList().get(i).intValue();
		}
		return (T) this.getAxes().getValueByIndices(newIndicesValue);
	}
	
	public T getValueWithInclusionOfArrayChildsByIndices() {
		int[] newIndicesValue = new int[this.getPositionList().size()];
		for(int i = 0; i < this.getPositionList().size(); i++) {
			newIndicesValue[i] = this.getPositionList().get(i).intValue();
		}
		return (T) this.getAxes().getValueWithInclusionOfArrayChildsByIndices(newIndicesValue);
	}
	
	
	
	public List<Float>  getPositionList() {
		return transform.getValuesForAnAxe(0, 0);
	}
	
	public List<Float>  getRotationList() {
		return transform.getValuesForAnAxe(1, 0);
	}
	
	public List<Float>  getScaleList() {
		return transform.getValuesForAnAxe(2, 0);
	}
	
	public float getPosition(int index) {
		return (float) transform.getValueByIndices(0, index);
	}
	
	public float getRotation(int index) {
		//if 3D then quaternion !
		return (float) transform.getValueByIndices(1, index);
	}
	
	public float getScale(int index) {
		return (float) transform.getValueByIndices(2, index);
	}
	
	public K[] transformInv(K[] b) {
		//TODO
				if(b[0] instanceof CoordOperation) {
					//TODO si rotation/scale or tarnslate...
					//mult matrices..see 3D
					return 	(K[]) Array.newInstance(b[0].getClass(), b.length);
				} else if(b[0] instanceof Integer) {
					//TODO si rotation/scale or translate...
					//mult matrices..see 3D
					Integer[] evol = new Integer[b.length];
					int index = 0;
					for(K m : b) {
						evol[index] = (Integer) m +  (int) getPosition(index);
						index++;
					}
					return 	(K[]) evol;
				}
				throw new AssertionError("No transform found"); 
	}
	
	public K[] transform(K[] b) {
		//TODO
		if(b[0] instanceof CoordOperation) {
			//TODO si rotation/scale or tarnslate...
			//mult matrices..see 3D
			return 	(K[]) Array.newInstance(b[0].getClass(), b.length);
		} else if(b[0] instanceof Integer) {
			//TODO si rotation/scale or translate...
			//mult matrices..see 3D
			Integer[] evol = new Integer[b.length];
			int index = 0;
			for(K m : b) {
				evol[index] = (Integer) m -  (int) getPosition(index);
				index++;
			}
			return 	(K[]) evol;
		}
		throw new AssertionError("No transform found"); 
	}
	
	

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		for(int index : indices) {
//			result = prime * result + index;
//		}
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		CoordinatesXDByIndices other = (CoordinatesXDByIndices) obj;
//		for(int index : indices) {
//			if (index != other.indices[index])
//				return false;
//		}
//		return true;
//	}

	public Axe<AxeValue<?>> getAxe(int index) {
		return axes.getAxe(index);
	}
	
	public int getAxesSize() {
		return axes.getAxes().size();
	}

	public ArrayXDOrd getAxes() {
		return axes;
	}

	public ArrayXDOrd getTransform() {
		return transform;
	}

}
