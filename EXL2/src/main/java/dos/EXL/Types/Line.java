package dos.EXL.Types;

import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Compiler.ASM.Interaces.MethodInterface;
import dos.EXL.Filer.Program.Function.LaterInt;
import dos.EXL.Filer.Program.Function.VariableData;
import dos.Util.Maybe;

public interface Line {
    
    public void accept();

    public String makeString(int indent);

    public Maybe<MyError> validate(DataInterface visitor, int lineNumber);

    public void addToData(DataInterface data);

    public void toASM(MethodInterface pass);

    public int fill(int lineNumber, VariableData data, LaterInt scopeEnd);
}
