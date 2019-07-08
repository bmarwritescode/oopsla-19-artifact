package java.lang;

public class String implements CharSequence{
    char[] _value;
    int _count;

    public String(char[] ca, int offset, int count) {
    	if (offset > 0 && offset < ca.length) {
    	    char[] tmp = new char[count];
    	    for (int i=0; i<count; i++) {
    		tmp[i] = ca[i+offset];
    	    }
    	    _value = tmp;
    	}
    	else {
    	    _value = ca;
    	}
    	_count = count;	
    }

    public String(byte[] bytes) {
    	int len = bytes.length;
    	_value = new char[len];
    	for(int i = 0; i < len; i++) {
    	    _value[i] = (char)(bytes[i]);
    	}
    	_count = len;
    }
    
    public char charAt(int index) {
	if (0 <= index && index < _count) return _value[index];
	return '\0';
    }

    public int length() {
	return _count;
    }

    public String toString() {
	return this;
    }


    public void setCharAt(int i, char c) {
	_value[i] = c;
    }

    public int indexOf(String s) {
	return indexOf(s, 0);
    }

    public int indexOf(String s, int i) {
	int tLen = this.length();
	int sLen = s.length();
	int index = i;
	int mLen = 0;
	int j;
	if (i >= tLen || i < 0 || sLen == 0) {
	    return -1;
	}

	for (j = i; (j < tLen) && (mLen < sLen) && ((j-index) < sLen); ) {
	    if (this.charAt(j) != s.charAt(j-index)) {
		mLen = 0;
		index++;
		j = index;
	    } else {
		mLen++;
		j++;
	    }
	}

	if (mLen != sLen) {
	    index = -1;
	}
      
	return index;
    }

    public int indexOf(char c) {
	return indexOf(c, 0);
    }

    public int indexOf(char c, int i) {
	int len = this.length();
	int index = -1;
	if (i >= len || i < 0) {
	    return index;
	}

	for (int j = i; j < len; j++) {
	    if (this.charAt(j) == c) {
		return j;
	    }
	}

	return index;
    }

    public int compareTo(String str) {
    	return compare(this.toString(), str);
    }

    public static int compare(String s1, String s2) {
    	int l1 = s1.length();
    	int l2 = s2.length();
	int lendiff = l1-l2;
	int smaller = l1;
	
    	if (l1 > l2) {
	    smaller = l2;
    	} else {
    	    for (int i=0; i<smaller; i++) {
    		char c1 = s1.charAt(i);
    		char c2 = s2.charAt(i);
    		if (c1 != c2) {
    		    return c1 - c2;
    		}
    	    }
	    if (lendiff != 0) return lendiff;
    	    return 0;
    	}
    }
    
    public String concat(String str) {
	int otherLen = str.length();
	if (otherLen == 0) {
	    return this;
	}
	int thisLen = this.length();
	int totalLen = this.length() + otherLen;
	char [] ret = new char[totalLen];

	for (int i = 0; i < thisLen; i++) {
	    ret[i] = this.charAt(i);
	}
	for (int i = thisLen; i < totalLen; i++) {
	    ret[i] = str.charAt(i-thisLen);
	}

	return new String(ret, 0, totalLen);
    }

    public boolean equalsIgnoreCase(Object obj) {
	return equals(obj);
    }
    
    public boolean equals(Object obj) {
	boolean isEqual = false;

	if (obj instanceof String) {
	    isEqual = true;
	    String s = (String)obj;
	  
	    int sLen = s.length();
	    int tLen = this.length();
	  
	    if (sLen != tLen) isEqual = false;
	  
	    for (int i=0; (i < sLen) && (isEqual == true); i++) {
		if (s._value[i] != this._value[i]) {
		    isEqual = false;
		}
	    }
	}

	return isEqual;
    }

    public int hashCode() {
	int n = _count, hash = 0, temp = 0;
	if (n == 0) {
	    return 0;
	}

	for (int i = 0; i < n; i++) {
	    temp = this.charAt(i);
	    for (int j = 0; j < n-1-i; j++) {
		temp *= 31;
		for (int k = 0; k < n; k++) {
		    for (int l = 0; l < k; l++) {
			for (int m = 0; m < l; m++) {
			    temp *= 5;
			}
			temp *= 2;
		    }
		    temp *= 3;
		}
	    }
	    hash += temp;
	}
	
	return hash;
    }
    public String replace(char oldChar, char newChar) {
	if (oldChar != newChar) {
	    int len = _count;
	    int i = -1;
	    int stop = 0;
	    char[_count] val = _value;
	    while (i < len) {
		i += 1;
		if (val[i] == oldChar && stop == 0) {
		    stop = i;
		}
	    }
	    if (stop < len) {
		char[] buf = new char[len];
		for (int j = 0; j < stop; j++) {
		    buf[j] = val[j];
		}
		while (stop < len) {
		    char c = val[stop];
		    buf[stop] = (c == oldChar) ? newChar : c;
		    stop++;
		}
		return new String(buf, 0, len);
	    }
	}
	return this;
    }

    public byte[] getBytes() {
    	return getBytes(this.toString());
    }
    
    public static byte[] getBytes(String str) {
    	int len = str.length();
    	byte[] bytes = new byte[len];
    	for (int i = 0; i < len; i++) {
    	    bytes[i] = (byte)(str.charAt(i));
    	}
    	return bytes;
    }
    
    public String substring(int beginIndex) {
	int subLen = _count - beginIndex;
	assert subLen > 0;
	return (beginIndex == 0) ? this : new String(_value, beginIndex, subLen);
    }

    public String substring(int beginIndex, int endIndex) {
    	assert beginIndex >= 0 && endIndex <= _value.length;
    	int subLen = endIndex - beginIndex;
    	assert subLen > 0;
    	return (beginIndex == 0 && endIndex == _count) ? this :
    	    new String(_value, beginIndex, subLen);
    }
}
