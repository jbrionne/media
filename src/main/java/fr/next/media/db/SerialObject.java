package fr.next.media.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerialObject {

	public static byte[] serialize(Object obj) {
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				ObjectOutputStream os = new ObjectOutputStream(out);) {
			os.writeObject(obj);
			return out.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		throw new AssertionError("serialization error");
	}

	public static Object deserialize(byte[] data) {
		try (ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);) {
			return is.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		throw new AssertionError("deserialization error");
	}
}
