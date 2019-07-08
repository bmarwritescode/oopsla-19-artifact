@rewriteClass
class ArrayList<E> {
    @alg
    boolean add(E e);

    @alg
    E get(int i);

    @alg
    @pure
    int size();

    rewrite int size(ArrayList ArrayList()) {
	return 0;
    }

    rewrite int size(ArrayList add!(ArrayList a, E e)) {
	return size(a)+1;
    }

    rewrite E get(ArrayList add!(ArrayList a, E e1),
		  int i) {
	return size(a) == i ? e1 : get(a, i);
    }
}
