import java.util.ArrayList;

public class HashTable<K, V> {

    protected final double maxLoadFactor;
    protected final int capacityGrowth;
    protected final int initialCapacity;
    protected int size;
    protected int currentCapacity;
    protected ArrayList<HashTableNode<K, V>> buckets;

    // Initialize with desired initial number of buckets
    public HashTable(int initialCapacity) {
        this.initialCapacity = initialCapacity;
	this.capacityGrowth = 2;
	this.maxLoadFactor = 0.99;
	this.buckets = new ArrayList<>();
        resetHashTable();
    }

    protected void resetHashTable() {
        this.size = 0;
        this.currentCapacity = this.initialCapacity;
        this.buckets = new ArrayList<>();
        for (int i = 0; i < this.currentCapacity; i++) {
	    this.buckets.add(null);
        }
    }

    // Returns the value stored under the given key, if found
    public V get(K key) {
	Object[] localObjs = new Object[3];
	localObjs[0] = key;
	int[] localInts = new int[1];
	
	stmts(localObjs, localInts);

	if (guard(localObjs, localInts)) {
	    return stmtsRetV(localObjs, localInts);	    
	}
	
	return stmtsRetV(localObjs, localInts);
    }

    generator V stmtsRetV(Object[] localObjs, int[] localInts) {
	if (??) { stmts(localObjs, localInts); }
	if (??) {
	    HashTableNode<K, V> result = (HashTableNode<K, V>) localObjs[1];
	    return result.getValue();
	}
	return null;
    }
    
    generator boolean guard(Object[] localObjs, int[] localInts) {
    	boolean comp = false;
    	if (??) {
    	    int i1 = genInt(localObjs, localInts);
    	    int i2 = genInt(localObjs, localInts);
    	    comp = {| i1 < i2, i1 <= i2, i1 == i2 |};
    	}
    	if (??) {
    	    comp = localObjs[??] == null;			     
    	}
    	if (??) {
    	    K k1 = genK(localObjs, localInts);
    	    K k2 = genK(localObjs, localInts);
    	    comp = k1.equals(k2);
    	}
    	if (??) {
    	    HashTableNode<> current = genHashTableNode(localObjs, localInts);
    	    comp = current.getNext() == null;
    	}
    	return {| comp, !comp |};
    }
    
    generator int genInt(Object[] localObjs, int[] localInts) {
    	if (??) {	    
    	    return {| capacityGrowth, initialCapacity, size, currentCapacity, ?? |};
    	}
    	if (??) {
    	    return buckets.size();
    	}
    	if (??) {
    	    return size()+??;
    	}
    	if (??) {
    	    return localInts[0];
    	}
    	if (??) {
    	    K k1 = genK(localObjs, localInts);
    	    return getBucketIndex(k1);
    	}
    	return 0;
    }
 
    generator HashTableNode<K,V> genHashTableNode(Object[] localObjs, int[] localInts) {
    	if (??) {
    	    K k2 = genK(localObjs, localInts);
    	    V v2 = genV(localObjs, localInts);
	    
    	    return new HashTableNode<>(k2, v2); 
    	}
    	if (??) {
    	    K k = (K) genK(localObjs, localInts);
    	    return getNodeWithKey(k);
    	}
    	if (??) {
    	    int i = genInt(localObjs, localInts);
    	    return buckets.get(i);
    	}
    	if (??) {
    	    return localObjs[2];
    	}
    	if (??) {
    	    return localObjs[3];
    	}
    	if (??) {
    	    HashTableNode<> current = genHashTableNode(localObjs, localInts);
    	    return current.getNext();
    	}
    	return null;
    }

    generator K genK(Object[] localObjs, int[] localInts) {
    	if (??) {	    
    	    HashTableNode<> current = genHashTableNode(localObjs, localInts);
    	    return current.getKey();
    	}
    	if (??) {
    	    return (K) localObjs[0];
    	}
    	return null;
    }
    
    generator V genV(Object[] localObjs, int[] localInts) {
    	if (??) {
    	    HashTableNode<K, V> result = genHashTableNode(localObjs, localInts);
    	    return result.getValue();
    	}
    	if (??) {
    	    return ((V) localObjs[1]);
    	}
    	return null;
    }
    
    generator void voidFuncs(Object[] localObjs, int[] localInts) {
	if (??) {
	    V v = genV(localObjs, localInts);	    
	    buckets.add(v);
	}
    	if (??) {
    	    HashTableNode<> n = genHashTableNode(localObjs, localInts);
    	    int i = genInt(localObjs, localInts);
            buckets.set(i, n);
    	}
    	if (??) {
    	    HashTableNode<> current = genHashTableNode(localObjs, localInts);
    	    V v = genV(localObjs, localInts);
    	    current.setValue(v);
    	}
    	if (??) {
    	    HashTableNode<> current = genHashTableNode(localObjs, localInts);	    
    	    HashTableNode<> newNode = genHashTableNode(localObjs, localInts);
    	    current.setNext(newNode);
    	}

    }
    
    generator void stmts(Object[] localObjs, int[] localInts) {
    	if (??) { capacityGrowth = genInt(localObjs, localInts); }
    	if (??) { initialCapacity = genInt(localObjs, localInts); }
    	if (??) { size = genInt(localObjs, localInts); }
    	if (??) { localObjs[1] = genHashTableNode(localObjs, localInts); }
    	if (??) { localObjs[2] = genHashTableNode(localObjs, localInts); }
    	if (??) { localObjs[3] = genHashTableNode(localObjs, localInts); }
    	if (??) { voidFuncs(localObjs, localInts); }
    	if (??) { stmts(localObjs, localInts); }	
    }

