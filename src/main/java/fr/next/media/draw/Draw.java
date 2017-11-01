package fr.next.media.draw;

import java.util.List;

import fr.next.media.array.ArrayXDOrd;
import fr.next.media.array.Axe;
import fr.next.media.array.AxeValue;
import fr.next.media.array.impl.ArrayXDWithEmptyValueGenericImpl;
import fr.next.media.array.impl.logigram.ArrayLogigramValue;

public class Draw {
	
	final static String[] alpha = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
			"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	public static String getAlpha(int i) {
		return alpha[i];
	}

	/**
	 * 
	 * @param maxLabel label size
	 * @param domainSize size of the domains list
	 * @param arrays2D arrays
	 */
	public static void drawLogigram(int maxLabel, int domainSize, List<ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>>> arrays2D) {

		int addLabel = 4;

		for (int k = maxLabel; k >= 0; k--) {
			for (int m = 0; m < maxLabel + addLabel; m++) {
				inline(" ");
			}
			for (int i = 0; i < domainSize - 1; i++) {
				ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cRef = arrays2D.get(i);
				Axe<AxeValue<String>> dCol = cRef.getAxe(1);
				for (AxeValue<String> s : dCol.getElements()) {
					if (k > s.getValue().length() - 1) {
						inline(" ");
					} else {
						inline(String.valueOf(s.getValue().charAt(k)));
					}
					inline(" ");
				}
				inline(" | ");
			}
			inlineln();
		}
		inlineln();

		for (int m = 0; m < maxLabel + addLabel; m++) {
			inline(" ");
		}
		int index = 0;
		for (int i = 0; i < domainSize - 1; i++) {
			ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cRef = arrays2D.get(i);
			for (int k = 0; k < cRef.getAxe(1).getElements().size(); k++) {
				inline(getAlpha(index));
				inline(" ");
				index++;
			}
			inline(" | ");
		}

		inlineln();
		inlineln();

		int first = 0;
		int sizeFirst = domainSize - 1;
		int inc = 1;
		while (first < arrays2D.size()) {
			ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> cRef = arrays2D.get(first);
			int nbLine = cRef.getAxe(0).getElements().size();
			for (int j = 0; j < nbLine; j++) {
				String ref = cRef.getAxe(0).getElements().get(j).getValue();
				StringBuilder strB = new StringBuilder();
				for (int k = 0; k < maxLabel; k++) {
					if (k < ref.length()) {
						strB.append(ref.charAt(k));
					} else {
						strB.append(" ");
					}
				}
				inline(strB.toString());

				inline(" ");
				if (inc < 10) {
					inline(" ");
				}
				inline(String.valueOf(inc));
				inline(" ");

				for (int i = first; i < first + sizeFirst; i++) {
					ArrayXDOrd<ArrayLogigramValue, String, Axe<AxeValue<String>>> c = arrays2D.get(i);
					for (ArrayLogigramValue ca : c.getValuesForAnAxe(0, j)) {
						inline(ca.getCode());
						inline(" ");
					}
					inline(" | ");
				}
				inlineln();
				inc++;
			}
			inlineln();
			first = first + sizeFirst;
			sizeFirst--;
		}
	}
	
	public static void draw(ArrayXDOrd arrays2D) {
		inline("        ");
		for (Object a :  arrays2D.getAxe(1).getElements()) {
			AxeValue x = (AxeValue) a;
			inline(x.getValue().toString());
			inline(" ");
		}
		inlineln();
		for (int i = 0; i < arrays2D.getAxe(0).size(); i++) {
			inline(((AxeValue) arrays2D.getAxe(0).getElements().get(i)).getValue().toString());
			inline(" ");
			for (int j = 0; j < arrays2D.getAxe(1).size(); j++) {
				inline(arrays2D.getValueByIndices(i, j).toString());
				inline(" ");
			}
			inlineln();
		}
	}
	
	public static void inline(String s) {
		System.out.print(s);
	}
	
	public static void inlineln(String s) {
		System.out.println(s);
	}
	
	public static void inlineln() {
		System.out.println();
	}
}
