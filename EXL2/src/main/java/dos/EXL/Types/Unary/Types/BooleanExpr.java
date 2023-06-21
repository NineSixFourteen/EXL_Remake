package dos.EXL.Types.Unary.Types;

import org.objectweb.asm.Label;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.EXL.Types.Binary.Boolean.BoolExpr;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

public class BooleanExpr implements BoolExpr  {

    private boolean bool; 

    public BooleanExpr(boolean b){
        bool = b;
    }

    @Override
    public void accept() {
        
    }

    @Override
    public String makeString() {
        return "" + bool;
    }

    @Override
    public Maybe<MyError> validate(DataInterface visitor, int line) {
        return new Maybe<>();
    }

    @Override
    public void toASM(MethodInterface visitor,Primitives type) {

    }

    @Override
    public Result<String> getType(DataInterface visitor, int line) {
        return Results.makeResult("boolean");
    }

    @Override
    public void pushInverse(Label jumpLoc) {

    }

    @Override
    public void push() {

    }
}
