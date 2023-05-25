package dos.EXL.Types.Lines;

import java.util.List;
import dos.EXL.Compiler.ASM.Util.ASMPass;
import dos.Util.Maybe;
import dos.Util.ValueRecords;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.Tag;
import dos.EXL.Validator.Misc.TagValidator;
import dos.Util.IndentMaker;

public class Field implements Line {
    private List<Tag> tags;
    private String name; 
    private String type;
    private Expression expr;

    public Field(List<Tag> t, String n, Expression e, String ty){
        tags = t;
        name = n; 
        expr = e;
        type = ty;
    }

    @Override
    public void accept() {

    }

    @Override
    public String makeString(int indent) {
        String res = IndentMaker.indent(indent);
        for(Tag t : tags){
            res += t.name().toLowerCase() + " ";
        }
        res += type + " ";
        res += name + " = ";
        res += expr.makeString() + ";\n";
        return res;
    }

    @Override
    public Maybe<Error> validate(ValueRecords records) {
        var tagV = TagValidator.validateForFunctionOrField(tags);
        if(tagV.hasValue()){
            return tagV;
        }
        var valueType = expr.getType(records);
        if(valueType.hasError()){
            return new Maybe<Error>(valueType.getError());
        }
        if(!type.equals(valueType.getValue())){// TODO Check nums to be converted
            return new Maybe<Error>(new Error("Expression doesn't match type"));
        }
        return new Maybe<>();
    }
       

    @Override
    public void toASM(ASMPass pass) {

    } 
    
}
