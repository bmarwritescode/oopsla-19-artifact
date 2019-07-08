@rewriteClass
class HashSet<E> {
    @alg
    boolean add(E e2);

    @alg
    boolean remove(Object e2);

    @alg
    @pure
    int size();

    rewrite boolean add(HashSet HashSet(), E e) {
    	return true;
    }

    rewrite boolean add(HashSet add!(HashSet h, E e1), E e2) {
    	return e2.equals(e1) ? false : add(h, e2);
    }

    rewrite boolean remove(HashSet HashSet(), Object e) {
    	return false;
    }

    rewrite boolean remove(HashSet add!(HashSet h, E e1), Object e2) {
    	return e2.equals(e1) ? true : remove(h, e2);
    }

    rewrite boolean remove(HashSet remove!(HashSet h, Object e1), Object e2) {
    	return e2.equals(e1) ? false : remove(h, e2);
    }

    rewrite int size(HashSet HashSet()) {
    	return 0;
    }
    
    rewrite int size(HashSet add!(HashSet h, E e1)) {
    	return size(h) + 1;
    }

    rewrite int size(HashSet remove!(HashSet h, Object e1)) {
	boolean b = remove(h, e1);
	if (b) {
	    return size(h)-1;
	} else {
	    return size(h);
	}
    }
}
