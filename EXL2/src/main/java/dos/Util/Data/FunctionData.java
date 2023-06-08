package dos.Util.Data;

import java.util.List;

import dos.EXL.Types.MyError;
import dos.EXL.Types.Tag;
import dos.Util.Maybe;

public class FunctionData {

    String Desc;
    List<Tag> Tags;

    public FunctionData(String desc, List<Tag> tags){
        Desc = desc;
        Tags = tags;
    }

    public String getDesc() {
        return Desc;
    }

    public Maybe<MyError> validate(){
        return new Maybe<>();
    }
    
}
