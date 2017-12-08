package fr.next.media.array;

public class RotationParams {

	public float x;
	public float y;
	public float z;
	public float w;

	public RotationParams() {
		x = 0f;
		y = 0f;
		z = 0f;
		w = 1f;
	}

	public RotationParams(float... coords) {
		this();
		this.x = coords[0];
		if(coords.length == 2) {
			this.w = coords[1];
		}
		if(coords.length == 3) {
			this.y = coords[1];
			this.w = coords[2];
		} 
		if(coords.length == 4) {
			this.y = coords[1];
			this.z = coords[2];
			this.w = coords[3];
		} 
		if(coords.length > 4) {
			throw new UnsupportedOperationException();
		}
	}

	public RotationParams normalizeLocal() {
		float n = MathWrapper.invSqrt(norm());
		x *= n;
		y *= n;
		z *= n;
		w *= n;
		return this;
	}

	public RotationParams fromAngleAxis(float angle, float... axis) {
		float axisX = 0f;
		float axisY = 0f;
		float axisZ = 0f;
		if(axis.length == 2) {
			axisX = axis[0];
			axisY = axis[1];
		}
		if(axis.length == 3) {
			axisX = axis[0];
			axisY = axis[1];
			axisZ = axis[2];
		} 
		
		VectorN normAxis = new VectorN(axisX, axisY, axisZ).normalize();
		fromAngleNormalAxis(angle, normAxis);
		return this;
	}

	public void loadIdentity() {
		x = y = z = 0f;
		w = 1f;
	}

	public RotationParams fromAngleNormalAxis(float angle, VectorN axis) {
		if (axis.getCoords()[0] == 0 && axis.getCoords()[1] == 0 && axis.getCoords()[2] == 0) {
			loadIdentity();
		} else {
			float halfAngle = 0.5f * angle;
			float sin = MathWrapper.sin(halfAngle);
			w = MathWrapper.cos(halfAngle);
			x = sin * axis.getCoords()[0];
			y = sin * axis.getCoords()[1];
			z = sin * axis.getCoords()[2];
		}
		return this;
	}

	public float norm() {
		return w * w + x * x + y * y + z * z;
	}

	public Matrix4D toRotationMatrix(Matrix4D result) {
		VectorN originalScale = result.toScaleVector();
		result.setScale(1f, 1f, 1f);
		float norm = norm();
		float s = (norm == 1f) ? 2f : (norm > 0f) ? 2f / norm : 0;

		float xs = x * s;
		float ys = y * s;
		float zs = z * s;
		float xx = x * xs;
		float xy = x * ys;
		float xz = x * zs;
		float xw = w * xs;
		float yy = y * ys;
		float yz = y * zs;
		float yw = w * ys;
		float zz = z * zs;
		float zw = w * zs;

		result.m00 = 1 - (yy + zz);
		result.m01 = (xy - zw);
		result.m02 = (xz + yw);
		result.m10 = (xy + zw);
		result.m11 = 1 - (xx + zz);
		result.m12 = (yz - xw);
		result.m20 = (xz - yw);
		result.m21 = (yz + xw);
		result.m22 = 1 - (xx + yy);

		result.setScale(originalScale.getCoords());
		return result;
	}

	public RotationParams set(RotationParams q) {
		this.x = q.x;
		this.y = q.y;
		this.z = q.z;
		this.w = q.w;
		return this;
	}

