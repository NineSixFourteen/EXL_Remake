package dos.EXL.Types.Lines;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.Util.IndentMaker;
import dos.EXL.Compiler.ASM.Util.ASMPass;
import dos.Util.Maybe;

public class VarOverwrite implements Line {

    String name; 
    Expression newExpr;

    public VarOverwrite(String n, Expression nE){
        name = n;
        newExpr = nE;
    }

    @Override
    public void accept() {
    } 

    @Override
    public String makeString(int indent) {
        return IndentMaker.indent(indent) + name +  " = " + newExpr.makeString() + ";\n";
    }

    @Override
    public Maybe<Error> validate() {
        return null;
    }

    @Override
    public void toASM(ASMPass pass) {

    } 

    
}
