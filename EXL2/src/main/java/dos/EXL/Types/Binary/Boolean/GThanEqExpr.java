package dos.EXL.Types.Binary.Boolean;

import dos.EXL.Types.Expression;
import dos.Util.Maybe;
import dos.Util.ValueRecords;


public class GThanEqExpr implements Expression{
    
    public GThanEqExpr(Expression l , Expression r){
        left = l; 
        right = r;
    }

    public Expression left; 
    public Expression right;
    
    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        return left.makeString() + " >= "  + right.makeString();
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
        return "boolean";
    }
    

}