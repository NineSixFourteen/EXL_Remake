package dos.EXL.Types.Lines;

import java.util.List;

import dos.Util.Maybe;
import dos.Util.InfoClasses.FunctionVisitor;
import dos.Util.InfoClasses.FunctionVisitor;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.Tag;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.EXL.Validator.Misc.TagValidator;
import dos.Util.IndentMaker;
import dos.EXL.Types.MyError;


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
    public Maybe<MyError> validate(FunctionVisitor visitor) {
        var tagV = TagValidator.validateForFunctionOrField(tags);
        if(tagV.hasValue()){
            return tagV;
        }
        var valueType = expr.getType(visitor);
        if(valueType.hasError())
            return new Maybe<>(valueType.getError());
        if(!type.equals(valueType.getValue()))
            return new Maybe<>(ErrorFactory.makeLogic("Expression" + expr.makeString() +  " doesn't match type of " + type, 10));
        var addError = visitor.addField(name, type);
        if(addError.hasValue())
            return addError;
        return new Maybe<>();
    }
       

    @Override
    public void toASM(FunctionVisitor pass) {

    } 
    
}
