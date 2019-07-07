@rewriteClass
class Stack<E> {
    @alg
    E push(E e);

    @alg
    E pop();

    // pop(push!(s, x)) = x
    rewrite E pop(Stack push!(Stack s, E e)) {
    	return e;
    }
}

class ArtifactExample {

    public harness static void mn(int x) {
	Stack<Object> s = new Stack<Object>();
	Object o1 = new Object();
	Object o2 = new Object();

	// push either o1 or o2 onto the stack
	Object toPush = {| o1, o2 |};
	s.push(toPush);

	// pop first element and assert it is o2
	Object popped = s.pop();
	assert popped.equals(o2);
    }
}