    // inserts a Key, Value pair into the table
    public void put(K key, V value) throws IllegalArgumentException {
	int[] localInts = new int[2];
	Object[] localObjs = new Object[5];
	localObjs[0] = key;
	localObjs[1] = value;

	ensureCapacity(size() + 1);

	K k1 = (K) localObjs[0];
	localInts[0] = getBucketIndex(k1);
	
	K k2 = (K) localObjs[0];
	V v2 = (V) localObjs[1];
	
	localObjs[2] = new HashTableNode<>(k2, v2); 
	localObjs[3] = buckets.get(localInts[0]);
	
        if (localObjs[3] == null) {
	    HashTableNode<> n = localObjs[2];
            buckets.set(localInts[0], n);
            this.size++;
            return;
        }

    	while (localObjs[3] != null) {
	    HashTableNode<> current = localObjs[3];
    	    K k = current.getKey();
	    K k3 = (K) localObjs[0];
            if (k.equals(k3)) {
		V v3 = (V) localObjs[1];
                current.setValue(v3);
    		return;
            }
            else if (current.getNext() == null) {
		HashTableNode<> newNode = localObjs[2];
                current.setNext(newNode);
                this.size++;
    		return;
            }
	    localObjs[3] = current.getNext();
        }
    }

    // Removes the Key, Value pair based on the provided Key
    public void remove(K key) {
        if (size() == 0 || key == null) {
            return;
        }

        int bucketIndex = getBucketIndex(key);
        HashTableNode<K, V> current = buckets.get(bucketIndex);
        HashTableNode<K, V> previous = null;

        // Traverse the list inside the bucket until match is found or end of list reached
        while (current != null) {
    	    K k = current.getKey();
            if (k.equals(key)) {
                // Handle case when node is first in bucket
                if (previous == null) {
                    // If there is a next node, set next node as first in bucket
                    if (current.getNext() != null) {
                        buckets.set(bucketIndex, current.getNext());
                    }
                    // If there is no other node in list, simply set bucket to null
                    else {
                        buckets.set(bucketIndex, null);
                    }
                }
                // Handle case when node is not in first position
                else {
                    // If it's the last node in the list, set previous's next as null
                    if (current.getNext() == null) {
                        previous.setNext(null);
                    }
                    // If it's anywhere else in the list, connect previous and next
                    else {
                        previous.setNext(current.getNext());
                    }
                }

                // We're done removing the node, diminish size and return
                this.size--;
                return;
            }

            previous = current;
            current = current.getNext();
        }
    }

    // Returns array of all values in table
    // Traverse each bucket and add value to results
    public V[] values() {
    	V[] values = (V[]) new Object[size()];
        if (size() > 0) {
            int index = 0;
    	    for (int i = 0; i < buckets.size(); i++) {
    		HashTableNode<K, V> current = buckets.get(i);
    		while (current != null) {
    		    values[index] = current.getValue();
    		    index++;
    		    current = current.getNext();
    		}
            }
        }
        return values;
    }

    // Returns array of all keys in table
    // Traverse each bucket and add key to results
    public K[] keys() {
        K[] keys = (K[]) new Object[size()];
        if (size() > 0) {
            int index = 0;
            for (int i = 0; i < buckets.size(); i++) {
                HashTableNode<K, V> current = buckets.get(i);
                while (current != null) {
                    keys[index] = current.getKey();
                    index++;
                    current = current.getNext();
                }
            }
         }
        return keys;
    }

    // Returns if some node in table contains provided key
    public boolean containsKey(K key) {
        HashTableNode<K, V> result = getNodeWithKey(key);
        return result != null;
    }

    // Returns if some node in table contains provided value
    public boolean containsValue(V value) {
        HashTableNode<K, V> result = getNodeWithValue(value);
        return result != null;
    }

    // Returns the total number of Key, Value pairs in the table
    public int size() {
        return this.size;
    }

    // Empty out the table
    public void clear() {
        resetHashTable();
    }

    // Hash the key and find the appropriate bucket index
    protected int getBucketIndex(K key) {
    	// leaving this method alone b/c it's a translation of the comments below
    	int h = key.hashCode();

	return h % this.currentCapacity;	
    }

    // Returns the node with the matching key, if any
    // Searches only inside the appropriate bucket
    private HashTableNode<K, V> getNodeWithKey(K key) {
        if (size() == 0 || key == null) {
            return null;
        }

        int bucketIndex = getBucketIndex(key);
        HashTableNode<K, V> current = buckets.get(bucketIndex);
        while (current != null) {
    	    K k = current.getKey();
            if (k.equals(key)) {
                return current;
            }
            current = current.getNext();
        }

        return null;
    }

    // Returns the node with the matching value, if any
    // Must search the entire table since the value doesn't give us
    // a clue about a possible bucket
    private HashTableNode<K, V> getNodeWithValue(V value) {
        if (size() == 0) {
            return null;
        }

    	// This seems too tough for Sketch
        for (int i = 0; i < buckets.size(); i++) {
            HashTableNode<K, V> current = buckets.get(i);

            while (current != null) {
    		V v = current.getValue();
                if (v.equals(value)) {
                    return current;
                }
                current = current.getNext();
            }
        }

        return null;
    }

    protected void ensureCapacity(int intendedCapacity) {
        double loadFactor = (double) intendedCapacity / (double) currentCapacity;
        // If we're within the load limit, return early, it's all good.
        if (loadFactor < maxLoadFactor) {
            return;
        }

        // Otherwise, ensure we will be within limits
        int newCapacity = currentCapacity * capacityGrowth;
        buckets.ensureCapacity(newCapacity);

        // Initialize buckets
    	for (int i = this.currentCapacity; i < newCapacity; i++) {
    	    this.buckets.add(null);
    	}
	
    	currentCapacity = newCapacity;
    }
    
}
