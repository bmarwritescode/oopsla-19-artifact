@rewriteClass
class ArrayList<E> {
    @alg
    boolean add(Object e);

    @alg
    E get(int i);

    @alg
    E set(int i, E e);

    @alg
    @pure
    void ensureCapacity(int n);
    
    @alg
    @pure
    int size();

    rewrite int size(ArrayList ArrayList()) {
	return 0;
    }

    rewrite int size(ArrayList add!(ArrayList a, E e)) {
	return size(a)+1;
    }

    rewrite int size(ArrayList set!(ArrayList a, int i, E e)) {
	return size(a);
    }

    rewrite E get(ArrayList add!(ArrayList a, E e1),
		  int i) {
	return size(a) == i-1 ? e1 : get(a, i);
    }

    rewrite E get(ArrayList set!(ArrayList a, int j, E e),
		  int i) {
	return i==j ? e : get(a, i);
    }

    rewrite E get(ArrayList ArrayList(),
		  int i) {
	return null;
    }
}
