package easycsv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static easycsv.CodeAssertion.verifyThat;

/**
 * Created by Dan Geabunea on 3/24/15.
 */
public class CsvRow {
    private ArrayList<CsvColumn> columns;

    protected CsvRow(){
        columns = new ArrayList<>();
    }
    public CsvRow(String v1, String v2) {
	columns = new ArrayList<>();

	columns.add(new CsvColumn(v1));
	columns.add(new CsvColumn(v2));
    }

    public CsvRow(CsvColumn v1, CsvColumn v2) {
	columns = new ArrayList<>();

	columns.add(v1);
	columns.add(v2);
    }
    
    /**
     * Create a Csv Row from a list of Csv Columns
     * @param rowColumns the columns that will make up the row
     * @throws easycsv.EasyCsvAssertionRuntimeException when rowColumns is null or empty
     */
    public CsvRow(ArrayList<CsvColumn> rowColumns){
        columns = new ArrayList<>();
        columns = rowColumns;
    }

    public CsvColumn getColumnAtIndex(int index){
        return columns.get(index);
    }

    public int getNumberOfColumns(){
        return columns.size();
    }

    public boolean isEmpty(){
        return columns.size() == 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i < this.columns.size(); i++){
	    CsvColumn c = this.columns.get(i);
            sb.append(c.getColumnValue());
            if(i < columns.size()-1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
