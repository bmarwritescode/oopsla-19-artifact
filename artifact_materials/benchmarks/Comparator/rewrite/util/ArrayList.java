@rewriteClass
class ArrayList<E> {
    @alg
    Object add(String e);

    @alg
    @pure
    Object get(int i);

    @alg
    @pure
    Object[] getFirstI(int i, String[] es, int[] rs, int r);

    @alg
    Object set(int i, String e);

    @alg
    @pure
    void ensureCapacity();
    
    @alg
    @pure
    int size();

    @alg
    @pure
    int maxSize();
    
    @alg
    Object sort(Object b);

    @alg
    Object remove(int i);

    @alg
    Object addAll(ArrayList<E> e);
    
    rewrite int size(Object ArrayList()) {
    	return 0;
    }
    
    rewrite int size(Object add!(ArrayList a, String e)) {
    	return size(a)+1;
    }

    rewrite int size(Object set!(ArrayList a, int i, String e)) {
    	return size(a)+1;
    }

    rewrite int size(Object remove!(ArrayList a, int i)) {
    	return size(a)-1;
    }

    rewrite int size(Object addAll!(ArrayList a1, ArrayList a2)) {
	int s1 = size(a1);
	int s2 = size(a2);
    	return s1 + s2;
    }

    rewrite int size(Object sort!(ArrayList a, Object i)) {
	return size(a);
    }
    
    rewrite int maxSize(Object ArrayList()) {
    	return 0;
    }

    rewrite int maxSize(Object add!(ArrayList a, String e)) {
    	return maxSize(a)+1;
    }

    rewrite int maxSize(Object set!(ArrayList a, int i, String e)) {
    	return maxSize(a)+1;
    }

    rewrite int maxSize(Object remove!(ArrayList a, int i)) {
    	return maxSize(a);
    }

    rewrite int maxSize(Object addAll!(ArrayList a1, ArrayList a2)) {
    	int s1 = maxSize(a1);
    	int s2 = maxSize(a2);
    	return s1 + s2;
    }

    rewrite int maxSize(Object sort!(ArrayList a, Object i)) {
    	return maxSize(a);
    }

    rewrite Object get(Object remove!(ArrayList a, int i2),
		       int i1) {	
    	return (i2 <= i1) ? get(a, i1+1) : get(a, i1);
    }
    
    rewrite Object get(Object add!(ArrayList a, String e1),
		       int i) {
	int sz = size(a);
    	return (sz == i) ? e1 : get(a, i);
    }

    rewrite Object get(Object set!(ArrayList a, int j, String e),
		       int i) {
	if (i ==j) {
	    return e;
	} else {
	    if (i < j) {
		return get(a, i-1);
	    } else {
		return get(a, i);
	    }
	}
    }

    rewrite Object get(Object addAll!(ArrayList a1, ArrayList a2),
		       int i) {
	int s1 = size(a1);
	return (i < s1) ? get(a1, i) : get(a2, i-s1);
      
    }

    rewrite Object get(Object sort!(ArrayList a, Object b),
		       int i) {
	int j = maxSize(a)+1;
    	String[] firstI = getFirstI(a, i+1, new String[j+1], new int[j+1], 0);	
    	return firstI[i-1];
    }

    rewrite Object getFirstI(Object remove!(ArrayList a, int i2),
			     int i1,
			     String[] es,
			     int[] rs,
			     int r) {
	rs[r] = i2;
	r++;
	return getFirstI(a, i1, es, rs, r);
    }

    rewrite Object getFirstI(Object add!(ArrayList a, String e1),
			     int i,
			     String[] es,
			     int[] rs,
			     int r) {
    	int index = i-1;
    	String x = es[index];
	int sz = size(a);
	boolean removed = false;
	for(int j=0; j<r; j++) {
	    if (sz == j) {
		removed = true;
		j = r;
	    }
	}
	if (!removed) {
	    while ((x == null || x.compareTo(e1) > 0) && index >= 0) {
		es[index+1] = es[index];
		index = index - 1;
		if (index < 0) {
		    x = null;
		} else {
		    x = es[index];
		}
	    }

	    es[index+1] = e1;
	}
	
    	return getFirstI(a, i, es, rs, r);
    }

    rewrite Object getFirstI(Object set!(ArrayList a, int j, String e), int i, String[] es, int[] rs, int r) {
    	int index = i-1;
    	String x = es[index];
	boolean removed = false;
	for(int k=0; k<r; k++) {
	    if (k == j) {
		removed = true;
		k = r;
	    }
	}
	if (!removed) {
	    while ((x == null || x.compareTo(e) > 0) && index >= 0) {
		es[index+1] = es[index];
		index = index - 1;
		if (index < 0) {
		    x = null;
		} else {
		    x = es[index];
		}
	    }

	    es[index+1] = e;
	}
    	return getFirstI(a, i, es, rs, r);
    }

    rewrite Object getFirstI(Object addAll!(ArrayList a1, ArrayList a2), int i, String[] es, int[] rs, int r) {
	es = getFirstI(a1, i, es, rs, r);
	return getFirstI(a2, i, es, rs, r);
    }

    rewrite Object getFirstI(Object sort!(ArrayList a, Object b),
			     int i, String[] es, int[] rs, int r) {
    	return getFirstI(a, i, es, rs, r);
    }

    rewrite Object getFirstI(Object ArrayList(), int i, String[] es, int[] rs, int r) {
	return es;
    }

}
