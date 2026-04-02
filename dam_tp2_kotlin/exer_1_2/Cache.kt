package exer_1_2

class Cache<K : Any, V : Any > {

    private val cache: MutableMap<K, V> = mutableMapOf();

    fun put(key: K, value: V) {
        cache[key] = value;
    }

    fun get(key: K) : V? {
       return cache[key]
    }

    fun evict(key: K) {
        cache.remove(key)
    }

    fun size() : Int {
        return cache.size;
    }

    fun getOrPut(key: K, default: () -> V): V? {

        if(cache[key] != null) {
            return cache[key]
        }

        cache[key] = default()

        return cache[key]
    }
}