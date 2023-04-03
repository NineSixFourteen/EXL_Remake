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

    public boolean isOk(){
        return value == null;
    }

    public V getValue() throws Exception{
        if(value == null){
            throw new Exception("There is no value");
        }
        return value;
    }

    public E getError() throws Exception{
        if(error == null){
            throw new Exception("There is no error");
        }
        return error;
    }

}
