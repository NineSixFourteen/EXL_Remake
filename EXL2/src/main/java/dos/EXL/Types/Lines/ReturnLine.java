package dos.EXL.Types.Lines;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.Util.IndentMaker;

public class ReturnLine implements Line {


    public Expression val; 

    public ReturnLine(Expression e){
        val = e;
    }

    @Override
    public void accept() {

    }

    @Override
    public String makeString(int indent) {
        return IndentMaker.indent(indent) + "return " +  val.makeString() + ";\n";
    }

}

