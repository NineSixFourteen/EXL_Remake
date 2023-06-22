package dos.EXL.Types.Lines;

import dos.EXL.Compiler.ASM.Util.Primitives;
import dos.EXL.Filer.Program.Function.LaterInt;
import dos.EXL.Filer.Program.Function.VariableData;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.Util.IndentMaker;
import dos.Util.Maybe;
import dos.Util.Interaces.MethodInterface;
import dos.Util.Interaces.DataInterface;
import dos.EXL.Types.MyError;

public class PrintLine implements Line {


    public Expression val; 

    public PrintLine(Expression e){
        val = e;
    }

    @Override
    public void accept() {

    }

    @Override
    public String makeString(int indent) {
        return IndentMaker.indent(indent) + "print " +  val.makeString() + ";\n";
    }

    @Override
    public Maybe<MyError> validate(DataInterface visitor, int l) {
        return val.validate(visitor,l);
    }

    @Override
    public void toASM(MethodInterface pass) {
        Primitives type = Primitives.getPrimitive(val.getType(pass.getData(), pass.getLineNumber()).getValue());
        pass.print(val,type);
        pass.lineNumberInc();
    }

    @Override
    public void addToData(DataInterface data) {
    }

    public int fill(int lineNumber, VariableData data, LaterInt scopeEnd) {
        return ++lineNumber;
    }  
}
