package easycsv;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static easycsv.CodeAssertion.verifyThat;

/**
 * Created by Dan Geabunea on 3/24/15.
 */
public class CsvDocument {
    private ArrayList<CsvRow> csvRows;

    public CsvDocument(ArrayList<CsvRow> csvRows){
        this.csvRows = csvRows;
    }

    public ArrayList<CsvRow> getCsvRows() {
        return csvRows;
    }

    /**
     * Boolean flag for determining if a CSV document is empty (does not have any rows/content)
     */
    public boolean isEmpty(){
        return this.csvRows.size() == 0;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
	int size = csvRows.size();
        for(int i = 0; i < size; i++) {
	    CsvRow row = csvRows.get(i);
            sb.append(row.toString());
        }
        return sb.toString();
    }

    /**
     * Reads the content of a csv file and transforms it into a CsvDocument object
     * @param filePath The absolute file path to the csv file
     * @throws IOException
     */
    public static CsvDocument read(String filePath) throws IOException {
        CsvConfiguration defaultConfiguration = new CsvConfiguration();
        return CsvDocument.read(filePath, defaultConfiguration);
    }

    generator public static int genInt(Object[] localObjs) {
    	if (??) {
    	    String filepath = (String) localObjs[0];
    	    return filepath.length();
    	}
    	if (??) {
    	    String csvLine = (String) localObjs[0];
    	    return csvLine.length();
    	}
    	if (??) {
    	    BufferedReader br = (BufferedReader) localObjs[6];
    	    String brLine = br.readLine();
    	    return brLine.length();
    	}
    	if (??) {
    	    ArrayList<CsvRow> parsedCsvRows = (ArrayList<CsvRow>) localObjs[2];
    	    return parsedCsvRows.size();	
    	}
    	return ??;
    }
    
    generator public static boolean genGuard(Object[] localObjs) {
    	int i1 = genInt(localObjs);
    	int i2 = genInt(localObjs);
    	boolean cond1 = {| i1 < i2, i1 <= i2, i1 == i2 |};
    	Object local = localObjs[??];
    	boolean cond2 = local == null;
					 
    	return {| cond1, cond2, !cond1, !cond2 |};
    }
    
    generator public static CsvRow setRow(Object[] localObjs) {
    	if (??) { return new CsvRow(); }
    	if (??) {
    	    CsvConfiguration conf = (CsvConfiguration) localObjs[1];
    	    String filepath = (String) localObjs[0];
    	    return parseCsvRow(conf, filepath);
    	}
    	if (??) {
    	    CsvConfiguration conf = (CsvConfiguration) localObjs[1];
    	    String csvLine = (String) localObjs[3];
    	    return parseCsvRow(conf, csvLine);
    	}
    	if (??) {
    	    CsvConfiguration conf = (CsvConfiguration) localObjs[1];
    	    BufferedReader br = (BufferedReader) localObjs[6];
    	    String brLine = br.readLine();
    	    return parseCsvRow(conf, brLine);
    	}
    	return null;
    }
    
    generator public static CsvDocument setParsedDocument(Object[] localObjs) {
    	if (??) {
    	    ArrayList<CsvRow> parsedRows = (ArrayList<CsvRow>) localObjs[2];
    	    return new CsvDocument(parsedRows);
    	}
    	return null;
    }
    
    generator public static String setCsvLine(Object[] localObjs) {
    	if (??) {
    	    String filepath = (String) localObjs[0];
    	    return filepath;
    	}
    	if (??) {
    	    String csvLine = (String) localObjs[3];
    	    return csvLine;
    	}
    	if (??) {
    	    BufferedReader br = (BufferedReader) localObjs[6];
    	    String brLine = br.readLine();
    	    return brLine;
    	}
    	return null;
    }
    
    generator public static ArrayList<CsvRow> setParsedCsvRows(Object[] localObjs) {
    	if (??) { return new ArrayList<>(); }
    	return null;
    }
    
