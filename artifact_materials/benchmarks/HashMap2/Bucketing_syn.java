package HashingTechnique;

import java.util.ArrayList;

import Interfaing.HashTable;

public class Bucketing<K, V> implements HashTable<K, V> {
    private Pair<K, V>[] bucketHash;
    private ArrayList<Pair<K, V>> overflow;
    private int[] sizeBucket;
    private int numberOfElements;
    private int index, integerKey;
    private int size;
    private int mod, numberOfSlots;
    private ArrayList<K> ilterator;
    double rehash;

    public Bucketing() {
	size = 5;
	mod = 2; 
	numberOfSlots = 2; 
	bucketHash = new Pair[size];
	overflow = new ArrayList<>();
	sizeBucket = new int[2];
	numberOfElements = 0;
    }

    public int sizeOfArray() {
	return size;
    }

    generator boolean guard(int[] localInts, Object[] localObjs) {
	boolean comp = false;
	if (??) {
	    int i1 = genInt(localInts, localObjs);
	    int i2 = genInt(localInts, localObjs);
	    comp = {| i1 < i2, i1 <= i2, i1 == i2 |};
	}
	if (??) {
	    comp = localObjs[??] == null;			     
	}
	if (??) {
	    Object o1 = localObjs[??];
	    Object o2 = localObjs[??];
	    return o1.equals(o2);
	}
	return {| comp, !comp |};
    }
    
    generator int genInt(int[] localInts, Object[] localObjs) {
	if (??) {
	    int i1 = localInts[??];
	    int i2 = localInts[??];
	    int i3 = {| i2, ?? |};
	    return {| i3, i1*i3, i1+i3, i1-i3, i1%i3 |};
	}
	if (??) {
	    ArrayList<Pair<K,V>> bs = (ArrayList<Pair<K,V>>) localObjs[3];
	    return bs.size();
	}
	if (??) {
	    K keyy = (K) localObjs[0];
	    return keyy.hashCode();
	}
	return ??;
    }

    generator K genK(int[] localInts, Object[] localObjs) {
	if (??) { return (K) localObjs[0]; }
	if (??) { return (K) localObjs[2]; }
	if (??) {
	    int i = genInt(localInts, localObjs);
	    Pair<K,V> tmp = (Pair<K,V>) bucketHash[i];
	    return tmp.key;
	}
	return null;
    }
    
    generator V genV(int[] localInts, Object[] localObjs) {
	if (??) {
	    int i = genInt(localInts, localObjs);
	    Pair<K,V> tmp = (Pair<K,V>) bucketHash[i];
	    return tmp.value;
	}
	return null;
    }

    generator Pair<K,V> genPair(int[] localInts, Object[] localObjs) {
	if (??) {
	    int i = genInt(localInts, localObjs);
	    return (Pair<K,V>) bucketHash[i];
	}
	return null;
    }
    
    generator void stmts(int[] localInts, Object[] localObjs) {
	if (??) { localInts[??] = genInt(localInts, localObjs); }
	if (??) { localObjs[??] = genV(localInts, localObjs); }
	if (??) { stmts(localInts, localObjs); }
    }
    
    public void rehashng() {
    	ArrayList<Pair<K, V>> temp1 = new ArrayList<>();

    	for (int i = 0; i < 2; i++) {
    	    for (int j = 0; j < sizeBucket[i]; j++) {
    		int index = i * numberOfSlots + j;
    		temp1.add(new Pair(bucketHash[index].key,bucketHash[index].value));
    	    }
    	}
	int sz = overflow.size();
    	for (int i = 0; i < sz; i++) {
	    Pair<K,V> tmp = overflow.get(i);
	    temp1.add(tmp);
    	}

    	size *= 4;
    	numberOfSlots *= 2;
    	mod *= 2;
    	bucketHash = new Pair[size];
    	sizeBucket = new int[mod];
    	numberOfElements = ??;

	int sz2 = temp1.size();
    	for (int i = 0; i < sz2; i++) {
	    Pair<K,V> tmp = temp1.get(i);
	    K key = tmp.key;
	    V val = tmp.value;			
	    put(key, val);
    	}
    }

