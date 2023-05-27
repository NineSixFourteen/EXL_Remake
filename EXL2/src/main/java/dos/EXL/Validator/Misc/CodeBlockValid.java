package dos.EXL.Validator.Misc;

import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Lines.CodeBlock;
import dos.Util.Maybe;
import dos.Util.ValueRecords;

public class CodeBlockValid {
    
    public static Maybe<MyError> validate(CodeBlock cbb, ValueRecords record){
        for(Line l : cbb.getLines()){
            var valid = l.validate(record);
            if(valid.hasValue()){
                return valid;
            }
        }
        return new Maybe<>();
    }
}
