package fr.next.media.array;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import com.jme3.math.FastMath;
import com.jme3.math.Transform;

public class CoordinatesXDByIndices<T, K, G extends Axe<? extends AxeVal<K>>> implements Serializable {
	
	
	private ArrayXDOrd<T, K, G> axes;

	private ArrayXDOrd<Float, K, G> transformIndices;

	public CoordinatesXDByIndices(ArrayXDOrd<T, K, G> axes, ArrayXDOrd<Float, K, G> transformIndices) {
		super();
		this.axes = axes;
		this.transformIndices = transformIndices;
	}

	public T getOriginValueByIndices() {
		float[] newIndicesValue = new float[this.getAxes().getAxes().size()];
		return (T) this.getAxes().getValueByIndices(transformInvByIndices(newIndicesValue));
	}

	public T getOriginValueWithInclusionOfArrayChildsByIndices() {
		float[] newIndicesValue = new float[this.getAxes().getAxes().size()];
		return (T) this.getAxes().getValueWithInclusionOfArrayChildsByIndices(transformInvByIndices(newIndicesValue));
	}

	public List<Float> getPositionList() {
		return transformIndices.getValuesForAnAxe(0, 0);
	}
	
	public List<Float> getRotationList() {
		return transformIndices.getValuesForAnAxe(1, 0);
	}

	public List<Float> getScaleList() {
		return transformIndices.getValuesForAnAxe(2, 0);
	}

	public Float getPosition(int index) {
		return transformIndices.getValueByIndices(0f, (float) index);
	}

	public Float getRotation(int index) {
		return transformIndices.getValueByIndices(1f, (float) index);
	}

	public Float getScale(int index) {
		return transformIndices.getValueByIndices(2f, (float) index);
	}
	
	public void rotate(float angleDeg, float... vector) {
			RotationParams q = new RotationParams();
			q.fromAngleAxis(angleDeg * FastMath.DEG_TO_RAD, vector);
			
			Matrix4D result = new Matrix4D();
			result.setTranslation(0f, 0f, 0f);
			result.setRotationQuaternion(q);
			result.setScale(1.0f, 1.0f, 1.0f);
			
			List<Float> tLine = transformIndices.getValuesForAnAxe(0, 0);
			List<Float> rotLine = transformIndices.getValuesForAnAxe(0, 1);
			List<Float> scaleLine = transformIndices.getValuesForAnAxe(0, 2);
			
			Matrix4D m = new Matrix4D();
			m.setTranslation(conv(tLine));
			m.setRotationQuaternion(new RotationParams(conv(rotLine)));
			m.setScale(conv(scaleLine));
			
			Matrix4D matrix4f =  m.mult(result);
			CoordinateTransform worldTransform = new CoordinateTransform(vector.length);
			worldTransform.fromTransformMatrix(matrix4f);
			
			VectorN v = worldTransform.getTranslation();
			RotationParams qa = worldTransform.getRotation();
			VectorN s = worldTransform.getScale();
			
			transformIndices.setValuesForAnAxe(0, convF(v.getCoords()));
			transformIndices.setValuesForAnAxe(1, qa.getX(), qa.getY(), qa.getZ(), qa.getW());
			transformIndices.setValuesForAnAxe(2, convF(s.getCoords()));
	}
	
	
	
	public void scale(float... vector) {
		// TODO
	}

	public void translate(float... vector) {
		// TODO
	}

	public K[] transformInv(K[] b) {
		// TODO
		if (b[0] instanceof CoordOperation) {
			return (K[]) Array.newInstance(b[0].getClass(), b.length);
		} else if (b[0] instanceof Integer) {
			float[] evol = new float[b.length];
			int index = 0;
			for (K m : b) {
				evol[index] = (float) ((Integer) m);
				index++;
			}
			float[] vals = transformInvByIndices(evol);
			Integer[] evolr = new Integer[b.length];
			index = 0;
			for (float m : vals) {
				evolr[index] = (int) m;
				index++;
			}
			return (K[]) evolr;
		} else if (b[0] instanceof Float) {
			return (K[]) convF(transformInvByIndices(conv((Float[]) b)));
		}
		throw new AssertionError("No transform found");
	}
	