    public void put(K key, V value) {
	delete(key);
    	integerKey = key.hashCode() % mod;
	if (integerKey < 0) { integerKey *= -1; }

    	// check if there is a place in buckting array or not
    	if (sizeBucket[integerKey] != numberOfSlots) {
    	    int index = numberOfSlots * integerKey + sizeBucket[integerKey];
    	    bucketHash[index] = new Pair(key, value);
    	    sizeBucket[integerKey] = sizeBucket[integerKey] + 1;

    	}
	else {
    	    overflow.add(new Pair(key, value));
    	}
    	numberOfElements++;
    	rehash = (double) numberOfElements / (double) size;
    	if (rehash > 0.75)
    	    rehashng();
    }

    public V get(K key) {
	int[] localInts = new int[5];
	localInts[0] = integerKey;
	localInts[1] = mod;
	localInts[2] = numberOfSlots;
	localInts[3] = index;
	Object[] localObjs = new Object[4];
	localObjs[0] = key;
	localObjs[3] = overflow;
	    
	if (guard(localInts, localObjs)) {
	    return genV(localInts, localObjs);
	}
	stmts(localInts, localObjs);
	if (guard(localInts, localObjs)) {
	    stmts(localInts, localObjs);
	}
	stmts(localInts, localObjs);
	for (int i = localInts[3]; i < localInts[3]+sizeBucket[localInts[0]]; i++) {
	    localInts[4] = i;
	    stmts(localInts, localObjs);
	    if (guard(localInts, localObjs)) {
	    	return genV(localInts, localObjs);
	    }
	}
	if (guard(localInts, localObjs)) {
	    ArrayList<Pair<K,V>> os = (ArrayList<Pair<K,V>>) localObjs[3];
	    for (int i = 0; i < os.size(); i++) {
	    	localInts[4] = i;
	    	stmts(localInts, localObjs);
	    	if(guard(localInts, localObjs)) {
		    return genV(localInts, localObjs);
		}
	    }
	}
	return null;
    }
    
    public void delete(K key) {	
    	integerKey = key.hashCode() % mod;
	if (integerKey < 0) { integerKey *= -1; }

    	index = numberOfSlots * integerKey;
    	boolean flag = false;
	int g3 = {| size, mod, numberOfSlots, numberOfElements, integerKey, index |};
	int add = index + sizeBucket[integerKey];	
	int g4 = {| size, mod, numberOfSlots, numberOfElements, integerKey, index, add |}; 
    	for (int i = index; i < index+sizeBucket[integerKey]; i++) {
	    Pair<K,V> tmp = bucketHash[i];
	    K tmp_key = tmp.key;
	    boolean b = tmp_key.equals(key);
    	    if (tmp_key.equals(key)) {
		flag = true;
	    }
	    else if (flag) {
    		bucketHash[i-1] = new Pair(bucketHash[i].key, bucketHash[i].value);
    	    }
    	}
    	if (flag) {
    	    numberOfElements--;
    	    sizeBucket[integerKey] = sizeBucket[integerKey] - 1;
    	}
	else if (sizeBucket[integerKey] == numberOfSlots) {
	    int sz = overflow.size();
	    int g5 = {| size, mod, numberOfSlots, numberOfElements, integerKey, index, sz |};
    	    for (int i = 0; i < sz; i++) {
	    	Pair<K,V> tmp = overflow.get(i);
	    	K tmp_key = tmp.key;				
	    	boolean b = tmp_key.equals(key);
    	    	if (tmp_key.equals(key)) {
    	    	    overflow.remove(i);
    	    	    numberOfElements--;
	    	    return;
    	    	}
    	    }
    	}
    }

    public boolean containsKey(K key) {
    	V check = get(key);
    	if (check == null)
    	    return false;
    	return true;
    }

    public boolean isEmpty() {
    	if (numberOfElements == ??)
    	    return true;
    	return false;
    }

    public int size() {
    	return numberOfElements;
    }

    public void clear() {
	bucketHash = new Pair[size];
	overflow = new ArrayList<>();
	sizeBucket = new int[2];
	numberOfElements = 0;
    }
}
