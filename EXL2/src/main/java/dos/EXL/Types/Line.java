package dos.EXL.Types;

import dos.Util.Maybe;
import dos.Util.InfoClasses.FunctionVisitor;

public interface Line {
    
    public void accept();

    public String makeString(int indent);

    public Maybe<MyError> validate(FunctionVisitor visitor);

    public void toASM(FunctionVisitor pass);
}
