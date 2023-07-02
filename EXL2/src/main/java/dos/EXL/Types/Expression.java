package dos.EXL.Types;


import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Compiler.ASM.Interaces.MethodInterface;
import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.Util.Maybe;
import dos.Util.Result;

public interface Expression {
    
    public void accept();

    public String makeString();

    public Maybe<MyError> validate(DataInterface visitor, int line);

    public void toASM(MethodInterface visitor,Primitives type);

    public Result<String> getType(DataInterface visitor, int line);
}
