package dos.EXL.Types.Unary.Types;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Types.Expression;
import dos.EXL.Types.MyError;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Results;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;
import static org.objectweb.asm.Opcodes.*;
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
    public Maybe<MyError> validate(DataInterface visitor) {
        return new Maybe<>();
    }

    @Override
    public void toASM(MethodInterface visitor,Primitives type) {

    }

    @Override
    public Result<String> getType(DataInterface visitor) {
        return Results.makeResult("int");
    }
}