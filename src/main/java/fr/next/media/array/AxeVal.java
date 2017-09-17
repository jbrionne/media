package fr.next.media.array;

import java.io.Serializable;

public interface AxeVal<T>  extends Serializable {

	void setAxe(Axe<? extends AxeVal<T>> axe);
	
	T getValue();
	
	void setValue(T value);
	
}
