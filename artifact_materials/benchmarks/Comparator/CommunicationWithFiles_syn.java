import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class CommunicationWithFiles {

    FileReaderr fr;
    String value;
    ArrayList<String> values;
    boolean isAList;
    String filename;
    BufferedReader bfr;
    String vbar;
    
    public CommunicationWithFiles(){};
    
    generator public boolean guard() {
    	boolean cond = false;
    	if (??) {
    	    cond = isAList;
    	}
    	return {| cond | !cond |};
    }
    
    generator public void stmts() {
	if (??) {
	    values.sort(null);
	}
	if (??) {
	    fr = new FileReaderr(filename);
	}
	if (??) {
	    bfr = new BufferedReader(fr);
	}
	if (??) {
	    value = bfr.readLine();	    
	}
	if (??) {
	    fr.close();
	}
    }

        /**
         * Function is creating diff.txt file
         * @param  arg filename name of the file to be loaded
         * @param  arg true for list of values
         */
        public ArrayList<String> ReadToArray(String filename2,boolean isAList2 ) throws IOException{    
    
	        fr = null;
                value = null;
                values = new ArrayList<String>();
		isAList = isAList2;
		filename = filename2;
		
		stmts();
		stmts();
		stmts();
		stmts();

		while(value != null) {
		    if (!isAList == true) {
			vbar = value.concat("|");			
			vbar = vbar.concat(filename);
			values.add(vbar);
		    }
		    stmts();		    
		}

		stmts();
		stmts();		
		
		return values;    
	} 
                               
        /**
         * Function is removing duplicated lines from ArrayList<String>
         * @param  toBeSorted - array to be sorted
         * @return ArrayList<String> without duplicated lines
         */
        public ArrayList<String> RemoveDuplicates(ArrayList<String> toBeSorted){
            int j=1;

            toBeSorted.sort(null);
	    
	    while(j+1<toBeSorted.size()){
		String get_j = toBeSorted.get(j);
		String sstr = get_j.substring(0, get_j.indexOf("|"));
		String get_j1 = toBeSorted.get(j+1);
		String sstr1 = get_j1.substring(0, get_j1.indexOf("|"));
		if (sstr.equals(sstr1)) {
                	toBeSorted.remove(j+1);
                        toBeSorted.remove(j);
                }else{j++; 	}
            }
            toBeSorted.set(0, "V|F");
            return toBeSorted;
        }   

        /**
         * Function is creating diff.txt pipe delimited file.
         * @param  rows with values to be checked
         */
        public void createFile(ArrayList<String> rows, String filename) throws IOException{
    
            FileWriterr fileWriter = new FileWriterr(filename);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter); 

	    for(int k=0;k<= rows.size()-1;k++){
		String v = rows.get(k);
		bufferedWriter.write(v);
		bufferedWriter.newLine();
	    }
	    bufferedWriter.close();
        }

        /**
         * Function is clearing chosen file
         * @param  name of the file to be cleared
         */
        public void ClearFile(String toBeCleared) throws IOException{
            FileWriterr fileWriter = new FileWriterr(toBeCleared);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter); 
            
            bufferedWriter.close();           
        }   
        
        
}
