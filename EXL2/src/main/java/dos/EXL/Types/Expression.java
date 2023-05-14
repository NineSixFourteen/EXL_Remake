package dos.EXL.Types;

import dos.Util.Maybe;

public interface Expression {
    
    public void accept();

    public String makeString();

    public Maybe<Error> validate();

    public void compileASM();
}
