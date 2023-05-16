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
        return null;
    }

    @Override
    public void toASM() {

    }

    @Override
    public String getType(ValueRecords records) {
        String description = DescriptionMaker.partial(name, params, records);
        return records.getType(name, description,records);
    }
    
}
