public class Object {
    public static HashMap_NoHash<String, String> fs;

    public static boolean equals(Object a, Object b) {
    	if (a == null) {
    	    if (b == null) {
    		return true;
    	    }
    	    return false;
    	}
    	return a.equals(b);
    }

    public static int hashCode(Object o) {
        return o != null ? o.hashCode() : 0;
    }

    public boolean equals(Object obj) {
    	return this == obj;
    }

    // NOTE THAT THIS SHOULD BE OVERRIDDEN FOR ALL RELEVANT CLASSES
    public int hashCode() {
    	return 0;
    }
}
