package dos.EXL.Validator.Misc;

import dos.EXL.Types.Line;
import dos.EXL.Types.Lines.CodeBlock;
import dos.Util.Maybe;

public class CodeBlockValid {
    
    public static Maybe<Error> validate(CodeBlock cbb){
        for(Line l : cbb.getLines()){
            var valid = l.validate();
            if(valid.hasValue()){
                return valid;
            }
        }
        return new Maybe<>();
    }
}
