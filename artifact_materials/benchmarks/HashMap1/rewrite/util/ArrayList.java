@rewriteClass
class ArrayList<E>{
    @alg
    Object add(E e);

    @alg
    @pure
    E get(int i);

    @alg
    E set(int i, E e);

    @alg
    @pure
    void ensureCapacity(int n);
    
    @alg
    @pure
    int size();

    rewrite int size(ArrayList<E> ArrayList()) {
	return 0;
    }

    rewrite int size(ArrayList<E> add!(ArrayList a, E e)) {
	return size(a)+1;
    }

    rewrite int size(ArrayList<E> set!(ArrayList a, int i, E e)) {
	return size(a);
    }

    rewrite E get(ArrayList<E> add!(ArrayList a, E e1),
		  int i) {
	return size(a) == i ? e1 : get(a, i);
    }

    rewrite E get(ArrayList<E> set!(ArrayList a, int j, E e),
		  int i) {
	return i==j ? e : get(a, i);
    }
}
