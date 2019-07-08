package java.util;

public interface Map<K,V> {
    public void clear();
    public int size();
    public boolean containsKey(K key);
    public V get(K key);
    public V put(K key, V value);
    public V replace(K key, V value);
    public V remove(K key);
    
    interface Entry<K,V> {
    	public boolean equals(Object o);
    }
}
