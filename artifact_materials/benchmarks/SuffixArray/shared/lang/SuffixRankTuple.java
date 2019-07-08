public class SuffixRankTuple {
    int firstHalf, secondHalf, originalIndex;
    
    // Sort Suffix ranks first on the first half then the second half
    public int compareTo(SuffixRankTuple other) {
    	int cmp = Integer.compare(firstHalf, other.firstHalf);
    	if (cmp == 0) cmp = Integer.compare(secondHalf, other.secondHalf);
    	if (cmp == 0) return Integer.compare(originalIndex, other.originalIndex);
    	return cmp;
    }
    
    public String toString() {
    	return "Blah!";	  
    }
    
}
