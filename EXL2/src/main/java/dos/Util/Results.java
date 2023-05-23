package dos.Util;

public class Results {

    public static <V,U> Result<V,U> makeResult(V val){
        Result<V,U> res = new Result<>();
        res.setValue(val);
        return res;
    }

    public static <V,U> Result<V,U> makeError(U error){
        Result<V,U> res = new Result<>();
        res.setError(error);
        return res;
    }
    
}
