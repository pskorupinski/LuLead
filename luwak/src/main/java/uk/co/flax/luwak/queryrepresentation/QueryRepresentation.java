/**
 * 
 */
package uk.co.flax.luwak.queryrepresentation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.lucene.search.Query;

/**
 * @author nonlinear
 *
 */
public abstract class QueryRepresentation implements Serializable {

	public byte[] toBytes() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		try {
		  out = new ObjectOutputStream(bos);   
		  out.writeObject(this);
		  byte[] yourBytes = bos.toByteArray();
		  return yourBytes;
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} finally {
		  try {
		    if (out != null) {
		      out.close();
		    }
		  } catch (IOException ex) {
			ex.printStackTrace();
		    return null;
		  }
		  try {
		    bos.close();
		  } catch (IOException ex) {
			ex.printStackTrace();
		    return null;
		  }
		}
	}
	
	public static QueryRepresentation fromBytes(byte [] bytes) {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInput in = null;
		try {
			in = new ObjectInputStream(bis);
			Object o = in.readObject(); 
			return (QueryRepresentation) o;
		} catch (IOException ex) {
			ex.printStackTrace();
		    return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		    return null;
		} finally {
			try {
				bis.close();
		    } catch (IOException ex) {
				ex.printStackTrace();
			    return null;
		    }
		  try {
		    if (in != null) {
		      in.close();
		    }
		  } catch (IOException ex) {
				ex.printStackTrace();
			    return null;
		  }
		}
	}

	public abstract Query getQuery();
	
}
