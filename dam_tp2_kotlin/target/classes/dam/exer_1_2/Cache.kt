package dam.exer_1_2

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

    fun transform(key: K, action: (V) -> V) : Boolean {
        if(cache[key] != null) { // key exists
            cache[key] = action(cache[key] as V)
            return true
        }
        return false;
    }

    fun snapshot(): Map<K, V> {
        return cache.toMap();
    }

    //challenge
    fun filterValues(predicate: (V) -> Boolean): Map<K, V> {
        //return cache.filterValues(predicate)

        val result = mutableMapOf<K, V>();
        for((key, value) in cache) {
            if(predicate(value)) {
                result[key] = value
            }
        }
        return result.toMap()
    }
}