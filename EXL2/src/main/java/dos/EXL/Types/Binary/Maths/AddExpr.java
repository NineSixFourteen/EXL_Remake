package dos.EXL.Types.Binary.Maths;

import dos.EXL.Types.Expression;
import dos.EXL.Validator.Util.TypeCombiner;
import dos.Util.Maybe;
import dos.Util.ValueRecords;

public class AddExpr implements Expression{
    
    public AddExpr(Expression l , Expression r){
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
        return left.makeString() + " + "  + right.makeString();
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
        return TypeCombiner.MathsBinary(left, right, records);
    }
    
}