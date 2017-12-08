package fr.next.media.array;

import java.util.Arrays;

public class CoordinateTransform {

	private RotationParams rot = new RotationParams();
    private VectorN translation;
    private VectorN scale;
    
	public CoordinateTransform(int size) {
		this(new VectorN(size), new RotationParams());
		scale = new VectorN(size, 1f);
	}

	public CoordinateTransform(VectorN translation, RotationParams rot) {
		this.translation = translation;
		this.rot = rot;
	}
	
	public void fromTransformMatrix(Matrix4D mat) {
        translation.set(mat.toTranslationVector());
        rot.set(mat.toRotationQuat());
        scale.set(mat.toScaleVector());
    }
	
    public CoordinateTransform setTranslation(float... coords) {
        translation.set(new VectorN(coords));
        return this;
    }

    public CoordinateTransform setScale(float... coords) {
        scale.set(new VectorN(coords));
        return this;
    }
    
    public CoordinateTransform setRotation(RotationParams rot) {
        this.rot.set(rot);
        return this;
    }
	
    public VectorN getTranslation() {
        return translation;
    }
    
    public RotationParams getRotation() {
        return rot;
    }
    
    public VectorN getScale() {
        return scale;
    }
    
    public VectorN transformVector(final VectorN in,  VectorN store){
        return rot.mult(store.set(in).multLocal(scale), store).addLocal(translation);
    }
    
    public VectorN transformInverseVector(final VectorN in, VectorN store){
        in.subtract(translation, store);
        rot.inverse().mult(store, store);
        return store.divideLocal(scale);
    }
    
    @Override
    public String toString(){
        return getClass().getSimpleName() + "[ " + Arrays.toString(translation.getCoords()) + "]\n"
                                          + "[ " + rot.x + ", " + rot.y + ", " + rot.z + ", " + rot.w + "]\n"
                                          + "[ " + Arrays.toString(scale.getCoords()) + "]";
    }
}
