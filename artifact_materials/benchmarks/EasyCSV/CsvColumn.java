package easycsv;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static easycsv.CodeAssertion.verifyThat;

/**
 * Created by Dan Geabunea on 3/21/2015.
 */
public class CsvColumn {
    private String columnValue;

    /*
    Instantiates a CsvColumn with no columnValue (null)
     */
    public CsvColumn(){
    }

    public CsvColumn(String value) {
        this.columnValue = value;
    }

    public CsvColumn(int intValue){
        this.columnValue = Integer.toString(intValue);
    }

    public CsvColumn(boolean booleanValue){
        this.columnValue = Boolean.toString(booleanValue);
    }

    public boolean hasValue(){
        return columnValue == null;
    }

    public String getColumnValue() {
        return columnValue;
    }

    public int getInteger(){
            int parsedValue = Integer.parseInt(columnValue);
            return parsedValue;
    }

    public boolean getBoolean(){
        //check for true values
        if(columnValue.equalsIgnoreCase("true") ||
                columnValue.equalsIgnoreCase("t") ||
                columnValue.equalsIgnoreCase("yes") ||
                columnValue.equalsIgnoreCase("y") ||
                columnValue.equals("1")){
            return true;
        }

        //check for true values
        if(columnValue.equalsIgnoreCase("false") ||
                columnValue.equalsIgnoreCase("f") ||
                columnValue.equalsIgnoreCase("no") ||
                columnValue.equalsIgnoreCase("n") ||
                columnValue.equals("0")){
            return false;
        }
	return false;
    }

}
