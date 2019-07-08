public class TreeSet<E> implements Set{
    E[] set;
    int capacity;	
    int size;

    static int INITIAL_CAPACITY = 16;
    static int RESIZE_FACTOR = 2;
    
    public TreeSet() {
	set = new E[INITIAL_CAPACITY];
	size = 0;
	capacity = INITIAL_CAPACITY;
    }

    private void resize() {
	int new_size = capacity * RESIZE_FACTOR;
	E[] new_set = new E[new_size];
	for (int i=0; i<capacity; i++) {
	    new_set[i] = set[i];
	}
	set = new_set;
	capacity = capacity * RESIZE_FACTOR;
    }

    private void check_size() {
	if (size >= capacity) {
	    resize();
	}
    }
    
    public boolean add(E e) {
	if (contains(e) || e == null) {
	    return false;
	} else {
	    set[size] = e;
	    size ++;
	    check_size();
	    return true;
	}
    }

    private int get_Index(Object o) {
	for (int i=0; i<size; i++) {
	    if (o.equals(set[i])) {
		return i;
	    }
	}
	return -1;
    }
    
    public boolean contains(Object o) {
	return get_Index(o) >= 0;
    }
    
    public void clear() {
	set = new E[INITIAL_CAPACITY];
	size = 0;
	capacity = INITIAL_CAPACITY;
    }

    public int size() {
	return size;
    }    
}
