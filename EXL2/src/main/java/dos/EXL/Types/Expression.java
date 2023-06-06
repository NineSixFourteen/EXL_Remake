package dos.EXL.Types;

import dos.Util.Maybe;
import dos.Util.Result;
import dos.Util.InfoClasses.FunctionVisitor;

public interface Expression {
    
    public void accept();

    public String makeString();

    public Maybe<MyError> validate(FunctionVisitor visitor);

    public void toASM();

    public Result<String> getType(FunctionVisitor visitor);
}
