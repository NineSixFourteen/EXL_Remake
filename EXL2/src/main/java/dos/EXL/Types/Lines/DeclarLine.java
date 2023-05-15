package dos.EXL.Types.Lines;

import dos.EXL.Compiler.ASM.Util.ASMPass;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.Util.IndentMaker;
import dos.Util.Maybe;

public class DeclarLine implements Line {

    public DeclarLine(String n, String t, Expression e){
        name = n;
        value = e;
        type = t;
    }

    String name;
    String type;
    Expression value;

    @Override
    public void accept() {
        
    }

    @Override
    public String makeString(int indent) {
        return IndentMaker.indent(indent) + type + " "+ name + " = " + value.makeString() + ";\n";
    }

    @Override
    public Maybe<Error> validate() {
        return null;
    }

    @Override
    public void toASM(ASMPass pass) {

    } 
    
    
}
