package easycsv;

import java.util.ArrayList;
import java.util.List;

import static easycsv.CodeAssertion.verifyThat;

/**
 * Created by Dan Geabunea on 3/26/2015.
 */
public class CsvConfiguration {
    private boolean skipHeader;
    private ArrayList<Integer> columnIndexesToParse;

    public CsvConfiguration(){
        this.skipHeader = false;
        this.columnIndexesToParse = new ArrayList<>();
    }

    public boolean skipHeader() {
        return skipHeader;
    }

    public void setSkipHeader(boolean skipHeader) {
        this.skipHeader = skipHeader;
    }

    public ArrayList<Integer> getColumnIndexesToParse() {
        return columnIndexesToParse;
    }

    public void setColumnIndexesToParse(ArrayList<Integer> columnIndexesToParse) {
        this.columnIndexesToParse = columnIndexesToParse;
    }

    public void setColumnIndexesToParse(int[] columnIndexesToParse) {
	int size = columnIndexesToParse.length;
        for (int i = 0; i < size; i++) {
	    int columnIndex = columnIndexesToParse[i];
            this.columnIndexesToParse.add(new Integer(columnIndex));
        }
    }

    public boolean parseAllColumns(){
        return this.columnIndexesToParse.size() == 0;
    }
}
