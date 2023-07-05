package dos.EXL.Types.Unary;

import org.objectweb.asm.Label;

import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Compiler.ASM.Interaces.MethodInterface;
import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.Util.Maybe;
import dos.Util.Result;

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
    public void pushInverse(MethodInterface visit,Label jumpLoc, Label j2) {
        try{
            BoolExpr bool = (BoolExpr) body; // Test this works
            bool.pushInverse(visit,jumpLoc, j2);
        } catch(Exception e){}
    }

    @Override
    public void push(MethodInterface visit,Label jumpLoc, Label j2) {
        try{
            BoolExpr bool = (BoolExpr) body;
            bool.push(visit,jumpLoc, j2);
        } catch(Exception e){}
    }

    @Override
    public boolean isOr() {
        try{
            BoolExpr bool = (BoolExpr) body;
            return bool.isOr();
        } catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean isAnd() {
        try{
            BoolExpr bool = (BoolExpr) body;
            return bool.isAnd();
        } catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean isAndorOr() {
        try{
            BoolExpr bool = (BoolExpr) body;
            return bool.isAndorOr();
        } catch(Exception e){
            return false;
        }
    }

}
