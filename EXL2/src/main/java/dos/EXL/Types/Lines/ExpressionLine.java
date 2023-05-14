package dos.EXL.Types.Lines;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
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
    
}
