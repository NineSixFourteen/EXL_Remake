package dos.EXL.Types.Unary;

import java.util.List;

import dos.EXL.Types.Expression;
import dos.Util.DescriptionMaker;
import dos.Util.Maybe;
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
        String type = getType(records);
        if(type.equals("error")){
            return new Maybe<Error>(new Error("Function type could not be found no matching pattern of Name: " + name + "Description : " + DescriptionMaker.partial(name, params, records)));
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
    public String getType(ValueRecords records) {
        String description = DescriptionMaker.partial(name, params, records);
        var type = records.getType(name, description,records);
        return type.hasError() ? "error" : type.getValue();
    }
    
}
