package fr.next.media.db;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WriteReader {

	private final static Logger logger = LoggerFactory.getLogger(WriteReader.class);

	public static void read(String fileNamePath) {
		String myLine = null;
		try (InputStreamReader flog = new InputStreamReader(new FileInputStream(fileNamePath));
				LineNumberReader llog = new LineNumberReader(flog);) {
			while ((myLine = llog.readLine()) != null) {
				logger.debug(myLine);
			}
		} catch (Exception e) {
			logger.error("Error : " + e.getMessage());
		}
	}

	public static void writeFile(String name1, String sourceCode) {
		try (FileWriter fstream = new FileWriter(name1); BufferedWriter out = new BufferedWriter(fstream);) {
			out.write(sourceCode);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void makeDir(String cibleJava) {
		String[] b = cibleJava.split("/");
		StringBuilder strB = new StringBuilder();
		subMakeDir(b, strB, b.length);
	}

	public static void subMakeDir(String[] b, StringBuilder strB, int max) {
		for (int i = 0; i < max; i++) {
			if (strB.length() != 0) {
				strB.append("/");
			}
			strB.append(b[i]);
			new File(strB.toString()).mkdir();
		}
	}

	public static void delete(File r) {
		if (r.isDirectory()) {
			del(r);
			r.delete();
		} else {
			r.delete();
		}
	}

	private static void del(File r) {
		File[] fileList = r.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].isDirectory()) {
				del(fileList[i]);
				fileList[i].delete();
			} else {
				fileList[i].delete();
			}
		}
	}

}
