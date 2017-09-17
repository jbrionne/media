package fr.next.media.db;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orientechnologies.orient.core.Orient;
import com.orientechnologies.orient.core.db.ODatabaseThreadLocalFactory;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OClass;
import com.orientechnologies.orient.core.metadata.schema.OType;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;

public class ManageOrientDb {

	private final static Logger logger = LoggerFactory.getLogger(ManageOrientDb.class);

	private ODatabaseDocumentTx db;
	private static final String id = "id";
	private static final String content = "content";
	public static final String ADMIN = "admin";
	private static final String pathDb = "./temp/db/scratchpad2";

	public ManageOrientDb() {
		initalize();
	}

	private void initalize() {
		WriteReader.makeDir(pathDb);
		ODatabaseThreadLocalFactory customFactory = new MyCustomRecordFactory("plocal:" + pathDb);
		Orient.instance().registerThreadDatabaseFactory(customFactory);
		db = (ODatabaseDocumentTx) customFactory.getThreadDatabase();
		if (!db.exists()) {
			db.create();
		}
		checkClose();
	}

	private void checkClose() {
		if (db.isClosed()) {
			db.open(ADMIN, ADMIN);
		}
	}

	protected void finalize() throws Throwable {
		db.close();
		super.finalize();
	};

	public void save(String idLoc, byte[] mybytes, String classe) {
		List<ODocument> o = find(idLoc, classe);
		if (o == null || o.size() == 0) {
			logger.debug("save : " + idLoc);
			Decoup d = getDecoupAndMakeClass(idLoc, classe);
			ODocument doc = db.newInstance(d.getBefore());
			doc.field(id, d.getSub());
			doc.field(content, mybytes);
			doc.save();
		} else {
			ODocument doc = o.get(0);
			doc.field(content, mybytes);
			doc.save();
		}
	}

	public void delete(String idLoc, String classe) {
		List<ODocument> o = find(idLoc, classe);
		if (o == null || o.size() == 0) {
			throw new AssertionError("elment non trouvé " + id);
		} else {
			ODocument doc = o.get(0);
			doc.delete();
		}
	}

	public List<ODocument> find(String idLoc, String classe) {
		logger.debug("find " + idLoc);

		Decoup d = getDecoupAndMakeClass(idLoc, classe);
		return db.query(new OSQLSynchQuery<ODocument>(
				"select * from " + d.getBefore() + " where " + id + " = '" + d.getSub() + "'"));
	}

	private Decoup getDecoupAndMakeClass(String idLoc, String classe) {
		Decoup d = new Decoup(classe, idLoc);
		if (db.getMetadata().getSchema().getClass(d.getBefore()) == null) {
			String instanceClasseId = "instanceclasseid";
			if (!db.getMetadata().getSchema().existsClass(instanceClasseId)) {
				OClass instanceClasse = db.getMetadata().getSchema().createClass(d.getBefore());
				instanceClasse.createProperty(id, OType.STRING);
				instanceClasse.createProperty(content, OType.BINARY);
				//instanceClasse.createIndex(instanceClasseId, OClass.INDEX_TYPE.UNIQUE, id);
			}
		}

		return d;
	}

	public List<ODocument> findBeginWith(String idLoc, String classe) throws Exception {
		logger.debug("find " + idLoc);
		Decoup d = getDecoupAndMakeClass(idLoc, classe);
		return db.query(new OSQLSynchQuery<ODocument>(
				"select * from " + d.getBefore() + " where " + id + " like '" + d.getSub() + "%'"));
	}

	public String[] listDir(String pkg, final String begin, String classe) throws Exception {

		List<ODocument> lst = findBeginWith(pkg + begin, classe);
		String[] rep = null;
		if (lst.size() > 0) {
			rep = new String[lst.size()];
			int index = 0;
			for (ODocument d : lst) {
				String k = d.field(id);
				;
				rep[index] = k;
				index++;
			}
		}
		return rep;
	}

	public byte[] findAndGetContent(String idLoc, String classe) {
		List<ODocument> result = find(idLoc, classe);
		if (result.size() == 1) {
			ODocument doc = result.get(0);
			if (doc.field(content) != null) {
				return (byte[]) doc.field(content);
			} else {
				return null;
			}
		} else {
			logger.debug("error recup " + idLoc + " " + result.size());
			if (result.size() > 1) {
				throw new AssertionError("Multiple résulats");
			}
			return null;
		}
	}

	public void clean() {
		db.drop();
		initalize();
	}

}
