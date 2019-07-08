import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map.Entry;

public class PasswordMap {

        HashMap<String, String> passMap;
	public String masterHash;

	public PasswordMap()     {
	    passMap = new HashMap<String, String>();
	    Cryptographer c = new Cryptographer();
	    masterHash = c.hash("1234");
	}

	public void add(String tag, String passEncryption) {
		passMap.put(tag, passEncryption);
	}

	public void remove(String tag) {
		if (passMap.containsKey(tag))
			passMap.remove(tag);
	}

	public String get(String domainHash) {
		return passMap.get(domainHash);
	}
}
