@rewriteClass
class TreeSet<E> {
    @alg
    boolean add(E e);
    
    @alg
    Object clear();

    @alg
    @pure
    boolean contains(Object e);

    @alg
    @pure
    int size();

    rewrite int size(TreeSet TreeSet()) {
	return 0;
    }

    rewrite int size(TreeSet add!(TreeSet s, E e)) {
	boolean b = contains(s, e);
	if (b) {
	    return size(s);
	} else {
	    return size(s)+1;
	}
    }

    rewrite int size(TreeSet clear!(TreeSet s)) {
	return 0;
    }
    
    rewrite boolean add(TreeSet clear!(TreeSet s), E e) {
    	return true;
    }
    
    rewrite boolean add(TreeSet TreeSet(), E e) {
    	return true;
    }

    rewrite boolean add(TreeSet add!(TreeSet s, E e1), E e2) {
    	return e2.equals(e1) ? false : add(s, e2);
    }

    rewrite boolean contains(TreeSet add!(TreeSet s, E e1), Object e2) {
    	return e1.equals(e2) ? true : contains(s, e2);
    }

    rewrite boolean contains(TreeSet TreeSet(), Object e) {
	return false;
    }

    rewrite boolean contains(TreeSet clear!(TreeSet s), Object e) {
    	return false;
    }

}