	public float[] transformInvByIndices(float... b) {
		
		List<Float> tLine = transformIndices.getValuesForAnAxe(0, 0);
		List<Float> rotLine = transformIndices.getValuesForAnAxe(0, 1);
		List<Float> scaleLine = transformIndices.getValuesForAnAxe(0, 2);
		
		CoordinateTransform worldTransform = new CoordinateTransform(b.length);
		worldTransform.setTranslation(conv(tLine));
		worldTransform.setRotation(new RotationParams(conv(rotLine)));
		worldTransform.setScale(conv(scaleLine));
		
		int index = 0;
		float[] evol = new float[b.length];
		for (float m : b) {
			evol[index] = m + getPosition(index);
			index++;
		}
		VectorN k = new VectorN(evol.length);
		worldTransform.transformVector(new VectorN(evol), k);

		for (int i = 0; i < evol.length; i++) {
			evol[i] = k.getCoords()[i] - getPosition(i);
		}
		return evol;
	}
	
	public float[] transformByIndices(float... b) {
		
		List<Float> tLine = transformIndices.getValuesForAnAxe(0, 0);
		List<Float> rotLine = transformIndices.getValuesForAnAxe(0, 1);
		List<Float> scaleLine = transformIndices.getValuesForAnAxe(0, 2);
		
		CoordinateTransform worldTransform = new CoordinateTransform(b.length);
		worldTransform.setTranslation(conv(tLine));
		worldTransform.setRotation(new RotationParams(conv(rotLine)));
		worldTransform.setScale(conv(scaleLine));
	
		float[] evol = new float[b.length];
		int index = 0;
		for (float m : b) {
			evol[index] = m + getPosition(index);
			index++;
		}
		VectorN k = new VectorN(evol.length);
		worldTransform.transformInverseVector(new VectorN(evol), k);
		for (int i = 0; i < evol.length; i++) {
			evol[i] = k.getCoords()[i] - getPosition(i);
		}
		return evol;
	}

	public K[] transform(K[] b) {
		// TODO
		if (b[0] instanceof CoordOperation) {
			return (K[]) Array.newInstance(b[0].getClass(), b.length);
		} else if (b[0] instanceof Integer) {
			float[] evol = new float[b.length];
			int index = 0;
			for (K m : b) {
				evol[index] = (float) ((Integer) m);
				index++;
			}
			float[] vals = transformByIndices(evol);
			Integer[] evolr = new Integer[b.length];
			index = 0;
			for (float m : vals) {
				evolr[index] = (int) m;
				index++;
			}
			return (K[]) evolr;
		} else if (b[0] instanceof Float) {
			return (K[]) convF(transformByIndices(conv((Float[]) b)));
		}
		throw new AssertionError("No transform found");
	}
	
	public G getAxe(int index) {
		return axes.getAxe(index);
	}

	public int getAxesSize() {
		return axes.getAxes().size();
	}

	public ArrayXDOrd<T, K, G> getAxes() {
		return axes;
	}

	public ArrayXDOrd getTransform() {
		return transformIndices;
	}

	//TODO
    private CoordinateTransform combineWithParent(CoordinateTransform current, CoordinateTransform parent) {
    	current.getScale().multLocal(parent.getScale());
        parent.getRotation().mult(current.getRotation(), current.getRotation());
        current.getTranslation().multLocal(parent.getScale());
        parent
        .getRotation()
            .multLocal(current.getTranslation())
            .addLocal(parent.getTranslation());
        return current;
    }
    
    private float[] conv(Float[] array) {
		float[] re = new float[array.length];
		int i = 0;
		for(Float f : array) {
			re[i] = f;
			i++;
		}
		return re;
	}
	
	private float[] conv(List<Float> list) {
		return conv(list.toArray(new Float[0]));
	}
	
	private Float[] convF(float[] array) {
		Float[] re = new Float[array.length];
		int i = 0;
		for(Float f : array) {
			re[i] = f;
			i++;
		}
		return re;
	}
}
