package dos.EXL.Types.Lines;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.Util.IndentMaker;

public class WhileLine implements Line {

    public WhileLine(Expression b, CodeBlock ls){
        bool = b;
        body = ls;
    }

    Expression bool;
    CodeBlock body;

    @Override
    public void accept() {

    } 
    
    @Override
    public String makeString(int indent) {
        String res = IndentMaker.indent(indent);
        res += "while( ";
        res += bool.makeString() + "){\n";
        indent++;
        for(Line l : body.lines){
            res += l.makeString(indent); 
        }
        res += IndentMaker.indent(indent - 1) + "}\n";
        return res;
    } 
    
}