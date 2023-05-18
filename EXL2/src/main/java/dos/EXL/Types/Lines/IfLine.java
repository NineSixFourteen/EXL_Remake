package dos.EXL.Types.Lines;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.Util.IndentMaker;
import dos.EXL.Compiler.ASM.Util.ASMPass;
import dos.Util.Maybe;

public class IfLine implements Line {

    public IfLine(Expression e, CodeBlock ls){
        val = e;
        body = ls;
    }
    
    private Expression val;
    private CodeBlock body;

    @Override
    public void accept() {
        
    }
    @Override
    public String makeString(int indent) {
        String res = IndentMaker.indent(indent);
        res += "if ";
        res += val.makeString() + "{\n";
        indent++;
        for(Line l : body.lines){
            res += l.makeString(indent); 
        }
        res += IndentMaker.indent(indent - 1) + "}\n";
        return res;
    } 

    @Override
    public Maybe<Error> validate() {
        return null;
    }

    @Override
    public void toASM(ASMPass pass) {

    } 
    
}