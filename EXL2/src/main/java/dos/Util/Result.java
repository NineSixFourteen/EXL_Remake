package dos.Util;

public class Result<V,E> {
    
    private V value;
    private E error; 
    
    public Result(){
        value = null;
        error = null;
    }

    public void setValue(V v){
        value = v;
    }

    public void setError(E e){
        error = e;
        value = null;
    }

    public boolean hasValue(){
        return value != null;
    }

    public boolean hasError(){
        return error != null;
    }


    public V getValue(){
        return value;
    }

    public E getError(){
        return error;
    }

}
