package engine.core.utils;

import java.util.ArrayList;
import java.util.List;

public class Dict<K, V> {
    private List<K> keys;
    private List<V> values;

    public Dict(){
       this.keys = new ArrayList<>();
       this.values = new ArrayList<>();
    }

    public List<K> keys(){
        return this.keys;
    }

    public List<V> values(){
        return this.values;
    }

    public void put(K k, V v){
        if(keys.stream().noneMatch((K key) -> key.equals(k))){
            this.keys.add(k);
            this.values.add(v);
        }
        else{
            int index = keys.indexOf(k);
            values.set(index, v);
        }
    }

    public V key(K k){
        if(keys.stream().noneMatch((K key) -> key.equals(k))){
            return null;
        }
        else{
            int index = keys.indexOf(k);
            return values.get(index);
        }

    }
}
