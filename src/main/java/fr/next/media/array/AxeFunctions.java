package fr.next.media.array;

public class AxeFunctions {

	public static Axe<AxeValue<Axe>> list(Axe... axes) {
		Axe<AxeValue<Axe>> list = new AxeOrd<AxeValue<Axe>>("list");
		for(Axe axe : axes) {
			list.add(new AxeValue<Axe>(axe));
		}
		return list;
	}
	
	public static Axe union(Axe... axes) {
		throw new AssertionError("Not yet implemented");
	}
	
	public static Axe intersection(Axe... axes) {
		throw new AssertionError("Not yet implemented");
	}
	
}
