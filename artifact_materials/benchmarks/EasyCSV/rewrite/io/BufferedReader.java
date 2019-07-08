@rewriteClass
public class BufferedReader {
    @alg
    @constructor
    BufferedReader BufferedReader(FileReaderr type);

    @alg
    String readLine();

    @alg
    String readLineHelp(int i);
    
    rewrite Object readLine(Object BufferedReader(FileReaderr f)) {
	return f.get(0);
    }

    rewrite Object readLine(Object readLine!(BufferedReader b)) {
    	return readLineHelp(b, 1);
    }

    rewrite Object readLineHelp(Object readLine!(BufferedReader b), int i) {
    	return readLineHelp(b, i+1);
    }

    rewrite Object readLineHelp(Object BufferedReader(FileReaderr f), int i) {
	return f.get(i);
    }

}