    generator public static BufferedReader setBufferedReader(Object[] localObjs) {
    	if (??) {
    	    String filepath = (String) localObjs[0];
    	    return new BufferedReader(new FileReaderr(filepath));
    	}
    	if (??) {
    	    String csvLine = (String) localObjs[3];
    	    return new BufferedReader(new FileReaderr(csvLine));
    	}
    	if (??) {
    	    BufferedReader br = (BufferedReader) localObjs[6];
    	    String brLine = br.readLine();
    	    return new BufferedReader(new FileReaderr(brLine));
    	}
    	return null;
    }

    generator public static void otherFuncs(Object[] localObjs) {
    	if (??) {
    	    ArrayList<CsvRow> parsedRows = (ArrayList<CsvRow>) localObjs[2];
    	    CsvRow row = (CsvRow) localObjs[5];
    	    parsedRows.add(row);
    	}
    }
    
    generator public static void genStmts(Object[] localObjs[]) {
    	if (??) { localObjs[2] = setParsedCsvRows(localObjs); }
    	if (??) { localObjs[3] = setCsvLine(localObjs); }
    	if (??) { localObjs[4] = setParsedDocument(localObjs); }
    	if (??) { localObjs[5] = setRow(localObjs); }
    	if (??) { localObjs[6] = setBufferedReader(localObjs); }
    	if (??) { otherFuncs(localObjs); }
    	if (??) { genStmts(localObjs); }
    }
    
    generator public static CsvDocument genStmtsRet(Object[] localObjs[]) {
    	if (??) { localObjs[2] = setParsedCsvRows(localObjs); }
    	if (??) { localObjs[3] = setCsvLine(localObjs); }
    	if (??) { localObjs[4] = setParsedDocument(localObjs); }
    	if (??) { localObjs[5] = setRow(localObjs); }
    	if (??) { localObjs[6] = setBufferedReader(localObjs); }
    	if (??) { otherFuncs(localObjs); }	
    	if (??) { genStmts(localObjs); }
    	return (CsvDocument) localObjs[4];
    }
    
    /**
     * Reads the content of a csv file and transforms it into a CsvDocument object
     * @param filePath The absolute file path to the csv file
     * @param csvConfiguration a configuration object that dictates how the csv parsing will take place
     * @throws IOException
     */
    public static CsvDocument read(String filePath, CsvConfiguration csvConfiguration) throws IOException {
	Object[] localObjs = new Object[7];
	localObjs[0] = filePath;
	localObjs[1] = csvConfiguration;
	
	genStmts(localObjs);

	while (localObjs[3] != null) {
	    genStmts(localObjs);
	}

	return genStmtsRet(localObjs);
    }

    /**
     * Writes the content of the csv document to the given path on disk. If the file does not exist, it will
     * be created
     * @param document the csv document to be written to a file
     * @param savePath the absolute file path where the csv document will be written
     */
    public static boolean tryWriteToFile(CsvDocument document, String savePath){
        PrintStream out = new PrintStream(new FileOutputStream(savePath));
	out.print(document.toString());
	out.close();
	return true;
    }

    private static CsvRow parseCsvRow(CsvConfiguration csvConfiguration, String csvLine) {
        final String COMA_SEPARATOR = ",";
        String[] columns = csvLine.split(COMA_SEPARATOR);

        ArrayList<CsvColumn> csvColumns = new ArrayList<>();
        if(csvConfiguration.parseAllColumns()){
            for(int i=0; i<columns.length;i++){
                csvColumns.add(new CsvColumn(columns[i]));
            }
        }
        else{
           // for(int columnIndex : csvConfiguration.getColumnIndexesToParse()){
	   ArrayList<Integer> colInds = csvConfiguration.getColumnIndexesToParse();
	   int size = colInds.size();
	   for (int i = 0; i < size; i++) {
	       Integer columnIndexInt = colInds.get(i);
	       int columnIndex = columnIndexInt.intValue();
	       csvColumns.add(new CsvColumn(columns[columnIndex]));
           }
        }

        return new CsvRow(csvColumns);
    }
}
