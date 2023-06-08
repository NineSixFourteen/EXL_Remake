package dos.EXL.Types;

import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.Interaces.DataInterface;

public interface Expression {
    
    public void accept();

    public String makeString();

    public Maybe<MyError> validate(DataInterface visitor);

    public void toASM();

    public Result<String> getType(DataInterface visitor);
}
