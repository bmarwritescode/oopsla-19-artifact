class ArtifactExample2 {

    public harness static void mn(int x) {
	Stack<Object> s = new Stack<Object>();

	Object o1 = new Object();
	Object o2 = new Object();

	Object toPush = {| o1, o2 |};

	s.push(toPush);
	
	Object popped = s.pop();
	assert popped.equals(o2);
    }
}

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

