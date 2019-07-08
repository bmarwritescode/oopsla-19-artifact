public class ArrayDeque <E> implements Deque{

    public class DequeNode {
	E val;
	DequeNode prev;
	DequeNode next;

	public DequeNode(E v, DequeNode p, DequeNode n) {
	    val = v;
	    prev = p;
	    next = n;
	}

	public DequeNode getNext() {
	    return next;
	}

	public DequeNode getPrev() {
	    return prev;
	}

	public E getVal() {
	    return val;
	}
	
	public void setNext(DequeNode n) {
	    next = n;
	}

	public void setPrev(DequeNode p) {
	    prev = p;
	}
    }

    DequeNode currentHead;
    DequeNode currentTail;
    int size;
    
    public ArrayDeque() {
	currentHead = null;
	currentTail = null;
	size = 0;
    }

    public int size() {
	return size;
    }

    public boolean isEmpty() {
	return size == 0;
    }

    public boolean add(E e) {
	DequeNode newNode = new DequeNode(e, null, null);
	if (isEmpty()) {
	    currentHead = newNode;
	} else {
	    newNode.setPrev(currentTail);
	    currentTail.setNext(newNode);
	}
	currentTail = newNode;
	size++;
	return true;
    }

    public void addLast(E e) {
	add(e);
    }

    public E getFirst() {
    	if (size > 0) assert currentHead != null;
    	if (currentHead != null) return currentHead.getVal();
    	return null;
    }

    public E getLast() {
    	if (size > 0) assert currentTail != null;
    	if (currentTail != null) return currentTail.getVal();
    	return null;
    }

    public E peek() {
	return getFirst();
    }

    public E peekFirst() {
	return getFirst();
    }

    public E peekLast() {
	return getLast();
    }

    public E removeFirst() {
	DequeNode head = currentHead;
	if (currentHead != null) {
	    DequeNode nextNode = currentHead.getNext();
	    if (nextNode != null) {
		nextNode.setPrev(null);
	    }
	    currentHead = nextNode;
	    if (size == 1) {
		currentTail = null;
	    }
	    size --;
	}
	return head.getVal();
    }
	
    public E removeLast() {
	DequeNode tail = currentTail;
	if (currentTail != null) {
	    DequeNode prevNode = currentTail.getPrev();
	    if (prevNode != null) {
		prevNode.setNext(null);
	    }
	    currentTail = prevNode;
	    if (size == 1) {
		currentHead = null;
	    }
	    size --;
	}
	return tail.getVal();
    }
}
