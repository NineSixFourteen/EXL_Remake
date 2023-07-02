package dos.EXL.Types.Unary.Types;

import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Compiler.ASM.Interaces.MethodInterface;
import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;

public class IntExpr  implements Expression  {

    private int val; 

    public IntExpr(int c){
        val = c;
    }

    @Override
    public void accept() {
        throw new UnsupportedOperationException("Unimplemented method 'accept'");
    }

    @Override
    public String makeString() {
        return val + "";
    }

    @Override
    public Maybe<MyError> validate(DataInterface visitor, int line) {
        return new Maybe<>();
    }

    @Override
    public void toASM(MethodInterface visitor,Primitives type) {
        visitor.pushInt(val, type);
    }

    @Override
    public Result<String> getType(DataInterface visitor, int line) {
        return Results.makeResult("int");
    }
}