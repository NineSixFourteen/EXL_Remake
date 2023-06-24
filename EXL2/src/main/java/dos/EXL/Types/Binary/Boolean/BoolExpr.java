package dos.EXL.Types.Binary.Boolean;

import org.objectweb.asm.Label;

import dos.EXL.Types.Expression;
import dos.Util.Interaces.MethodInterface;

public interface BoolExpr extends Expression {

    void pushInverse(MethodInterface visit,Label jump1, Label jump2);

    void push(MethodInterface visit,Label jump1, Label jump2);
    
}
