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
    
    public CommunicationWithFiles(){};

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
		
		values.sort(null);
		fr = new FileReaderr(filename);
		bfr = new BufferedReader(fr);

		value = bfr.readLine();		
		while(value != null) {
		    if (!isAList == true) {
		    	String vbar = value.concat("|");
		    	values.add(vbar.concat(filename));
		    }
		    value = bfr.readLine();
		}

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
            //     if( toBeSorted.get(j).substring(0,toBeSorted.get(j).indexOf("|")).equals(toBeSorted.get(j+1).substring(0,toBeSorted.get(j+1).indexOf("|")) )){
                	toBeSorted.remove(j+1);
                        toBeSorted.remove(j);
                }else{j++; 	}
            }
            // toBeSorted.add(0, "Values present in only one file|File name");
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

            // try{
	    for(int k=0;k<= rows.size()-1;k++){
		String v = rows.get(k);
		bufferedWriter.write(v);
		bufferedWriter.newLine();
	    }
            // }finally{
	    bufferedWriter.close();
	    // System.out.println(filename+" was created");
            // }
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
