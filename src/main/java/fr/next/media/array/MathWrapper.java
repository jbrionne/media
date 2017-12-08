package fr.next.media.array;

public class MathWrapper {

    public static float sin(float v) {
        return (float) Math.sin(v);
    }
    
    public static float sqrt(float fValue) {
        return (float) Math.sqrt(fValue);
    }
    
    public static float cos(float v) {
        return (float) Math.cos(v);
    }
    
    public static float invSqrt(float fValue) {
        return (float) (1.0f / Math.sqrt(fValue));
    }
}
