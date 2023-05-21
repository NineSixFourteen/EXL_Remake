package dos.EXL.Validator.Misc;

import java.util.List;

import dos.EXL.Types.Tag;
import dos.Util.Maybe;

public class TagValidator {
    

    public static Maybe<Error> validateForClass(List<Tag> tags){
        if(tags.size() > 1){
            return new Maybe<Error>(new Error("Class has too many tags can only have one either Public or Private"));
        } 
        if(tags.size() == 1){
            switch(tags.get(0)){
                case Public:
                case Private:
                default:
                    return new Maybe<Error>(new Error(tags.get(0) + " is not valid tag for a class"));
            }
        }
        return new Maybe<>();
    }

    public static Maybe<Error> validateForFunctionOrField(List<Tag> tags){
        if(tags.size() > 0){
            switch(tags.get(0)){
                case Public:
                case Private:
                    if(tags.size() > 1){
                        switch(tags.get(1)){
                            case Static:
                                if(tags.size() > 2){
                                    return new Maybe<Error>(new Error("Too many tags in function "));
                                }
                                break;
                            case Public:
                            case Private:
                                return new Maybe<Error>(new Error("Public or private must go at the start of function"));
                        }
                    }
                    break;
                case Static:
                    if(tags.size() > 1){
                        return new Maybe<Error>(new Error("No tags should come after static tag " + tags.get(1) + " is after"));
                    }
                    break;
                default:
                    return new Maybe<Error>(new Error("Invalid Tag in function - " + tags.get(0)));
            }
        }
        return new Maybe<>();
    }
}
