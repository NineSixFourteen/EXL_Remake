package dos.EXL.Types.Binary.Boolean;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import dos.EXL.Types.Expression;

public interface BoolExpr extends Expression {

    void pushInverse(MethodVisitor visit,Label jump1, Label jump2);

    void push(MethodVisitor visit,Label jump1, Label jump2);
    
}
