package fr.next.media.array;

public interface CoordOperation<K> {

	//should be an reference XArrayOrd..(rotation, translation, scale..)
	K[] transform(K[] b);
	
}
