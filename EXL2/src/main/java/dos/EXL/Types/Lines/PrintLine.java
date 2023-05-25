package dos.EXL.Types.Lines;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.Util.IndentMaker;
import dos.EXL.Compiler.ASM.Util.ASMPass;
import dos.Util.Maybe;
import dos.Util.ValueRecords;

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
    public Maybe<Error> validate(ValueRecords records) {
        return val.validate(records);
    }

    @Override
    public void toASM(ASMPass pass) {

    } 

}
