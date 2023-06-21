package dos.EXL.Types.Binary.Boolean;

import org.objectweb.asm.Label;

import dos.EXL.Types.Expression;

public interface BoolExpr extends Expression {

    void pushInverse(Label jumpLoc);

    void push();
    
}
