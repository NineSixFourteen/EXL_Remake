package dos.EXL.Types.Unary;

import java.util.List;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Errors.ErrorFactory;
import dos.Util.DescriptionMaker;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;
import static org.objectweb.asm.Opcodes.*;
public class FunctionExpr implements Expression{
    
    public FunctionExpr(String n, List<Expression> p){
        name = n;
        params = p; 
    }

    String name;
    List<Expression> params; 
    
    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        String res = name + "(";
        for(Expression exp : params){
            res += exp.makeString() + ", ";
        }
        if(params.size() > 0) 
            res = res.substring(0, res.length() -2);
        res += ")";
        return res;
    }

    @Override
    public Maybe<MyError> validate(DataInterface visitor){
        var descriptions = visitor.getDescriptionsFromName(name);
        if(descriptions.size() == 0)
            return new Maybe<>(ErrorFactory.makeLogic("Unable to find any functions with the name " + name, 12));
        var descriptionMaybe = DescriptionMaker.partial(params, visitor);
        if(descriptionMaybe.hasError())
            return new Maybe<MyError>(descriptionMaybe.getError());
        var type = visitor.getType(name, descriptionMaybe.getValue());
        if(type.hasError())
            return new Maybe<>(type.getError());
        for(Expression param : params){
            var x = param.validate(visitor);
            if(x.hasValue())
                return x;
        }
        return new Maybe<>();
    }

    @Override
    public void toASM(MethodInterface visitor,Primitives type) {

    }

    @Override
    public Result<String> getType(DataInterface visitor) {
        var val = validate(visitor);
        if(val.hasValue())
            return Results.makeError(val.getValue()); 
        var descriptionMaybe = DescriptionMaker.partial(params, visitor);
        var type = visitor.getType(name, descriptionMaybe.getValue());
        return type;
    }

    public String getName() {
        return name;
    };

    public List<Expression> getParams() {
        return params;
    }
    
}
