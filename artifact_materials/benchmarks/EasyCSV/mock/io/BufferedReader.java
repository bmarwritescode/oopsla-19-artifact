public class BufferedReader {
    FileReaderr reader;
    
    public BufferedReader(FileReaderr reader) {
	this.reader = reader;
    }

    public String readLine() {
	char c = this.reader.read();
	StringBuilder sb = new StringBuilder();
	while (c != '\n' && c != -1) {
	    sb.append(c);
	    c = this.reader.read();
	}
	String str = sb.toString();
	return c == -1 && str.length() == 0 ? null : str;
    }
}
