package fr.next.media.memory;

import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.human.MedString;

public class InternalCmds {

	private Memory memory = Memory.getInstance();
	
	public String doCmd(String cmd) {
		String[] args = cmd.split(" ");
		if(args[0].equals("CREER")) {
			//TODO
			AxeOrd<AxeValue<AxeValue<Character>>> m = new MedString(args[1]);
			save(args[1], m);
		} else if(args[0].equals("GET")) {
			//TODO
			return get(args[1]).toString();
		}
		return "";
	}
	
	private void save(String idLoc, Object o) {
		memory.save(idLoc, o);
	}
	
	private Object get(String idLoc) {
		return memory.findAndGetContent(idLoc);
	}
}
