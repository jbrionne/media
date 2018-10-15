package fr.next.media.human;

import fr.next.media.array.Axe;
import fr.next.media.array.AxeFunctions;
import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.memory.Memory;

public class MedString extends AxeOrd<AxeValue<AxeValue<Character>>> {

	private static transient Memory memory = Memory.getInstance();
	
	public MedString(String name) {
		super(name);
		
		for (int i = 0; i < name.length(); i++) {
			char c = name.charAt(i);
			AxeValue<Character> val = null;
			for (AxeValue<Axe<AxeValue<Character>>> k : AxeFunctions.listGen(alphaMini(), alphaMaj(), num(), accent()).getElements()) {
				for(AxeValue<Character> o : k.getValue().getElements()) {
					if (o.getValue().charValue() == c) {
						val = (AxeValue<Character>) o;
						break;
					}
				}
			}
			if(val == null) {
				throw new AssertionError("Unable to find " + c);
			}
			this.add(new AxeValue<AxeValue<Character>>(val));
		}
	}
	
	public static AxeOrd<AxeValue<Character>> num() {
		String id = Memory.NUM_ID;
		Object a = memory.findAndGetContent(id);
		AxeOrd<AxeValue<Character>> num = null;
		if (a == null) {
			num = new AxeOrd<AxeValue<Character>>(id);
			num.add(new AxeValue<Character>('0'));
			num.add(new AxeValue<Character>('1'));
			num.add(new AxeValue<Character>('2'));
			num.add(new AxeValue<Character>('3'));
			num.add(new AxeValue<Character>('4'));
			num.add(new AxeValue<Character>('5'));
			num.add(new AxeValue<Character>('6'));
			num.add(new AxeValue<Character>('7'));
			num.add(new AxeValue<Character>('8'));
			num.add(new AxeValue<Character>('9'));
			memory.save(id, num);
		} else {
			num = (AxeOrd<AxeValue<Character>>) a;
		}
		return num;
	}
	
	
	public static AxeOrd<AxeValue<Character>> consonnes() {
		//B, C, D, F, G, H, J, K, L, M, N, P, Q, R, S, T, V, W, X, Z ;
		return null;
	}
	

	public static AxeOrd<AxeValue<Character>> alphaMini() {
		String id = Memory.ALPHA_MIN_ID;
		Object a = memory.findAndGetContent(id);
		AxeOrd<AxeValue<Character>> alpha = null;
		if (a == null) {
			alpha = new AxeOrd<AxeValue<Character>>(id);
			alpha.add(new AxeValue<Character>('a'));
			alpha.add(new AxeValue<Character>('b'));
			alpha.add(new AxeValue<Character>('c'));
			alpha.add(new AxeValue<Character>('d'));
			alpha.add(new AxeValue<Character>('e'));
			alpha.add(new AxeValue<Character>('f'));
			alpha.add(new AxeValue<Character>('g'));
			alpha.add(new AxeValue<Character>('h'));
			alpha.add(new AxeValue<Character>('i'));
			alpha.add(new AxeValue<Character>('j'));
			alpha.add(new AxeValue<Character>('k'));
			alpha.add(new AxeValue<Character>('l'));
			alpha.add(new AxeValue<Character>('m'));
			alpha.add(new AxeValue<Character>('n'));
			alpha.add(new AxeValue<Character>('o'));
			alpha.add(new AxeValue<Character>('p'));
			alpha.add(new AxeValue<Character>('q'));
			alpha.add(new AxeValue<Character>('r'));
			alpha.add(new AxeValue<Character>('s'));
			alpha.add(new AxeValue<Character>('t'));
			alpha.add(new AxeValue<Character>('u'));
			alpha.add(new AxeValue<Character>('v'));
			alpha.add(new AxeValue<Character>('w'));
			alpha.add(new AxeValue<Character>('x'));
			alpha.add(new AxeValue<Character>('y'));
			memory.save(id, alpha);
		} else {
			alpha = (AxeOrd<AxeValue<Character>>) a;
		}
		return alpha;
	}
	
