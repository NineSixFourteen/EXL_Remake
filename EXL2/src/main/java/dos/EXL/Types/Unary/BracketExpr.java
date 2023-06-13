package dos.EXL.Types.Unary;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

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
    public Maybe<MyError> validate(DataInterface visitor, int line) {
        return body.validate(visitor,line);
    }

    @Override
    public void toASM(MethodInterface visitor,Primitives type, int line) {
        visitor.push(body, type,line);
    }

    @Override
    public Result<String> getType(DataInterface visitor, int line) {
        return body.getType(visitor,line);
    }

}
