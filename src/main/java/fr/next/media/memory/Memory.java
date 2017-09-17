package fr.next.media.memory;

import java.util.concurrent.locks.ReentrantLock;

import fr.next.media.array.AxeOrd;
import fr.next.media.array.AxeValue;
import fr.next.media.db.ManageOrientDb;
import fr.next.media.db.SerialObject;

public class Memory {

	private static ManageOrientDb data = new ManageOrientDb();
	
	private String classe = "classe";
	
	private ReentrantLock refLock = new ReentrantLock();
	
	private static Memory INSTANCE = new Memory();
	
	public static String BOOLEAN_ID = "boolean";
	public static String ALPHA_MIN_ID = "alpha_min";
	public static String ALPHA_MAJ_ID = "alpha_maj";
	
	private Memory() {
		AxeOrd<AxeValue<Boolean>> booleanAxe = new AxeOrd<AxeValue<Boolean>>(BOOLEAN_ID);
		booleanAxe.add(new AxeValue<Boolean>(true));
		booleanAxe.add(new AxeValue<Boolean>(false));
		save(BOOLEAN_ID, booleanAxe);
	};
	
	public static Memory getInstance() {
		return INSTANCE;
	}
	
	public void save(String idLoc, Object axe) {
		refLock.lock();
		try {
			byte[] mybytes = SerialObject.serialize(axe);
			data.save(idLoc, mybytes, classe);
		} finally {
			refLock.unlock();
		}
	}
	
	public void delete(String idLoc) {
		refLock.lock();
		try {
			data.delete(idLoc, classe);
		} finally {
			refLock.unlock();
		}
	}

	public Object findAndGetContent(String idLoc) {
		refLock.lock();
		try {
			byte[] b = data.findAndGetContent(idLoc, classe);
			if(b == null) {
				return null;
			}
			return SerialObject.deserialize(b);
		} finally {
			refLock.unlock();
		}
	}
	
	
}
