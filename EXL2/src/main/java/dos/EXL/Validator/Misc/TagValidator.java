package dos.EXL.Validator.Misc;

import java.util.List;

import dos.EXL.Types.MyError;
import dos.EXL.Types.Tag;
import dos.Util.Maybe;

public class TagValidator {
    

    public static Maybe<MyError> validateForClass(List<Tag> tags){
        if(tags.size() > 1){
            return new Maybe<>(new MyError("Class has too many tags can only have one either Public or Private"));
        } 
        if(tags.size() == 1){
            switch(tags.get(0)){
                case Public:
                case Private:
                default:
                    return new Maybe<>(new MyError(tags.get(0) + " is not valid tag for a class"));
            }
        }
        return new Maybe<>();
    }

    public static Maybe<MyError> validateForFunctionOrField(List<Tag> tags){
        if(tags.size() > 0){
            switch(tags.get(0)){
                case Public:
                case Private:
                    if(tags.size() > 1){
                        switch(tags.get(1)){
                            case Static:
                                if(tags.size() > 2){
                                    return new Maybe<>(new MyError("Too many tags in function "));
                                }
                                break;
                            case Public:
                            case Private:
                                return new Maybe<>(new MyError("Public or private must go at the start of function"));
                        }
                    }
                    break;
                case Static:
                    if(tags.size() > 1){
                        return new Maybe<>(new MyError("No tags should come after static tag " + tags.get(1) + " is after"));
                    }
                    break;
                default:
                    return new Maybe<>(new MyError("Invalid Tag in function - " + tags.get(0)));
            }
        }
        return new Maybe<>();
    }
}
