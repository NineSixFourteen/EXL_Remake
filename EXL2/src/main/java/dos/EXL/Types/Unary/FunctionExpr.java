package dos.EXL.Types.Unary;

import java.util.List;

import dos.EXL.Types.Expression;
import dos.Util.DescriptionMaker;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.ValueRecords;

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
        res = res.substring(0, res.length() -2);
        res += ")";
        return res;
    }

    @Override
    public Maybe<Error> validate(ValueRecords records) {
        var type = getType(records);
        if(type.hasError()){
            return new Maybe<Error>(type.getError());
        }
        for(Expression param : params){
            var x = param.validate(records);
            if(x.hasValue()){
                return x;
            }
        }
        return new Maybe<>();
    }

    @Override
    public void toASM() {

    }

    @Override
    public Result<String,Error> getType(ValueRecords records) {
        var val = validate(records);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }
        var descriptionMaybe = DescriptionMaker.partial(name, params, records);
        if(descriptionMaybe.hasError()){return descriptionMaybe;}
        var type = records.getType(name, descriptionMaybe.getValue(),records);
        return type;
    }
    
}
