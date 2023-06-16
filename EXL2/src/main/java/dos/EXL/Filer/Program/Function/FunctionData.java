package dos.EXL.Filer.Program.Function;

import java.util.List;

import dos.EXL.Types.MyError;
import dos.EXL.Types.Tag;
import dos.Util.Maybe;

public class FunctionData {

    String name;  
    String key; // if function is call fun and takes two ints key is fun(II) 
    String Desc;
    String Type; 
    List<Tag> Tags;

    public FunctionData(String n,String type, String desc, List<Tag> tags){
        name = n;
        Type = type;
        key = n + desc.substring(0, desc.lastIndexOf(")") + 1);
        Desc = desc;
        Tags = tags;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return Type;
    }

    public boolean is(String k){
        return k.equals(key);
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return Desc;
    }

    public List<Tag> getTags() {
        return Tags;
    }

    public Maybe<MyError> validate(){
        return new Maybe<>();
    }
    
}
