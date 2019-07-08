package easycsv.test;

import easycsv.CsvColumn;
import easycsv.EasyCsvConversionException;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by Dan Geabunea on 3/21/2015.
 */
public class CsvColumnTest {

    public static void runTests() {
	the_getIntValue_method_when_column_is_integer_should_return_correct_result();
	the_getBooleanValue_method_when_column_can_be_converted_to_boolean_should_return_correct_result(); 
    }
    
    @Test
    public static void the_getIntValue_method_when_column_is_integer_should_return_correct_result(){
        //arrange
        String someIntValue = "20";
        CsvColumn someIntCsvColumn = new CsvColumn(someIntValue);

        //act
        int result = someIntCsvColumn.getInteger();

	assert 20 == result;
    }

    public static void the_getBooleanValue_method_when_column_can_be_converted_to_boolean_should_return_correct_result(){
	String trueValue = "true";
	String falseValue = "false";

	//arrange
        CsvColumn columnWithTrueResult = new CsvColumn(trueValue);
        CsvColumn columnWithFalseResult = new CsvColumn(falseValue);

        //act
        boolean resultThatShouldBeTrue = columnWithTrueResult.getBoolean();
        boolean resultThatShouldBeFalse = columnWithFalseResult.getBoolean();

	assert resultThatShouldBeTrue;
	assert !resultThatShouldBeFalse;	
    }
}
