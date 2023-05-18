package dos.EXL.Types.Lines;
import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.Util.IndentMaker;
import dos.EXL.Compiler.ASM.Util.ASMPass;
import dos.Util.Maybe;


public class ForLine implements Line {

    public ForLine(DeclarLine d, Expression b, Line l, CodeBlock ls){
        dec = d;
        bool = b;
        line = l;
        body = ls;
    }

    DeclarLine dec;
    Expression bool;
    Line line;
    CodeBlock body;

    @Override
    public void accept() {
    }

    @Override
    public String makeString(int indent) {
        String res = IndentMaker.indent(indent);
        res += "for( ";
        res += dec.makeString(0) + "; ";
        res += bool.makeString() + "; ";
        res += line.makeString(0) + "){\n";
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