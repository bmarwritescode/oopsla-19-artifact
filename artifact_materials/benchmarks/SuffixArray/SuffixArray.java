/**
 *
 * Generally speaking, suffix arrays are used to do multiple queries 
 * efficiently on one piece of data rather than to do one operation 
 * then move on to another piece of text.
 *
 * Good suffix array read: http://www.cs.yale.edu/homes/aspnes/pinewiki/SuffixArrays.html
 *
 * @author William Fiset, william.alexandre.fiset@gmail.com
 **/

import java.util.*;

class SuffixArray {

    // Size of the suffix array
    int N; 
    
    // T is the text
    int[] T;

    // Suffix array. Contains the indexes of sorted suffixes.
    int[] sa;
    
    // Contains Longest Common Prefix (LCP) count between adjacent suffixes.
    // lcp[i] = longestCommonPrefixLength( suffixes[i], suffixes[i+1] ).
    // Also, LCP[len-1] = 0
    int [] lcp;

    public int[] clone(int[] arr) {
  	int l = arr.length;
  	int[] arr_cp = new int[l];
  	for(int i=0; i<l; i++) {
  	    arr_cp[i] = arr[i];
  	}
  	return arr_cp;
    }

    public SuffixArray(String text) {
	this(toIntArray(text));
    }

    private static String intArrToString(int [] text) {
  	char[] tmp = new char[text.length];      
  	for (int i=0; i<text.length; i++) {
  	    tmp[i] = (char) text[i];
  	}
	
  	// Extract part of the suffix we need to compare
        return new String(tmp, 0, text.length);
    }
    
    private static int[] toIntArray(String s) {
	int[] text = new int[s.length()];
	for(int i=0;i<s.length();i++)text[i] = s.charAt(i);
	return text;
    }

    public SuffixArray(int[] text) {
	T = clone(text);
	N = text.length;    
	construct();
	kasai();
    }

    // Construct a suffix array in O(nlog^2(n))
    public void construct() {
	
	sa = new int[N];

	// Maintain suffix ranks in both a matrix with two rows containing the
	// current and last rank information as well as some sortable rank objects
	// CHANGE
	// int[][] suffixRanks = new int[2][N];
	TwoDArray suffixRanks = new TwoDArray(2, N);
	SuffixRankTuple[] ranks = new SuffixRankTuple[N];
	
	// Assign a numerical value to each character in the text
	for (int i = 0; i < N; i++) {
	    suffixRanks.set(0, i, T[i]);
	    ranks[i] = new SuffixRankTuple();
	}

	// O(logn)
	for(int pos = 1; pos < N; pos *= 2) {

	    for(int i = 0; i < N; i++) {
		SuffixRankTuple suffixRank = ranks[i];
		suffixRank.firstHalf  = suffixRanks.get(0, i);
		suffixRank.secondHalf = i+pos < N ? suffixRanks.get(0, i+pos) : -1;
		suffixRank.originalIndex = i;
	    }

	    // O(nlogn)
	    ranks = Arrays.sort(ranks, ranks.length);

	    int newRank = 0;
	    suffixRanks.set(1, ranks[0].originalIndex, 0);

	    for (int i = 1; i < N; i++ ) {
		
		SuffixRankTuple lastSuffixRank = ranks[i-1];
		SuffixRankTuple currSuffixRank = ranks[i];
  
		// If the first half differs from the second half
		if (currSuffixRank.firstHalf  != lastSuffixRank.firstHalf ||
		    currSuffixRank.secondHalf != lastSuffixRank.secondHalf)
		    newRank++;

		suffixRanks.set(1, currSuffixRank.originalIndex, newRank);

	    }
      
	    // Place top row (current row) to be the last row
	    suffixRanks.setRow(0, suffixRanks.getRow(1));

	    if (newRank == N-1) pos = N;      

	}

	// Fill suffix array
	for (int i = 0; i < N; i++) {
	    sa[i] = ranks[i].originalIndex;
	    ranks[i] = null;
	}

	// Cleanup
	suffixRanks = null;
	ranks = null;
    }

    // Constructs the LCP (longest common prefix) array in linear time - O(n)
    // http://www.mi.fu-berlin.de/wiki/pub/ABI/RnaSeqP4/suffix-array.pdf
    private void kasai() {

	lcp = new int[N];
    
	// Compute inverse index values
	int [] inv = new int[N];
	for (int i = 0; i < N; i++)
	    inv[sa[i]] = i;

	// Current lcp length
	int len = 0;

	for (int i = 0; i < N; i++) {
	    if (inv[i] > 0) {

		// Get the index of where the suffix below is
		int k = sa[inv[i]-1];
		// Compute lcp length. For most loops this is O(1)
		while( (i + len < N) && (k + len < N) && T[i+len] == T[k+len] )
		    len++;

		lcp[inv[i]-1] = len;
		if (len > 0) len--;
		
	    }
	}
	
    }

    // Finds the LRS(s) (Longest Repeated Substring) that occurs in a string.
    // Traditionally we are only interested in substrings that appear at
    // least twice, so this method returns an empty set if this is the case.
    // @return an ordered set of longest repeated substrings
    public TreeSet <String> lrs() {
	int max_len = 0;
	TreeSet <String> lrss = new TreeSet<>();

	char[] tmp = new char[T.length];      
	for (int i=0; i<T.length; i++) {
	    tmp[i] = (char) T[i];
	}
    
	for (int i = 0; i < N; i++) {
	    if (lcp[i] > 0 && lcp[i] >= max_len) {
        
		// We found a longer LRS
		if (lcp[i] > max_len ) {
		    lrss.clear();
		}
		    
		// Append substring to the list and update max
		max_len = lcp[i];
		lrss.add( new String(tmp, sa[i], max_len) );
	    }
	}

	return lrss;	
    }
}

