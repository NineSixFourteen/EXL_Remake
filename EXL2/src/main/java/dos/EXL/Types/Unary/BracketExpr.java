package dos.EXL.Types.Unary;

import org.objectweb.asm.Label;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

public class BracketExpr implements BoolExpr {
    
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
    public void toASM(MethodInterface visitor,Primitives type) {
        visitor.push(body, type);
    }

    @Override
    public Result<String> getType(DataInterface visitor, int line) {
        return body.getType(visitor,line);
    }

    @Override
    public void pushInverse(Label jumpLoc) {
        try{
            BoolExpr bool = (BoolExpr) body; // Test this works
            bool.pushInverse(jumpLoc);
        } catch(Exception e){}
    }

    @Override
    public void push() {
        try{
            BoolExpr bool = (BoolExpr) body;
            bool.push();
        } catch(Exception e){}
    }

}
