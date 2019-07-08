public class HashMap<K,V> extends Map {
    static final int DEFAULT_INITIAL_CAPACITY = 16;
    static final int RESIZE_FACTOR = 2;
    static final float RESIZE_RATIO = 0.75;
    Node[] elementData;
    int numPairs;
    int capacity;

    class Node<K,V> extends Object {
    	K key;
    	V value;
    	int hash;

    	public Node(K key,
		    V value,
		    int hash) {
    	    this.key = key;
    	    this.value = value;
    	    this.hash = hash;
    	}

	public K getKey() {
	    return this.key;
	}

	public V getValue() {
	    return this.value;
	}

	public int getHash() {
	    return this.hash;
	}

	public void setKey(K key) {
	    this.key = key;
	}

	public void setValue(V value) {
	    this.value = value;
	}

	public void setHash(int hash) {
	    this.hash = hash;
	}

	public boolean equals(Object o) {
	    if (o instanceof Node) {
		Node n = (Node) o;
		if (this.key == n.key) {
		    if (this.value == n.value) {
			if (this.hash == n.hash) {
			    return true;
			} else {
			    return false;
			}
		    } else {
			return false;
		    }
		} else {
		    return false;
		}
	    }
	}
    }

    public HashMap() {
    	this.elementData = new Node[DEFAULT_INITIAL_CAPACITY];
    	this.numPairs = 0;
    	this.capacity = DEFAULT_INITIAL_CAPACITY;
    }

    public void resize(int newSize) {
    	int i;
	int h;
	int hashMod;
    	Node<K, V> n;
    	Node[] oldElementData = elementData;
    	Node[] newElementData = new Node[newSize];
    	K k;
    	V v;

    	for (i = 0; i < capacity; i++) {
    	    if (oldElementData[i] != null) {
    		h = oldElementData[i].hash;
    		k = oldElementData[i].key;
    		v = oldElementData[i].value;
    		hashMod = h % newSize;
    		if (hashMod < 0) {
    		    hashMod += newSize;
    		}
    		newElementData[hashMod] = new Node<K,V>(k, v, h);
    	    }
    	}

    	this.elementData = newElementData;
    	this.capacity = newSize;
    }

    public boolean containsKey(K key) {
	V v = get(key);
    	return v != null;
    }

    public V get(K key) {
    	int hashMod = key.hashCode() % capacity;
    	if (hashMod < 0) {
    	    hashMod += capacity;
    	}
    	Node<K,V> node = elementData[hashMod];

    	if (node != null) {
    	    if (key.equals(node.key)) {
    		return node.value;
    	    }
    	}
    	return null;
    }

    public V remove(K key) {
    	V val = get(key);
    	int hashMod = key.hashCode() % capacity;
    	if (hashMod < 0) {
    	    hashMod += capacity;
    	}

    	elementData[hashMod] = null;

    	if (val != null) {
    	    numPairs --;
    	}

    	return val;
    }

    public V put(K key,
		 V value) {
    	int h = key.hashCode();
        return putVal(h, key, value);
    }

    private V putVal(int hash,
		     K key,
		     V value) {
    	int hashMod = hash % capacity;
    	if (hashMod < 0) {
    	    hashMod += capacity;
    	}
    	Node<K,V> node = elementData[hashMod];
	
    	if (node != null) {
    	    if (node.hash != hash ||
		!key.equals(node.key)) {
    		resize(hash+1);
    		hashMod = hash % capacity;
    		if (hashMod < 0) {
    		    hashMod += capacity;
    		}
    		node = elementData[hashMod];
    		numPairs ++;
    	    } 
    	    elementData[hashMod] = new Node<K,V>(key, value, hash);
    	    if (node != null) {
    		return node.value;
    	    } else {
    		return null;
    	    }
    	}
    	elementData[hashMod] = new Node<K,V>(key, value, hash);
    	numPairs ++;
    	return null;
    }
}