	public static AxeOrd<AxeValue<Character>> accent() {
		String id = Memory.ACCENT_ID;
		Object a = memory.findAndGetContent(id);
		AxeOrd<AxeValue<Character>> alpha = null;
		if (a == null) {
			alpha = new AxeOrd<AxeValue<Character>>(id);
			alpha.add(new AxeValue<Character>(' '));
			alpha.add(new AxeValue<Character>('\''));
			alpha.add(new AxeValue<Character>('.'));
			alpha.add(new AxeValue<Character>('é'));
			alpha.add(new AxeValue<Character>('è'));
			alpha.add(new AxeValue<Character>('à'));
			alpha.add(new AxeValue<Character>('ù'));
			alpha.add(new AxeValue<Character>('ç'));
		} else {
			alpha = (AxeOrd<AxeValue<Character>>) a;
		}
		return alpha;
	}
	public static AxeOrd<AxeValue<String>> phonème() {
		String id = Memory.PHONEME;
		Object a = memory.findAndGetContent(id);
		AxeOrd<AxeValue<String>> alpha = null;
		if (a == null) {
			alpha = new AxeOrd<AxeValue<String>>(id);
			alpha.add(new AxeValue<String>("/i/")); //il
			alpha.add(new AxeValue<String>("/e/")); //blé
			alpha.add(new AxeValue<String>("/ɛ/")); //colère
			alpha.add(new AxeValue<String>("/a/")); //plat
			alpha.add(new AxeValue<String>("/ɑ/")); //pâte
			alpha.add(new AxeValue<String>("/ɔ/")); //mort
			alpha.add(new AxeValue<String>("/o/")); //chaud
			alpha.add(new AxeValue<String>("/u/")); //genou
			alpha.add(new AxeValue<String>("/y/")); //rue
			alpha.add(new AxeValue<String>("/ø/")); //peu
			alpha.add(new AxeValue<String>("/œ/")); //peur
			alpha.add(new AxeValue<String>("/ə/")); //le
			alpha.add(new AxeValue<String>("/ɛ̃/")); //plein
			alpha.add(new AxeValue<String>("/ɑ̃/")); //sans
			alpha.add(new AxeValue<String>("/ɔ̃/")); //bon
			alpha.add(new AxeValue<String>("/œ̃/")); //brun
			
			alpha.add(new AxeValue<String>("/j/")); //yeux
			alpha.add(new AxeValue<String>("/w/")); //oui
			
			alpha.add(new AxeValue<String>("/p/")); //père
			alpha.add(new AxeValue<String>("/t/")); //terre
			alpha.add(new AxeValue<String>("/k/")); //cou
			alpha.add(new AxeValue<String>("/b/")); //bon
			alpha.add(new AxeValue<String>("/d/")); //dans
			alpha.add(new AxeValue<String>("/ɡ/")); //gare
			alpha.add(new AxeValue<String>("/f/")); //feu
			alpha.add(new AxeValue<String>("/s/")); //sale
			alpha.add(new AxeValue<String>("/ʃ/")); //chat
			alpha.add(new AxeValue<String>("/v/")); //vous
			alpha.add(new AxeValue<String>("/z/")); //zéro
			alpha.add(new AxeValue<String>("/ʒ/")); //je
			alpha.add(new AxeValue<String>("/l/")); //lent
			alpha.add(new AxeValue<String>("/ʁ/")); //rue
			alpha.add(new AxeValue<String>("/m/")); //main
			alpha.add(new AxeValue<String>("/n/")); //nous
			alpha.add(new AxeValue<String>("/ɲ/")); //agneau
			
			alpha.add(new AxeValue<String>("/h/")); //hop
			alpha.add(new AxeValue<String>("/ŋ/")); //camping
			
			alpha.add(new AxeValue<String>("/x/")); //jota espagnole, et 'kh,
			
//			Cas du « x » : [ks], [ɡz], [s], [z] ;
//			Cas du « y » : [i] prononcé doublement.
			
			memory.save(id, alpha);
		} else {
			alpha = (AxeOrd<AxeValue<String>>) a;
		}
		return alpha;
		
		
	}
	
	public static AxeOrd<AxeValue<Character>> alphaMaj() {
		String id = Memory.ALPHA_MAJ_ID;
		Object a = memory.findAndGetContent(id);
		AxeOrd<AxeValue<Character>> alpha = null;
		if (a == null) {
			alpha = new AxeOrd<AxeValue<Character>>(id);
			alpha.add(new AxeValue<Character>('A'));
			alpha.add(new AxeValue<Character>('B'));
			alpha.add(new AxeValue<Character>('C'));
			alpha.add(new AxeValue<Character>('D'));
			alpha.add(new AxeValue<Character>('E'));
			alpha.add(new AxeValue<Character>('F'));
			alpha.add(new AxeValue<Character>('G'));
			alpha.add(new AxeValue<Character>('H'));
			alpha.add(new AxeValue<Character>('I'));
			alpha.add(new AxeValue<Character>('J'));
			alpha.add(new AxeValue<Character>('K'));
			alpha.add(new AxeValue<Character>('L'));
			alpha.add(new AxeValue<Character>('M'));
			alpha.add(new AxeValue<Character>('N'));
			alpha.add(new AxeValue<Character>('O'));
			alpha.add(new AxeValue<Character>('P'));
			alpha.add(new AxeValue<Character>('Q'));
			alpha.add(new AxeValue<Character>('R'));
			alpha.add(new AxeValue<Character>('S'));
			alpha.add(new AxeValue<Character>('T'));
			alpha.add(new AxeValue<Character>('U'));
			alpha.add(new AxeValue<Character>('V'));
			alpha.add(new AxeValue<Character>('W'));
			alpha.add(new AxeValue<Character>('X'));
			alpha.add(new AxeValue<Character>('Y'));
			alpha.add(new AxeValue<Character>('Z'));
			memory.save(id, alpha);
		} else {
			alpha = (AxeOrd<AxeValue<Character>>) a;
		}
		return alpha;
	}

	public static String readMot(AxeOrd<AxeValue<AxeValue<Character>>> m) {
		StringBuilder strB = new StringBuilder();
		for (AxeValue<AxeValue<Character>> letter : m.getElements()) {
			strB.append(letter.getValue().getValue());
		}
		return strB.toString();
	}
}
