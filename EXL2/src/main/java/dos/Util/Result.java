package dos.Util;

import dos.EXL.Types.MyError;

public class Result<V> {
    
    private V value;
    private MyError error; 
    
    public Result(){
        value = null;
        error = null;
    }

    public void setValue(V v){
        value = v;
    }

    public void setError(MyError e){
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

    public MyError getError(){
        return error;
    }

}
