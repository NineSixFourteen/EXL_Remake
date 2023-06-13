package dos.EXL.Types;


import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

public interface Expression {
    
    public void accept();

    public String makeString();

    public Maybe<MyError> validate(DataInterface visitor, int line);

    public void toASM(MethodInterface visitor,Primitives type, int line);

    public Result<String> getType(DataInterface visitor, int line);
}
