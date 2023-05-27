package dos.Util;

import dos.EXL.Types.MyError;

public class Results {

    public static <V> Result<V> makeResult(V val){
        Result<V> res = new Result<>();
        res.setValue(val);
        return res;
    }

    public static <V> Result<V> makeError(String message){
        Result<V> res = new Result<>();
        res.setError(new MyError(message));
        return res;
    }

    
    public static <V> Result<V> makeError(MyError message){
        Result<V> res = new Result<>();
        res.setError(message);
        return res;
    }
    
}
