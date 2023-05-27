package dos.EXL.Validator.Functions;

import java.util.List;

import org.javatuples.Pair;

import dos.EXL.Types.MyError;
import dos.EXL.Types.Tag;
import dos.EXL.Types.Lines.CodeBlock;
import dos.EXL.Validator.Misc.CodeBlockValid;
import dos.EXL.Validator.Misc.TagValidator;
import dos.Util.DescriptionMaker;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.ValueRecords;

public class ValFunctionMake {

    public static Maybe<MyError> validate(String name, List<Tag> tags, List<Pair<String, String>> params, String type,CodeBlock body, ValueRecords base) {
        Result<String> desc = DescriptionMaker.makeFuncASM(type, params, base);
        var bodyValid = CodeBlockValid.validate(body,base);
        if(bodyValid.hasValue()){
            return bodyValid;
        }
        var x = TagValidator.validateForFunctionOrField(tags);
        return x.hasValue() ? x : base.addFunction(name, desc.getValue());
    }
    
}
