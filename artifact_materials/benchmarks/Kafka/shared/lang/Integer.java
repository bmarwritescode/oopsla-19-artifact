package java.lang;

public class Integer {
    private final int value;
    
    public Integer(int value) {
    	this.value = value;
    }

    public int intValue() {
    	return this.value;
    }

    public int compareTo(Integer anotherInteger) {
        return compare(this.value, anotherInteger.intValue());
    }

    public static int compare(int x, int y) {
        return (x < y) ? -1 : ((x == y) ? 0 : 1);
    }

    public boolean equals(Object obj) {
    	if (obj instanceof Integer) {
    	    return this.value == ((Integer)obj).intValue();
    	}
    	return false;
    }

    public int hashCode() {
	return this.value;
    }

    public String toString() {
    	return toString(this.value);
    }

    public static String toString(int i) {

    	if (i == 0) {
    	    return "0";
    	}

    	int index = 0, temp = i, j;
    	char [32] ret = new char[32];
    	char [32] ret2 = new char[32];
    	char [] nums = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    	if (temp < 0) {
    	    temp = temp * -1;
    	}

    	while (temp > 0) {
    	    ret[index] = nums[temp%10];
    	    temp = temp / 10;
	    index = index + 1;	    
    	}

    	if (i < 0) {
    	    ret[index] = '-';
	    index = index + 1;
    	}
       
    	int size = index;

    	for (j=0; j<size; j++) {
    	    ret2[j] = ret[index-1];
    	    index = index -1;
    	}

    	return new String(ret2, 0, size);
    }
    
}
