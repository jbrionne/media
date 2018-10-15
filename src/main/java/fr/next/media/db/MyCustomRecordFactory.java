package fr.next.media.db;

import com.orientechnologies.orient.core.db.ODatabaseDocumentInternal;
import com.orientechnologies.orient.core.db.ODatabaseThreadLocalFactory;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentPool;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;

public class MyCustomRecordFactory implements ODatabaseThreadLocalFactory {
	
	private String url;
	private ODatabaseDocumentTx oDatabaseDocumentTx;
	
	public MyCustomRecordFactory(String url){
		this.url = url;
	}
	
	@Override
	public ODatabaseDocumentInternal getThreadDatabase() {
		if(oDatabaseDocumentTx == null){
			oDatabaseDocumentTx = new ODatabaseDocumentTx(url);
		}		
		return  oDatabaseDocumentTx; 
	}
}



	
