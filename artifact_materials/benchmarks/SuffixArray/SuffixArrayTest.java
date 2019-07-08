public class SuffixArrayTest {

    harness public void main() {
	testLRS();
    }
    
    @Test
    public void testLRS() {
	String s = "aabaab";
	SuffixArray sa = new SuffixArray(s);
	TreeSet <String> lrss = sa.lrs();
	assert 1 == lrss.size();
	assert lrss.contains("aab");

	s = "babababa";
	sa = new SuffixArray(s);
	lrss = sa.lrs();
	assert lrss.size() == 1;
	assert lrss.contains("bababa");
    
	s = "abcba";
	sa = new SuffixArray(s);
	lrss = sa.lrs();
	assert lrss.size() == 2;
	assert lrss.contains("a");
	assert lrss.contains("b");
	
	s = "abccdd";
	sa = new SuffixArray(s);
	lrss = sa.lrs();
	assert lrss.size() == 2;
	assert lrss.contains("c");
	assert lrss.contains("d");    
	
	s = "aaab";
	sa = new SuffixArray(s);
	lrss = sa.lrs();
	assert lrss.size() == 1;
	assert lrss.contains("aa");
	
	s = "ababa";	
	sa = new SuffixArray(s);
	lrss = sa.lrs();
	assert lrss.size() == 1;
	assert lrss.contains("aba");    	
    }
}
