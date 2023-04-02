package dos.Util;

public class Result<V,E> {
    
    private V value;
    private E error; 
    
    Result(){
        value = null;
        error = null;
    }

    void setValue(V v){
        value = v;
    }

    void setError(E e){
        error = e;
        value = null;
    }

    boolean isOk(){
        return value == null;
    }

    V getValue() throws Exception{
        if(value == null){
            throw new Exception("There is no value");
        }
        return value;
    }

    E getError() throws Exception{
        if(error == null){
            throw new Exception("There is no error");
        }
        return error;
    }

}
