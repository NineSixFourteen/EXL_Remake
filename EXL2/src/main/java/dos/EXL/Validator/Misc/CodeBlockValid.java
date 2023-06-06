package dos.EXL.Validator.Misc;

import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Lines.CodeBlock;
import dos.Util.Maybe;
import dos.Util.InfoClasses.FunctionVisitor;

public class CodeBlockValid {
    
    public static Maybe<MyError> validate(CodeBlock cbb, FunctionVisitor visitor){
        for(Line l : cbb.getLines()){
            var valid = l.validate(visitor);
            if(valid.hasValue()){
                return valid;
            }
        }
        return new Maybe<>();
    }
}
