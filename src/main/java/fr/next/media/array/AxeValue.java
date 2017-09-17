package fr.next.media.array;

public class AxeValue<T>  implements AxeVal<T> {

	private Axe<? extends AxeVal<T>> axe;
	
	private T value;
	
	public AxeValue(T value) {
		super();
		this.value = value;
	}
	
	public int findIndex() {
		int index = 0;
		for (AxeVal<T> s : axe.getElements()) {
			if (s.getValue().equals(value)) {
				return index;
			}
			index++;
		}
		return -1;
	}
	
	public T getValue() {
		return value;
	}

	@SuppressWarnings("unchecked")
	public <G extends Axe<? extends AxeVal<T>>>  G  getAxe() {
		return (G) axe;
	}

	@Override
	public void setAxe(Axe<? extends AxeVal<T>> axe) {
		this.axe = axe;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((axe == null) ? 0 : axe.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AxeValue other = (AxeValue) obj;
		if (axe == null) {
			if (other.axe != null)
				return false;
		} else if (!axe.equals(other.axe))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}
}
