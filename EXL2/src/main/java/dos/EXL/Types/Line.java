package dos.EXL.Types;

import dos.EXL.Filer.Program.Function.LaterInt;
import dos.EXL.Filer.Program.Function.VariableData;
import dos.Util.Maybe;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

public interface Line {
    
    public void accept();

    public String makeString(int indent);

    public Maybe<MyError> validate(DataInterface visitor, int lineNumber);

    public void addToData(DataInterface data);

    public void toASM(MethodInterface pass);

    public int fill(int lineNumber, VariableData data, LaterInt scopeEnd);
}
