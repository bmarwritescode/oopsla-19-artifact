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
	    
	if (localObjs[0] == null) {
	    return null;
	}
	K keyy = (K) localObjs[0];
	localInts[0] = keyy.hashCode() % localInts[1];
	if (localInts[0] < 0) {
	    localInts[0] *= -1;
	}
	localInts[3] = localInts[2] * localInts[0];
	for (int i = localInts[3]; i < localInts[3]+sizeBucket[localInts[0]]; i++) {
	    localInts[4] = i;
	    localObjs[1] = bucketHash[localInts[4]];
	    Pair<K,V> tmp = (Pair<K,V>) localObjs[1];
	    localObjs[2] = tmp.key;
	    K tmp_key = (K) localObjs[2];
	    K keyy2 = (K) localObjs[0]; 
	    if(tmp_key.equals(keyy2)) {
	    	Pair<K,V> tmp2 = (Pair<K,V>) localObjs[1];
	    	return tmp2.value;
	    }
	}
	if (sizeBucket[localInts[0]] == localInts[2]) {
	    ArrayList<Pair<K,V>> os = (ArrayList<Pair<K,V>>) localObjs[3];
	    for (int i = 0; i < os.size(); i++) {
	    	localInts[4] = i;
	    	localObjs[1] = os.get(i);
	    	Pair<K,V> tmp = (Pair<K,V>) localObjs[1];
	    	localObjs[2] = tmp.key;
	    	K tmp_key = (K) localObjs[2];
	    	if(tmp_key.equals(localObjs[0])) {
	    	    Pair<K,V> tmp2 = (Pair<K,V>) localObjs[1];
	    	    return tmp2.value;
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
