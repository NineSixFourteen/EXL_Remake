package dos.EXL.Types.Lines;

import dos.Util.Maybe;
import dos.EXL.Compiler.ASM.Interaces.DataInterface;
import dos.EXL.Compiler.ASM.Interaces.MethodInterface;
import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Filer.Program.Function.LaterInt;
import dos.EXL.Filer.Program.Function.VariableData;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.EXL.Types.MyError;
import dos.Util.IndentMaker;


public class ExpressionLine implements Line  {

    private Expression expr;

    public ExpressionLine(Expression e){
        expr = e;
    }

    @Override
    public void accept() {
        
    }

    @Override
    public String makeString(int indent) {
        return IndentMaker.indent(indent) +  expr.makeString() + ";\n";
    }

    @Override
    public Maybe<MyError> validate(DataInterface visitor, int l) {
        return expr.validate(visitor,l);
    }

    @Override
    public void toASM(MethodInterface pass) {
        Primitives p = Primitives.getPrimitive(expr.getType(pass.getData(), pass.getLineNumber()).getValue());
        pass.push(expr,p);
        pass.pop(p);
        pass.lineNumberInc();
    }

    @Override
    public void addToData(DataInterface data) {
    } 

    public int fill(int lineNumber, VariableData data, LaterInt scopeEnd) {
        return ++lineNumber;
    }  
    
}
