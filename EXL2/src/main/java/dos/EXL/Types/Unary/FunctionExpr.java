package dos.EXL.Types.Unary;

import java.util.List;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.Util.DescriptionMaker;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.InfoClasses.ValueRecords;

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
    public Maybe<MyError> validate(ValueRecords records) {
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
    public Result<String> getType(ValueRecords records) {
        var val = validate(records);
        if(val.hasValue()){
            return Results.makeError(val.getValue());
        }
        var descriptionMaybe = DescriptionMaker.partial(params, records);
        if(descriptionMaybe.hasError())
            return descriptionMaybe;
        var type = records.getType(name, descriptionMaybe.getValue(),records);
        return type;
    }

    public String getName() {
        return name;
    };

    public List<Expression> getParams() {
        return params;
    }
    
}
