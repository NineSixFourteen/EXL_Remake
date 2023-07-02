package dos.EXL.Validator.Misc;

import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Lines.CodeBlock;
import dos.Util.Maybe;

public class CodeBlockValid {
    
    public static Maybe<MyError> validate(CodeBlock cbb, DataInterface visitor, int line){
        for(Line l : cbb.getLines()){
            var valid = l.validate(visitor, line);
            if(valid.hasValue()){
                return valid;
            }
        }
        return new Maybe<>();
    }
}
