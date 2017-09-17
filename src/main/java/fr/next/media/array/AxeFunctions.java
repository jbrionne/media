package fr.next.media.array;

public class AxeFunctions {

	 public static <T> Axe<AxeValue<Axe<AxeValue<T>>>> list(Axe<AxeValue<T>>... axes) {
		Axe<AxeValue<Axe<AxeValue<T>>>> list = new AxeOrd<AxeValue<Axe<AxeValue<T>>>>("list");
		for(Axe<AxeValue<T>> axe : axes) {
			list.add(new AxeValue<Axe<AxeValue<T>>>(axe));
		}
		return list;
	}
	
	public static <T> Axe<AxeValue<T>> union(Axe<AxeValue<T>>... axes) {
		throw new AssertionError("Not yet implemented");
	}
	
	public static <T> Axe<AxeValue<T>> intersection(Axe<AxeValue<T>>... axes) {
		throw new AssertionError("Not yet implemented");
	}
	
}
