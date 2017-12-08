package fr.next.media.array;

import java.util.Arrays;

public class VectorN {

	private float[] coords;
	
	public VectorN(int size) {
		coords = new float[size];
	}
	
	public VectorN(int size, float val) {
		coords = new float[size];
		for(int i = 0; i < size; i ++) {
			coords[i] = val;
		}
	}

	public VectorN(float... coords) {
		super();
		this.coords = coords;
	}

	public float[] getCoords() {
		return coords;
	}

	public VectorN normalizeLocal() {
		float length = 0;
		for (float val : coords) {
			length = length + val * val;
		}
		if (length != 1f && length != 0f) {
			length = 1.0f / MathWrapper.sqrt(length);
			for (int i = 0; i < coords.length; i++) {
				coords[i] *= length;
			}
		}
		return this;
	}

	public VectorN normalize() {
		float length = 0f;
		for (float val : coords) {
			length = length + val * val;
		}
		if (length != 1f && length != 0f) {
			length = 1.0f / MathWrapper.sqrt(length);
			float[] newCoords = new float[coords.length];
			int i = 0;
			for (float val : newCoords) {
				newCoords[i] = val * length;
				i++;
			}
			return new VectorN(newCoords);
		}
		return new VectorN(Arrays.copyOf(coords, coords.length));
	}

	public VectorN multLocal(float scalar) {
		for (int i = 0; i < coords.length; i++) {
			coords[i] *= scalar;
		}
		return this;
	}

	public VectorN multLocal(VectorN scalarV) {
		for (int i = 0; i < coords.length; i++) {
			if(i < scalarV.getCoords().length) {
				coords[i] *= scalarV.getCoords()[i];
			}
		}
		return this;
	}

	public VectorN addLocal(VectorN vec) {
		for (int i = 0; i < coords.length; i++) {
			if(i < vec.getCoords().length) {
				coords[i] += vec.getCoords()[i];
			}
		}
		return this;
	}

	public VectorN set(VectorN vect) {
		for (int i = 0; i < coords.length; i++) {
			if(i < vect.getCoords().length) {
				coords[i] = vect.getCoords()[i];
			}
		}
		return this;
	}

	public VectorN set(float... vect) {
		for (int i = 0; i < coords.length; i++) {
			if(i < vect.length) {
				coords[i] = vect[i];
			}
		}
		return this;
	}

	public VectorN subtract(VectorN vec, VectorN store) {
		float[] newCoords = new float[coords.length];
		for (int i = 0; i < coords.length; i++) {
			if(i < vec.getCoords().length) {
				newCoords[i] = coords[i] - vec.getCoords()[i];
			}
		}
		return store.set(newCoords);
	}

	public VectorN divideLocal(VectorN scalar) {
		for (int i = 0; i < coords.length; i++) {
			coords[i] /= scalar.getCoords()[i];
		}
		return this;
	}

	public String toString() {
		return "(" + Arrays.toString(coords) + ")";
	}

}
