package fr.next.media.array;

import java.io.Serializable;

public interface AxeVal<T>  extends Serializable {

	void setAxe(Axe<? extends AxeVal<T>> axe);
	
	<G extends Axe<? extends AxeVal<T>>>  G  getAxe();
	
	T getValue();
	
	void setValue(T value);
	
}
