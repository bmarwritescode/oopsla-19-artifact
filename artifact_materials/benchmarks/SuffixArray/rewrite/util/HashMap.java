@rewriteClass
class HashMap<K,V> {
    @alg
    V put(K k, V v);

    @alg
    @pure
    V get(K k);

    rewrite V put(HashMap HashMap(), K k, V v) {
	return null;
    }

    rewrite V put(HashMap put!(HashMap h, K k1, V v1), K k2, V v2) {
	return k2.equals(k1) ? v1 : put(h, k2, v2);
    }

    rewrite V get(HashMap put!(HashMap h, K k1, V v1), K k2) {
	return k2.equals(k1) ? v1 : get(h, k2);
    }

    rewrite V get(HashMap HashMap(), K k) {
	return null;
    }
}
