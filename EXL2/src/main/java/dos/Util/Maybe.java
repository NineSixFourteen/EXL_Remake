package dos.Util;

public class Maybe<V> {
    
    private V value; 
    
    public Maybe(){
        value = null;
    }

    public Maybe(V v){
        value = v;
    }
    public boolean hasValue(){
        return value != null;
    }

    public V getValue(){
        return value;
    }
    
}
