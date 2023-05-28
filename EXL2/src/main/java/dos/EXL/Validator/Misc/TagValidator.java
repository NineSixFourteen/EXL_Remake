package dos.EXL.Validator.Misc;

import java.util.List;

import dos.EXL.Types.MyError;
import dos.EXL.Types.Tag;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.Maybe;

public class TagValidator {
    

    public static Maybe<MyError> validateForClass(List<Tag> tags){
        if(tags.size() > 1){
            return new Maybe<>(ErrorFactory.makeLogic("Class has too many tags can only have one either Public or Private",15));
        } 
        if(tags.size() == 1){
            switch(tags.get(0)){
                case Public:
                case Private:
                default:
                    return new Maybe<>(ErrorFactory.makeLogic(tags.get(0) + " is not valid tag for a class",15));
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
                                    return new Maybe<>(ErrorFactory.makeLogic("Too many tags in function ",14));
                                }
                                break;
                            case Public:
                            case Private:
                                return new Maybe<>(ErrorFactory.makeLogic("Public or private must go at the start of function",14));
                        }
                    }
                    break;
                case Static:
                    if(tags.size() > 1){
                        return new Maybe<>(ErrorFactory.makeLogic("No tags should come after static tag " + tags.get(1) + " is after",14));
                    }
                    break;
                default:
                    return new Maybe<>(ErrorFactory.makeLogic("Invalid Tag in function - " + tags.get(0),14));
            }
        }
        return new Maybe<>();
    }
}
