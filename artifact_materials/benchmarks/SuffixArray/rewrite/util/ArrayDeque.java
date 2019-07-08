@rewriteClass
class ArrayDeque<E> {
    @alg
    @pure
    E peekFirst();

    @alg
    @pure
    E peekFirstHelp(int i, int j);
    
    @alg
    @pure
    boolean isEmpty();

    @alg
    E removeFirst();

    @alg
    @pure
    E peekLast();

    @alg
    @pure
    E peekLastHelp(int i, int j);
    
    @alg
    E removeLast();

    @alg
    void addLast(E e);

    @alg
    @pure
    int size();

    rewrite int size(ArrayDeque ArrayDeque()) {
	return 0;
    }
    
    rewrite int size(ArrayDeque addLast!(ArrayDeque d,E e)) {
    	return size(d)+1;
    }

    rewrite int size(ArrayDeque removeFirst!(ArrayDeque d, E e)) {
    	return size(d)==0 ? 0 : size(d)-1;
    }

    rewrite int size(ArrayDeque removeLast!(ArrayDeque d, E e)) {
    	return size(d)==0 ? 0 : size(d)-1;
    }

    rewrite E peekLast(ArrayDeque ArrayDeque()) {
	return null;
    }
    
    rewrite E peekLast(ArrayDeque addLast!(ArrayDeque d, E e)) {
    	return e;
    }

    rewrite E peekLast(ArrayDeque removeLast!(ArrayDeque d)) {
	return peekLastHelp(d, 0, 1);
    }

    rewrite E peekLastHelp(ArrayDeque removeLast!(ArrayDeque d),
			   int i,
			   int j) {
	return peekLastHelp(d,i,j+1);
    }

    rewrite E peekLast(ArrayDeque removeFirst!(ArrayDeque d)) {
	return peekLastHelp(d, 1, 0);
    }

    rewrite E peekLastHelp(ArrayDeque removeFirst!(ArrayDeque d),
			   int i,
			   int j) {
	return peekLastHelp(d, i+1, j);
    }
    
    rewrite E peekLastHelp(ArrayDeque addLast!(ArrayDeque d, E e),
			   int i,
			   int j) {
	if (j > 0) {
	    return peekLastHelp(d, i, j-1);
	} else if (i > 0) {
	    return size(d) == 0 ? null : e;
	} else {
	    return e;
	}
    }

    rewrite E peekFirst(ArrayDeque ArrayDeque()) {
	return null;
    }
    
    rewrite E peekFirst(ArrayDeque addLast!(ArrayDeque d, E e)) {
	return size(d)==0 ? e : peekFirst(d);
    }

    rewrite E peekFirst(ArrayDeque removeFirst!(ArrayDeque d)) {
	return peekFirstHelp(d, 1, 0);
    }

    rewrite E peekFirst(ArrayDeque removeLast!(ArrayDeque d)) {
	return peekFirstHelp(d, 0, 1);
    }

    rewrite E peekFirstHelp(ArrayDeque removeFirst!(ArrayDeque d),
			    int i, int j) {
	return peekFirstHelp(d, i+1, j);
    }

    rewrite E peekFirstHelp(ArrayDeque removeLast!(ArrayDeque d), int i, int j) {
	return peekFirstHelp(d, i, j+1);
    }

    rewrite E peekFirstHelp(ArrayDeque addLast!(ArrayDeque d, E e), int i, int j) {
	if (size(d) == i) {
	    if (j > 0) {
		return null;
	    }
	    return e;
	} else {
	    return peekFirstHelp(d, i, j-1);
	}
    }
}
