package dos.EXL.Types.Unary;

import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.InfoClasses.FunctionVisitor;

public class BracketExpr implements Expression {
    
    Expression body; 

    public BracketExpr(Expression bod){
        body = bod;
    }

    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        return "(" + body.makeString() + ")"; 
    }

    @Override
    public Maybe<MyError> validate(FunctionVisitor visitor) {
        return body.validate(visitor);
    }

    @Override
    public void toASM() {

    }

    @Override
    public Result<String> getType(FunctionVisitor visitor) {
        return body.getType(visitor);
    }

}
