package dos.EXL.Types;

import dos.Util.Maybe;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;

public interface Line {
    
    public void accept();

    public String makeString(int indent);

    public Maybe<MyError> validate(DataInterface visitor, int lineNumber);

    public void addToData(DataInterface data);

    public void toASM(MethodInterface pass);
}
