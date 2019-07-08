package easycsv.test;

import easycsv.CsvColumn;
import easycsv.CsvConfiguration;
import easycsv.CsvDocument;
import easycsv.CsvRow;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.Test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Dan Geabunea on 3/26/2015.
 */
public class CsvDocumentTest {

    public static void runTests() {
	with_no_config_options_should_parse_csv_file_and_create_csv_document();	
    }
    @Test
    public static void with_no_config_options_should_parse_csv_file_and_create_csv_document() throws IOException {
	String csvPath = "A,B\n1,T\n2,F";
	
        CsvDocument document = CsvDocument.read(csvPath);

	ArrayList<CsvRow> rs = document.getCsvRows();

	CsvRow headers = rs.get(0);
	CsvRow r1 = rs.get(1);
	CsvRow r2 = rs.get(2);	

	String hstr = headers.toString();
	String r1str = r1.toString();
	String r2str = r2.toString();

	assert hstr.equals("A,B");
	assert r1str.equals("1,T");
	assert r2str.equals("2,F");	

	csvPath = "C,D";
	
        document = CsvDocument.read(csvPath);

	rs = document.getCsvRows();

	headers = rs.get(0);

	hstr = headers.toString();

	assert hstr.equals("C,D");
    }

}
