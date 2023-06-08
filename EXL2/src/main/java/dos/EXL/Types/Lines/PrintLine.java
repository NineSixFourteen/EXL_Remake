package dos.EXL.Types.Lines;

import dos.EXL.Types.Expression;
import dos.EXL.Types.Line;
import dos.Util.IndentMaker;
import dos.Util.Maybe;
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
    public Maybe<MyError> validate(DataInterface visitor) {
        return val.validate(visitor);
    }

    @Override
    public void toASM(DataInterface pass) {

    } 

}
