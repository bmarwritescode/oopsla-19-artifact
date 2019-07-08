@rewriteClass
class HashMap<K, V> {

    @alg
    V put(K k, V v);

    @alg
    V remove(Object k);
    
    @alg
    @pure
    V get(Object k);

    @alg
    @pure
    boolean containsKey(Object k);

    rewrite V get(HashMap remove!(HashMap h, Object k1), Object k2) {
    	return k2.equals(k1) ? null : get(h, k2);
    }
    
    rewrite V get(HashMap put!(HashMap h, K k1, V v1), Object k2) {
	return k2.equals(k1) ? v1 : get(h, k2);
    }

    rewrite V get(HashMap HashMap(), Object k) {
	return null;
    }

    rewrite boolean containsKey(HashMap remove!(HashMap h, Object k1), Object k2) {
    	return k2.equals(k1) ? false : containsKey(h, k2);
    }
    
    rewrite boolean containsKey(HashMap put!(HashMap h, K k1, V v1), Object k2) {
	return k1.equals(k2) ? true : containsKey(h, k2);
    }

    rewrite boolean containsKey(HashMap HashMap(), Object k) {
	return false;
    }
      
}
