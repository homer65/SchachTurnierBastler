package org.myoggradio.stb;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class DeepCopy 
{
	public static Object clone(Object obj)
	{
		Object erg = null;
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(obj);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			erg = ois.readObject();
		}
		catch (Exception e)
		{
			Protokol.write("DeepCopy:clone:Exception:");
			Protokol.write(e.toString());
		}
		return erg;
	}
}
