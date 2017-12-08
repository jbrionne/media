package fr.next.media.array;

public class Matrix4D {

	public float m00, m01, m02, m03;
	public float m10, m11, m12, m13;
	public float m20, m21, m22, m23;
	public float m30, m31, m32, m33;

    public Matrix4D() {
        loadIdentity();
    }
    
    public void loadIdentity() {
        m01 = m02 = m03 = 0.0f;
        m10 = m12 = m13 = 0.0f;
        m20 = m21 = m23 = 0.0f;
        m30 = m31 = m32 = 0.0f;
        m00 = m11 = m22 = m33 = 1.0f;
    }

    public void setTranslation(float... translation) {
		m03 = translation[0];
		m13 = translation[1];
		m23 = translation[2];
	}
	
    public void setRotationQuaternion(RotationParams quat) {
        quat.toRotationMatrix(this);
    }
    
	public VectorN toScaleVector() {
		float scaleX = (float) Math.sqrt(m00 * m00 + m10 * m10 + m20 * m20);
		float scaleY = (float) Math.sqrt(m01 * m01 + m11 * m11 + m21 * m21);
		float scaleZ = (float) Math.sqrt(m02 * m02 + m12 * m12 + m22 * m22);
		return new VectorN(scaleX, scaleY, scaleZ);
    }
	
    public void setScale(float... coords) {
    	float x =  coords[0];
    	float y = coords[1];
    	float z = coords[2];
    	
    	VectorN vect1 = new VectorN(m00, m10, m20);
    	vect1.normalizeLocal().multLocal(x);
        m00 = vect1.getCoords()[0];
        m10 = vect1.getCoords()[1];
        m20 = vect1.getCoords()[2];

        vect1 = new VectorN(m01, m11, m21);
        vect1.normalizeLocal().multLocal(y);
        m01 = vect1.getCoords()[0];
        m11 = vect1.getCoords()[1];
        m21 = vect1.getCoords()[2];

        vect1 = new VectorN(m02, m12, m22);
        vect1.normalizeLocal().multLocal(z);
        m02 = vect1.getCoords()[0];
        m12 = vect1.getCoords()[1];
        m22 = vect1.getCoords()[2];
    }
    
    public Matrix4D mult(Matrix4D in2) {
        return mult(in2, null);
    }
    
    
    public Matrix4D mult(Matrix4D in2, Matrix4D store) {
        if (store == null) {
            store = new Matrix4D();
        }

        float temp00, temp01, temp02, temp03;
        float temp10, temp11, temp12, temp13;
        float temp20, temp21, temp22, temp23;
        float temp30, temp31, temp32, temp33;

        temp00 = m00 * in2.m00
                + m01 * in2.m10
                + m02 * in2.m20
                + m03 * in2.m30;
        temp01 = m00 * in2.m01
                + m01 * in2.m11
                + m02 * in2.m21
                + m03 * in2.m31;
        temp02 = m00 * in2.m02
                + m01 * in2.m12
                + m02 * in2.m22
                + m03 * in2.m32;
        temp03 = m00 * in2.m03
                + m01 * in2.m13
                + m02 * in2.m23
                + m03 * in2.m33;

        temp10 = m10 * in2.m00
                + m11 * in2.m10
                + m12 * in2.m20
                + m13 * in2.m30;
        temp11 = m10 * in2.m01
                + m11 * in2.m11
                + m12 * in2.m21
                + m13 * in2.m31;
        temp12 = m10 * in2.m02
                + m11 * in2.m12
                + m12 * in2.m22
                + m13 * in2.m32;
        temp13 = m10 * in2.m03
                + m11 * in2.m13
                + m12 * in2.m23
                + m13 * in2.m33;

        temp20 = m20 * in2.m00
                + m21 * in2.m10
                + m22 * in2.m20
                + m23 * in2.m30;
        temp21 = m20 * in2.m01
                + m21 * in2.m11
                + m22 * in2.m21
                + m23 * in2.m31;
        temp22 = m20 * in2.m02
                + m21 * in2.m12
                + m22 * in2.m22
                + m23 * in2.m32;
        temp23 = m20 * in2.m03
                + m21 * in2.m13
                + m22 * in2.m23
                + m23 * in2.m33;

        temp30 = m30 * in2.m00
                + m31 * in2.m10
                + m32 * in2.m20
                + m33 * in2.m30;
        temp31 = m30 * in2.m01
                + m31 * in2.m11
                + m32 * in2.m21
                + m33 * in2.m31;
        temp32 = m30 * in2.m02
                + m31 * in2.m12
                + m32 * in2.m22
                + m33 * in2.m32;
        temp33 = m30 * in2.m03
                + m31 * in2.m13
                + m32 * in2.m23
                + m33 * in2.m33;

        store.m00 = temp00;
        store.m01 = temp01;
        store.m02 = temp02;
        store.m03 = temp03;
        store.m10 = temp10;
        store.m11 = temp11;
        store.m12 = temp12;
        store.m13 = temp13;
        store.m20 = temp20;
        store.m21 = temp21;
        store.m22 = temp22;
        store.m23 = temp23;
        store.m30 = temp30;
        store.m31 = temp31;
        store.m32 = temp32;
        store.m33 = temp33;

        return store;
    }
    
    public VectorN toTranslationVector() {
        return new VectorN(m03, m13, m23);
    }
    
    public RotationParams toRotationQuat() {
    	RotationParams quat = new RotationParams();
        quat.fromRotationMatrix(m00, m01, m02, m10, m11, m12, m20, m21, m22);
        return quat;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Matrix4f\n[\n");
        result.append(" ");
        result.append(m00);
        result.append("  ");
        result.append(m01);
        result.append("  ");
        result.append(m02);
        result.append("  ");
        result.append(m03);
        result.append(" \n");
        result.append(" ");
        result.append(m10);
        result.append("  ");
        result.append(m11);
        result.append("  ");
        result.append(m12);
        result.append("  ");
        result.append(m13);
        result.append(" \n");
        result.append(" ");
        result.append(m20);
        result.append("  ");
        result.append(m21);
        result.append("  ");
        result.append(m22);
        result.append("  ");
        result.append(m23);
        result.append(" \n");
        result.append(" ");
        result.append(m30);
        result.append("  ");
        result.append(m31);
        result.append("  ");
        result.append(m32);
        result.append("  ");
        result.append(m33);
        result.append(" \n]");
        return result.toString();
    }
}