	public RotationParams fromRotationMatrix(float m00, float m01, float m02, float m10, float m11, float m12, float m20,
			float m21, float m22) {
		float lengthSquared = m00 * m00 + m10 * m10 + m20 * m20;
		if (lengthSquared != 1f && lengthSquared != 0f) {
			lengthSquared = 1.0f / MathWrapper.sqrt(lengthSquared);
			m00 *= lengthSquared;
			m10 *= lengthSquared;
			m20 *= lengthSquared;
		}
		lengthSquared = m01 * m01 + m11 * m11 + m21 * m21;
		if (lengthSquared != 1f && lengthSquared != 0f) {
			lengthSquared = 1.0f / MathWrapper.sqrt(lengthSquared);
			m01 *= lengthSquared;
			m11 *= lengthSquared;
			m21 *= lengthSquared;
		}
		lengthSquared = m02 * m02 + m12 * m12 + m22 * m22;
		if (lengthSquared != 1f && lengthSquared != 0f) {
			lengthSquared = 1.0f / MathWrapper.sqrt(lengthSquared);
			m02 *= lengthSquared;
			m12 *= lengthSquared;
			m22 *= lengthSquared;
		}

		float t = m00 + m11 + m22;

		if (t >= 0) {
			float s = MathWrapper.sqrt(t + 1);
			w = 0.5f * s;
			s = 0.5f / s;
			x = (m21 - m12) * s;
			y = (m02 - m20) * s;
			z = (m10 - m01) * s;
		} else if ((m00 > m11) && (m00 > m22)) {
			float s = MathWrapper.sqrt(1.0f + m00 - m11 - m22);
			x = s * 0.5f;
			s = 0.5f / s;
			y = (m10 + m01) * s;
			z = (m02 + m20) * s;
			w = (m21 - m12) * s;
		} else if (m11 > m22) {
			float s = MathWrapper.sqrt(1.0f + m11 - m00 - m22);
			y = s * 0.5f;
			s = 0.5f / s;
			x = (m10 + m01) * s;
			z = (m21 + m12) * s;
			w = (m02 - m20) * s;
		} else {
			float s = MathWrapper.sqrt(1.0f + m22 - m00 - m11);
			z = s * 0.5f;
			s = 0.5f / s;
			x = (m02 + m20) * s;
			y = (m21 + m12) * s;
			w = (m10 - m01) * s;
		}

		return this;
	}

	public RotationParams mult(RotationParams q) {
		return mult(q, null);
	}

	public RotationParams mult(RotationParams q, RotationParams res) {
		if (res == null) {
			res = new RotationParams();
		}
		float qw = q.w, qx = q.x, qy = q.y, qz = q.z;
		res.x = x * qw + y * qz - z * qy + w * qx;
		res.y = -x * qz + y * qw + z * qx + w * qy;
		res.z = x * qy - y * qx + z * qw + w * qz;
		res.w = -x * qx - y * qy - z * qz + w * qw;
		return res;
	}

	public VectorN multLocal(VectorN v) {
		float tempX, tempY;
		float vx = v.getCoords()[0];
		float vy = v.getCoords()[1];
		float vz = v.getCoords()[2];
		tempX = w * w * vx + 2 * y * w * vz - 2 * z * w * vy + x * x * vx + 2 * y * x * vy + 2 * z * x * vz - z * z * vx
				- y * y * vx;
		tempY = 2 * x * y * vx + y * y * vy + 2 * z * y * vz + 2 * w * z * vx - z * z * vy + w * w * vy - 2 * x * w * vz
				- x * x * vy;
		v.getCoords()[2] = 2 * x * z * vx + 2 * y * z * vy + z * z * vz - 2 * w * y * vx - y * y * vz + 2 * w * x * vy
				- x * x * vz + w * w * vz;
		v.getCoords()[0] = tempX;
		v.getCoords()[1] = tempY;
		return v;
	}

	public VectorN mult(VectorN v, VectorN	store) {
		if(store == null) {
		 	store = new VectorN(v.getCoords().length);
		}
		float vx0 = v.getCoords()[0];
		float vy0 = v.getCoords()[1];
		float vz0 = 0f;
		//TODO
		if(v.getCoords().length  >= 3) {
			vz0 = v.getCoords()[2];
		}
		if (vx0 == 0 && vy0 == 0 && vz0 == 0) {
			store.set(0f, 0f, 0f);
		} else {
			float vx = vx0, vy = vy0, vz = vz0;
			store.getCoords()[0] = w * w * vx + 2 * y * w * vz - 2 * z * w * vy + x * x * vx + 2 * y * x * vy
					+ 2 * z * x * vz - z * z * vx - y * y * vx;
			store.getCoords()[1] = 2 * x * y * vx + y * y * vy + 2 * z * y * vz + 2 * w * z * vx - z * z * vy
					+ w * w * vy - 2 * x * w * vz - x * x * vy;
			if(v.getCoords().length  >= 3) {
				store.getCoords()[2] = 2 * x * z * vx + 2 * y * z * vy + z * z * vz - 2 * w * y * vx - y * y * vz
						+ 2 * w * x * vy - x * x * vz + w * w * vz;
			}
		}
		return store;
	}

	public RotationParams inverse() {
		float norm = norm();
		if (norm > 0.0) {
			float invNorm = 1.0f / norm;
			return new RotationParams(-x * invNorm, -y * invNorm, -z * invNorm, w * invNorm);
		}
		return null;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float getW() {
		return w;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ", " + w + ")";
	}
}
