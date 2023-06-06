package dos.EXL.Validator.Functions;

import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Types.MyError;
import dos.EXL.Types.Tag;
import dos.EXL.Types.Lines.CodeBlock;
import dos.EXL.Validator.Misc.CodeBlockValid;
import dos.EXL.Validator.Misc.TagValidator;
import dos.Util.Maybe;
import dos.Util.InfoClasses.FunctionVisitor;

public class ValFunctionMake {

    public static Maybe<MyError> validate(String name, List<Tag> tags, List<Pair<String, String>> params, String type,CodeBlock body, FunctionVisitor base) {     
        var bodyValid = CodeBlockValid.validate(body,base);
        if(bodyValid.hasValue()){
            return bodyValid;
        }
        var x = TagValidator.validateForFunctionOrField(tags);
        return x;
    }
    
}
